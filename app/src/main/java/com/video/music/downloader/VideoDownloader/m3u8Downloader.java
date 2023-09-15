package com.video.music.downloader.VideoDownloader;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.coco.m3u8lib.M3U8Downloader;
import com.coco.m3u8lib.M3U8DownloaderConfig;
import com.coco.m3u8lib.OnM3U8DownloadListener;
import com.coco.m3u8lib.bean.M3U8Task;
import com.video.music.downloader.VideoDownloader.Utils.Commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public abstract class m3u8Downloader extends Thread {


    private String M3U8_URL;
    private Activity activity;
    private String baseDownloadDir;
    private boolean IsAudio=false;

    public abstract void onDownloaderError(String ErrorMessage);
    public abstract void onDownloaderSuccess(String FilePath,String BaseFileDir);
    public abstract void onDownloaderProgress(float Percentage);

    private static final String TAG = "M3U8SURAJ";
    private  String DownloadTmpFileName="BigBuckBunnyVideo";
    private String targetDownloadDir;
    private Context context;



    public m3u8Downloader(String _M3U8_URL,Context _context,String _baseDownloadDir)
    {
        M3U8_URL=_M3U8_URL;
        context=_context;
        baseDownloadDir=_baseDownloadDir;
    }

    @Override
    public void run() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                M3U8DownloaderConfig.build(context.getApplicationContext()).setSaveDir(baseDownloadDir).setDebugMode(true);
                M3U8Downloader.getInstance().setOnM3U8DownloadListener(onM3U8DownloadListener);
                M3U8Downloader.getInstance().download(M3U8_URL);
            }
        });


    }

    private OnM3U8DownloadListener onM3U8DownloadListener = new OnM3U8DownloadListener() {

        @Override
        public void onDownloadItem(M3U8Task task, long itemFileSize, int totalTs, int curTs) {
            super.onDownloadItem(task, itemFileSize, totalTs, curTs);
            Log.d(TAG,"onDownloadItem-" );
        }

        @Override
        public void onDownloadSuccess(M3U8Task task) {
            super.onDownloadSuccess(task);
            Log.d(TAG,"onDownloadSuccess");
            targetDownloadDir=task.getM3U8().getDirFilePath();
            String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", targetDownloadDir + "/local.m3u8", "copy", "copy", task.getM3U8().getDirFilePath() + "/"+ DownloadTmpFileName +".mp4");
            String[] command = cmd.split(" ");
            combineTsFiles(command);
        }

        @Override
            public void onDownloadPending(M3U8Task task) {
            super.onDownloadPending(task);
            Log.d(TAG,"onDownloadPending");
        }

        @Override
        public void onDownloadPause(M3U8Task task) {
            super.onDownloadPause(task);
            Log.d(TAG,"onDownloadPause");
        }

        @Override
        public void onDownloadProgress(final M3U8Task task) {
            super.onDownloadProgress(task);
            Log.d(TAG,"onDownloadProgress");
            if(Commons.isMyServiceRunning(m3u8DownloaderService.class,context))
            {
                onDownloaderProgress(task.getProgress());
            }
            else
            {
                M3U8Downloader.getInstance().cancelAndDelete(M3U8_URL);
            }

        }

        @Override
        public void onDownloadPrepare(final M3U8Task task) {
            super.onDownloadPrepare(task);
            Log.d(TAG,"onDownloadPrepare");
        }

        @Override
        public void onDownloadError(final M3U8Task task, Throwable errorMsg) {
            super.onDownloadError(task, errorMsg);
            Log.d(TAG,"onDownloadError- " + errorMsg);
            M3U8Downloader.getInstance().cancelAndDelete(M3U8_URL);
            onDownloaderError(errorMsg.getMessage());
        }

    };



    private void combineTsFiles(String[] command)
    {
        try
        {

            String M3u8Localfile=targetDownloadDir + "/local.m3u8";

            List<File> finishedFiles=new ArrayList<>();

            File m3u8Text=new File(M3u8Localfile);
            BufferedReader br = new BufferedReader(new FileReader(m3u8Text));
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.startsWith("#"))
                {
                    finishedFiles.add(new File(targetDownloadDir + "/" + line) );
                }
            }
            br.close();

            File file = new File(targetDownloadDir + "/" + DownloadTmpFileName + ".mp4");
            System.gc();
            if (file.exists())
                file.delete();
            else file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[4096];
            for (File f : finishedFiles) {
                FileInputStream fileInputStream = new FileInputStream(f);
                int len;
                while ((len = fileInputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, len);
                }
                fileInputStream.close();
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            onDownloaderSuccess(targetDownloadDir + "/" + DownloadTmpFileName + ".mp4",targetDownloadDir);


        }
        catch (Exception ex){
            onDownloaderError(ex.getMessage());
        }
    }

}
