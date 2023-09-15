package com.video.music.downloader.VideoDownloader;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;

public class MyApplication extends Application {

    public static final String CHANEL_ID="Alertify";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        createNotificationChangel();
        if (SettingsManager.isFirstRun(this))
            initFFmpeg();
    }

    private void createNotificationChangel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O  )
        {
            NotificationChannel serviceChanel=new NotificationChannel(CHANEL_ID,getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChanel);
        }
    }

    private void initFFmpeg() {

        //FFmpeg ffmpeg = FFmpeg.getInstance(this);

//        try {
//            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
//
//                @Override
//                public void onStart() {
//                }
//
//                @Override
//                public void onFailure() {
//                }
//
//                @Override
//                public void onSuccess() {
//                }
//
//                @Override
//                public void onFinish() {
//                }
//            });
//        } catch (FFmpegNotSupportedException e) {
//            // Handle if FFmpeg is not supported by device
//        }
    }



}
