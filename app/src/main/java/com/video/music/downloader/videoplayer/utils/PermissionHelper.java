package com.video.music.downloader.videoplayer.utils;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.video.music.downloader.videoplayer.VideoPlayerActivity;


public class PermissionHelper {
    public static boolean checkPermission(String permission, int requestCode, VideoPlayerActivity musicPlayerActivity) {
        if (ContextCompat.checkSelfPermission(musicPlayerActivity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(musicPlayerActivity, new String[] { permission }, requestCode);
            return false;
        } else {
            return true;
        }
    }
}
