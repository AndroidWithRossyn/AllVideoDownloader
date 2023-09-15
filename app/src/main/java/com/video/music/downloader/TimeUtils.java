package com.video.music.downloader;

import android.annotation.SuppressLint;

public class TimeUtils {
    @SuppressLint("DefaultLocale")
    public static String toFormattedTime(int i) {
        int i2 = i / 3600000;
        int i3 = i - (3600000 * i2);
        int i4 = (i3 - ((i3 / 60000) * 60000)) / 1000;
        if (i2 > 0) {
            return String.format("%02d:%02d:%02d", i2, 0, i4);
        }
        return String.format("%02d:%02d", 0, i4);
    }
}
