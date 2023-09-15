package com.coco.m3u8lib;


import com.coco.m3u8lib.BaseListener;
import com.coco.m3u8lib.bean.M3U8;


interface OnTaskDownloadListener extends BaseListener {

    void onStartDownload(int totalTs, int curTs);

    void onDownloading(long totalFileSize, long itemFileSize, int totalTs, int curTs);

    void onSuccess(M3U8 m3U8);

    void onProgress(long curLength);

    void onStart();

    void onError(Throwable errorMsg);
}
