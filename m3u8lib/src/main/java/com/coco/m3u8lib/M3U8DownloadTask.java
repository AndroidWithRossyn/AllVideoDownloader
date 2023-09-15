package com.coco.m3u8lib;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coco.m3u8lib.M3U8DownloaderConfig;
import com.coco.m3u8lib.M3U8EncryptHelper;
import com.coco.m3u8lib.M3U8InfoManger;
import com.coco.m3u8lib.OnM3U8InfoListener;
import com.coco.m3u8lib.OnTaskDownloadListener;
import com.coco.m3u8lib.WeakHandler;
import com.coco.m3u8lib.bean.M3U8;
import com.coco.m3u8lib.bean.M3U8Ts;
import com.coco.m3u8lib.utils.M3U8Log;
import com.coco.m3u8lib.utils.MUtils;

class M3U8DownloadTask {
    private static final int WHAT_ON_ERROR = 1001;
    private static final int WHAT_ON_PROGRESS = 1002;
    private static final int WHAT_ON_SUCCESS = 1003;
    private static final int WHAT_ON_START_DOWNLOAD = 1004;
    private OnTaskDownloadListener onTaskDownloadListener;

    private String encryptKey = null;
    private String m3u8FileName = "local.m3u8";
    private String saveDir;
    private volatile int curTs = 0;
    private volatile int totalTs = 0;
    private volatile long itemFileSize = 0;
    private volatile long totalFileSize = 0;
    private volatile boolean isStartDownload = true;
    private long curLength = 0;
    private boolean isRunning = false;
    private int threadCount = 3;
    private int readTimeout = 30 * 60 * 1000;
    private int connTimeout = 10 * 1000;
    private Timer netSpeedTimer;
    private ExecutorService executor;
    private M3U8 currentM3U8;

    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_ON_ERROR:
                    onTaskDownloadListener.onError((Throwable) msg.obj);
                    break;

                case WHAT_ON_START_DOWNLOAD:
                    onTaskDownloadListener.onStartDownload(totalTs, curTs);
                    break;

                case WHAT_ON_PROGRESS:
                    onTaskDownloadListener.onDownloading(totalFileSize, itemFileSize, totalTs, curTs);
                    break;

