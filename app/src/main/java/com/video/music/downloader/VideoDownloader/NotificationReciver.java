package com.video.music.downloader.VideoDownloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String whichAction = intent.getAction();

        switch (whichAction)
        {
            case "quit_action":
            {
                context.stopService(new Intent(context, m3u8DownloaderService.class));
            }
                return;
        }
    }
}
