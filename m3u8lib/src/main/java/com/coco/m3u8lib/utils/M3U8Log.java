package com.coco.m3u8lib.utils;

import android.util.Log;

import com.coco.m3u8lib.M3U8DownloaderConfig;

public class M3U8Log {

    private static String TAG = "M3U8Log";

    public static void d(String msg){
        if (M3U8DownloaderConfig.isDebugMode()) Log.d(TAG, msg);
    }

    public static void e(String msg){
        if (M3U8DownloaderConfig.isDebugMode()) Log.e(TAG, msg);
    }


}
