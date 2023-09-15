package com.video.music.downloader;

import android.Manifest;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class PermitConstant {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static final String Manifest_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static final String Manifest_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String Manifest_CAMERA = Manifest.permission.CAMERA;
}