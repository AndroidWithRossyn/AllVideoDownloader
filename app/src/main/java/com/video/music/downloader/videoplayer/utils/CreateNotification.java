package com.video.music.downloader.videoplayer.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.ImageView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.video.music.downloader.R;
import com.video.music.downloader.videoplayer.data.model.Song;
import com.video.music.downloader.videoplayer.service.NotificationService;


public class CreateNotification {
    public static final String CHANNEL_ID = "666";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_NEXT = "next";

    public static Notification notification;

    public static void createNotification(Context context, Song song, boolean isPlaying) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");

            ImageView ivCover = new ImageView(context);
            ivCover.setImageURI(song.getCoverUri());
            BitmapDrawable drawable = (BitmapDrawable) ivCover.getDrawable();
            Bitmap bitmapCover;

            if(drawable == null) {
                bitmapCover = BitmapFactory.decodeResource(context.getResources(), R.drawable.song_cover);
            } else {
                bitmapCover = drawable.getBitmap();
            }

            Intent intentPrevious = new Intent(context, NotificationService.class)
                        .setAction(ACTION_PREVIOUS);
            PendingIntent pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                        intentPrevious, PendingIntent.FLAG_IMMUTABLE);

            Intent intentPlay = new Intent(context, NotificationService.class)
                    .setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_IMMUTABLE);
            int idPausePlay;
            if(isPlaying) {
                idPausePlay = R.drawable.pause;
            } else {
                idPausePlay = R.drawable.play;
            }

            Intent intentNext = new Intent(context, NotificationService.class)
                        .setAction(ACTION_NEXT);
            PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_IMMUTABLE);

            //create notification
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setLargeIcon(bitmapCover)
                    .setSmallIcon(R.id.iv_song_cover)
                    .setContentTitle(song.getTitle())
                    .setContentText(song.getArtist())
                    .setOnlyAlertOnce(true)//show notification for only first time
                    .setShowWhen(false)
                    .addAction(R.drawable.previous, "Previous", pendingIntentPrevious)
                    .addAction(idPausePlay, "Play", pendingIntentPlay)
                    .addAction(R.drawable.next, "Next", pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(127, notification);
        }
    }
}