                case WHAT_ON_SUCCESS:
                    if (netSpeedTimer != null) {
                        netSpeedTimer.cancel();
                    }
                    onTaskDownloadListener.onSuccess(currentM3U8);
                    break;
            }
            return true;
        }
    });

    public M3U8DownloadTask(){
        connTimeout = com.coco.m3u8lib.M3U8DownloaderConfig.getConnTimeout();
        readTimeout = com.coco.m3u8lib.M3U8DownloaderConfig.getReadTimeout();
        threadCount = M3U8DownloaderConfig.getThreadCount();
    }

    public void download(final String url, OnTaskDownloadListener onTaskDownloadListener) {
        saveDir = MUtils.getSaveFileDir(url);
        M3U8Log.d("start download ,SaveDir: "+ saveDir);
        this.onTaskDownloadListener = onTaskDownloadListener;
        if (!isRunning()) {
            getM3U8Info(url);
        } else {
            handlerError(new Throwable("Task running"));
        }
    }


    public void setEncryptKey(String encryptKey){
        this.encryptKey = encryptKey;
    }

    public String getEncryptKey(){
        return encryptKey;
    }


    public boolean isRunning() {
        return isRunning;
    }

    private void getM3U8Info(String url) {

        M3U8InfoManger.getInstance().getM3U8Info(url, new OnM3U8InfoListener() {
            @Override
            public void onSuccess(final M3U8 m3U8) {
                currentM3U8 = m3U8;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            startDownload(m3U8);
                            if (executor != null) {
                                executor.shutdown();
                            }
                            while (executor != null && !executor.isTerminated()) {
                                Thread.sleep(100);
                            }
                            if (isRunning) {
                                File m3u8File = MUtils.createLocalM3U8(new File(saveDir), m3u8FileName, currentM3U8);
                                currentM3U8.setM3u8FilePath(m3u8File.getPath());
                                currentM3U8.setDirFilePath(saveDir);
                                currentM3U8.getFileSize();
                                mHandler.sendEmptyMessage(WHAT_ON_SUCCESS);
                                isRunning = false;
                            }
                        } catch (InterruptedIOException e) {
                            return;
                        } catch (IOException e) {
                            handlerError(e);
                            return;
                        } catch (InterruptedException e) {
                            handlerError(e);
                            return;
                        } catch (Exception e) {
                            handlerError(e);
                        }
                    }
                }.start();
            }

            @Override
            public void onStart() {
                onTaskDownloadListener.onStart();
            }

            @Override
            public void onError(Throwable errorMsg) {
                handlerError(errorMsg);
            }
        });
    }

    private void startDownload(final M3U8 m3U8) {
        final File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        totalTs = m3U8.getTsList().size();
        if (executor != null) {
            executor.shutdownNow();
        }
        M3U8Log.d("executor is shutDown ! Downloading !");
        curTs = 1;
        isRunning = true;
        isStartDownload = true;
        executor = null;

        executor = Executors.newFixedThreadPool(threadCount);
        final String basePath = m3U8.getBasePath();
        netSpeedTimer = new Timer();
        netSpeedTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTaskDownloadListener.onProgress(curLength);
            }
        }, 0, 1500);

        for (final M3U8Ts m3U8Ts : m3U8.getTsList()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    File file;
                    try {
                        String fileName = M3U8EncryptHelper.encryptFileName(encryptKey, m3U8Ts.obtainEncodeTsFileName());
                        m3U8Ts.setFileName(fileName);
                        file = new File(dir + File.separator + fileName);
                    } catch (Exception e) {
                        file = new File(dir + File.separator + m3U8Ts.getUrl());
                    }

                    if (!file.exists()) {

                        FileOutputStream fos = null;
                        InputStream inputStream = null;
                        try {
                            URL url = new URL(m3U8Ts.obtainFullUrl(basePath));
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(connTimeout);
                            conn.setReadTimeout(readTimeout);
                            if (conn.getResponseCode() == 200) {
                                if (isStartDownload){
                                    isStartDownload = false;
                                    mHandler.sendEmptyMessage(WHAT_ON_START_DOWNLOAD);
                                }
                                inputStream = conn.getInputStream();
                                fos = new FileOutputStream(file);
                                int len = 0;
                                byte[] buf = new byte[8 * 1024 * 1024];
                                while ((len = inputStream.read(buf)) != -1) {
                                    curLength += len;
                                    fos.write(buf, 0, len);
                                }
                            } else {
                                handlerError(new Throwable(String.valueOf(conn.getResponseCode())));
                            }
                        } catch (MalformedURLException e) {
                            handlerError(e);
                        } catch (IOException e) {
                            handlerError(e);
                        } catch (Exception e) {
                            handlerError(e);
                        }
                        finally
                        {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                }
                            }
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                }
                            }
                        }

                        itemFileSize = file.length();
                        m3U8Ts.setFileSize(itemFileSize);
                        mHandler.sendEmptyMessage(WHAT_ON_PROGRESS);
                        curTs++;
                    }else {
                        curTs ++;
                        itemFileSize = file.length();
                        m3U8Ts.setFileSize(itemFileSize);
                    }
                }
            });
        }
    }


    private void handlerError(Throwable e) {
        if (!"Task running".equals(e.getMessage())) {
            stop();
        }
        if ("thread interrupted".equals(e.getMessage())) {
            return;
        }
        Message msg = Message.obtain();
        msg.obj = e;
        msg.what = WHAT_ON_ERROR;
        mHandler.sendMessage(msg);
    }

    public void stop() {
        if (netSpeedTimer != null) {
            netSpeedTimer.cancel();
            netSpeedTimer = null;
        }
        isRunning = false;
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public File getM3u8File(String url){
        try {
            return new File(MUtils.getSaveFileDir(url), m3u8FileName);
        }catch (Exception e){
            M3U8Log.e(e.getMessage());
        }
        return null;
    }

}
