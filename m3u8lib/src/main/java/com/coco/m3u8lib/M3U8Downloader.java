package com.coco.m3u8lib;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.List;

import com.coco.m3u8lib.DownloadQueue;
import com.coco.m3u8lib.M3U8DownloadTask;
import com.coco.m3u8lib.OnDeleteTaskListener;
import com.coco.m3u8lib.OnM3U8DownloadListener;
import com.coco.m3u8lib.OnTaskDownloadListener;
import com.coco.m3u8lib.bean.M3U8;
import com.coco.m3u8lib.bean.M3U8Task;
import com.coco.m3u8lib.bean.M3U8TaskState;
import com.coco.m3u8lib.utils.M3U8Log;
import com.coco.m3u8lib.utils.MUtils;

public class M3U8Downloader {

    private long currentTime;
    private M3U8Task currentM3U8Task;
    private com.coco.m3u8lib.DownloadQueue downLoadQueue;
    private M3U8DownloadTask m3U8DownLoadTask;
    private OnM3U8DownloadListener onM3U8DownloadListener;

    private M3U8Downloader() {
        downLoadQueue = new com.coco.m3u8lib.DownloadQueue();
        m3U8DownLoadTask = new M3U8DownloadTask();
    }

    private static class SingletonHolder{
        static M3U8Downloader instance = new M3U8Downloader();
    }

    public static M3U8Downloader getInstance(){
        return SingletonHolder.instance;
    }

    private boolean isQuicklyClick(){
        boolean result = false;
        if (System.currentTimeMillis() - currentTime <= 100){
            result = true;
            M3U8Log.d("is too quickly click!");
        }
        currentTime = System.currentTimeMillis();
        return result;
    }


    private void downloadNextTask() {
        startDownloadTask(downLoadQueue.poll());
    }

    private void pendingTask(M3U8Task task){
        task.setState(M3U8TaskState.PENDING);
        if (onM3U8DownloadListener != null){
            onM3U8DownloadListener.onDownloadPending(task);
        }
    }


    public void download(String url){
        if (TextUtils.isEmpty(url) || isQuicklyClick())return;
        M3U8Task task = new M3U8Task(url);
        if (downLoadQueue.contains(task)){
            task = downLoadQueue.getTask(url);
            if (task.getState() == M3U8TaskState.PAUSE || task.getState() == M3U8TaskState.ERROR){
                startDownloadTask(task);
            }else {
                pause(url);
            }
        }else {
            downLoadQueue.offer(task);
            startDownloadTask(task);
        }
    }


    public void pause(String url){
        if (TextUtils.isEmpty(url))return;
        M3U8Task task = downLoadQueue.getTask(url);
        if (task != null) {
            task.setState(M3U8TaskState.PAUSE);

            if (onM3U8DownloadListener != null) {
                onM3U8DownloadListener.onDownloadPause(task);
            }

            if (url.equals(currentM3U8Task.getUrl())) {
                m3U8DownLoadTask.stop();
                downloadNextTask();
            } else {
                downLoadQueue.remove(task);
            }
        }
    }

    public void pause(List<String> urls){
        if (urls == null || urls.size() == 0)return;
        boolean isCurrentTaskPause = false;
        for (String url : urls){
            if (downLoadQueue.contains(new M3U8Task(url))){
                M3U8Task task = downLoadQueue.getTask(url);
                if (task != null){
                    task.setState(M3U8TaskState.PAUSE);
                    if (onM3U8DownloadListener != null){
                        onM3U8DownloadListener.onDownloadPause(task);
                    }
                    if (task.equals(currentM3U8Task)){
                        m3U8DownLoadTask.stop();
                        isCurrentTaskPause = true;
                    }
                    downLoadQueue.remove(task);
                }
            }
        }
        if (isCurrentTaskPause)startDownloadTask(downLoadQueue.peek());
    }

    public boolean checkM3U8IsExist(String url){
        try {
            return m3U8DownLoadTask.getM3u8File(url).exists();
        }catch (Exception e){
            M3U8Log.e(e.getMessage());
        }
        return false;
    }

    public String getM3U8Path(String url){
        return m3U8DownLoadTask.getM3u8File(url).getPath();
    }

    public boolean isRunning(){
        return m3U8DownLoadTask.isRunning();
    }


    public boolean isCurrentTask(String url){
        return !TextUtils.isEmpty(url)
                && downLoadQueue.peek() != null
                && downLoadQueue.peek().getUrl().equals(url);
    }


    public void setOnM3U8DownloadListener(OnM3U8DownloadListener onM3U8DownloadListener) {
        this.onM3U8DownloadListener = onM3U8DownloadListener;
    }

