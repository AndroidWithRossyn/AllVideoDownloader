package com.video.music.downloader.VideoDownloader.StatusSaver.utils;


import android.os.Environment;

import java.io.File;

public class MyConstants {
    public static final File STATUS_DIRECTORY =
            new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses");

    public static final File GB_DIRECTORY =
            new File(Environment.getExternalStorageDirectory() + File.separator + "GBWhatsApp/Media/.Statuses");

    public static final File BUSINESS_DIRECTORY =
            new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp Business/Media/.Statuses");

    public static final String APP_DIR = Environment.getExternalStorageDirectory() + File.separator + "StatusSaver";

    public static final String ADD_DIR = "/storage/emulated/0/StatusSaver/";

    public static final int THUMBSIZE = 256;

    public static final String WhatsAppDirectoryPath = "/storage/emulated/0/WhatsApp/Media/.Statuses/";
    public static final String GBDirectoryPath = "/storage/emulated/0/GBWhatsApp/Media/.Statuses/";
    public static final String BusinessDirectoryPath = "/storage/emulated/0/WhatsApp Business/Media/.Statuses/";
}
