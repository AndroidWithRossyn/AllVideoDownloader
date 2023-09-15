package com.coco.m3u8lib;

import com.coco.m3u8lib.bean.M3U8Task;



public abstract class OnM3U8DownloadListener {

    public void onDownloadItem(M3U8Task task, long itemFileSize, int totalTs, int curTs) {

    }

    public void onDownloadSuccess(M3U8Task task) {

    }

    public void onDownloadPause(M3U8Task task) {

    }

    public void onDownloadPending(M3U8Task task) {

    }

    public void onDownloadProgress(M3U8Task task) {

    }

    public void onDownloadPrepare(M3U8Task task) {

    }

    public void onDownloadError(M3U8Task task, Throwable errorMsg) {

    }

}