    public void setEncryptKey(String encryptKey){
        m3U8DownLoadTask.setEncryptKey(encryptKey);
    }

    public String getEncryptKey(){
        return m3U8DownLoadTask.getEncryptKey();
    }

    private void startDownloadTask(M3U8Task task){
        if (task == null)return;
        pendingTask(task);
        if (!downLoadQueue.isHead(task)){
            M3U8Log.d("start download task, but task is running: " + task.getUrl());
            return;
        }

        if (task.getState() == M3U8TaskState.PAUSE){
            M3U8Log.d("start download task, but task has pause: " + task.getUrl());
            return;
        }
        try {
            currentM3U8Task = task;
            M3U8Log.d("====== start downloading ===== " + task.getUrl());
            m3U8DownLoadTask.download(task.getUrl(), onTaskDownloadListener);
        }catch (Exception e){
            M3U8Log.e("startDownloadTask Error:"+e.getMessage());
        }
    }

    public void cancel(String url){
        pause(url);
    }

    public void cancel(List<String> urls){
        pause(urls);
    }


    public void cancelAndDelete(final String url, @Nullable final OnDeleteTaskListener listener){
        pause(url);
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
                if (listener != null) {
                    if (isDelete) {
                        listener.onSuccess();
                    } else {
                        listener.onFail();
                    }
                }
            }
        }).start();
    }

    public void cancelAndDelete(final String url){
        cancel(url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
            }
        }).start();
    }

    public void cancelAndDelete(final List<String> urls, @Nullable final OnDeleteTaskListener listener){
        pause(urls);
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = true;
                for (String url : urls){
                    isDelete = isDelete && MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
                }
                if (listener != null) {
                    if (isDelete) {
                        listener.onSuccess();
                    } else {
                        listener.onFail();
                    }
                }
            }
        }).start();
    }


    private OnTaskDownloadListener onTaskDownloadListener = new OnTaskDownloadListener() {
        private long lastLength;
        private float downloadProgress;

        @Override
        public void onStartDownload(int totalTs, int curTs) {
            M3U8Log.d("onStartDownload: "+totalTs+"|"+curTs);

            currentM3U8Task.setState(M3U8TaskState.DOWNLOADING);
            downloadProgress = 1.0f * curTs / totalTs;
        }

        @Override
        public void onDownloading(long totalFileSize, long itemFileSize, int totalTs, int curTs) {
            if (!m3U8DownLoadTask.isRunning())return;
            M3U8Log.d("onDownloading: "+totalFileSize+"|"+itemFileSize+"|"+totalTs+"|"+curTs);

            downloadProgress = 1.0f * curTs / totalTs;

            if (onM3U8DownloadListener != null){
                onM3U8DownloadListener.onDownloadItem(currentM3U8Task, itemFileSize, totalTs, curTs);
            }
        }

        @Override
        public void onSuccess(M3U8 m3U8) {
            m3U8DownLoadTask.stop();
            currentM3U8Task.setM3U8(m3U8);
            currentM3U8Task.setState( M3U8TaskState.SUCCESS);
            if (onM3U8DownloadListener != null) {
                onM3U8DownloadListener.onDownloadSuccess(currentM3U8Task);
            }
            M3U8Log.d("m3u8 Downloader onSuccess: "+ m3U8);
            downloadNextTask();

        }

        @Override
        public void onProgress(long curLength) {
            if (curLength - lastLength > 0) {
                currentM3U8Task.setProgress(downloadProgress);
                currentM3U8Task.setSpeed(curLength - lastLength);
                if (onM3U8DownloadListener != null ){
                    onM3U8DownloadListener.onDownloadProgress(currentM3U8Task);
                }
                lastLength = curLength;
            }
        }

        @Override
        public void onStart() {
            currentM3U8Task.setState(M3U8TaskState.PREPARE);
            if (onM3U8DownloadListener != null){
                onM3U8DownloadListener.onDownloadPrepare(currentM3U8Task);
            }
            M3U8Log.d("onDownloadPrepare: "+ currentM3U8Task.getUrl());
        }

        @Override
        public void onError(Throwable errorMsg) {
            if (errorMsg.getMessage() != null && errorMsg.getMessage().contains("ENOSPC")){
                currentM3U8Task.setState(M3U8TaskState.ENOSPC);
            }else {
                currentM3U8Task.setState(M3U8TaskState.ERROR);
            }
            if (onM3U8DownloadListener != null) {
                onM3U8DownloadListener.onDownloadError(currentM3U8Task, errorMsg);
            }
            M3U8Log.e("onError: " + errorMsg.getMessage());
            downloadNextTask();
        }

    };

}
