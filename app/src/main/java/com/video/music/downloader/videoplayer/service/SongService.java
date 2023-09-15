package com.video.music.downloader.videoplayer.service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

import com.video.music.downloader.videoplayer.data.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SongService extends Service {
    private static final int PROGRESS_UPDATE_DELAY = 10;

    private MediaPlayer mediaPlayer;
    private final MediaPlayerListener mediaPlayerListener = new MediaPlayerListener();

    private final IBinder musicBinder = new SongBinder();
    private ICallback callback;
    private final Handler handlerProgress = new Handler();
    private final UpdateProgressTask updateProgressTask = new UpdateProgressTask();

    private List<Song> songs = new ArrayList<>();
    private int currentPosition = -1;

    public static void start(Context context, ServiceConnection serviceConnection) {
        Intent playIntent = new Intent(context, SongService.class);
        context.bindService(playIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        context.startService(playIntent);
    }

    public interface ICallback {
        void onBeginSong(int position);
        void onProgressChange(int maxProgress, int progress);
        void onDurationChange(int duration);
        void onPlaySong();
        void onPauseSong();
        void onFinishSong();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void callProgressChange() {
        callback.onProgressChange(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeMusicPlayer();
    }

    public void initializeMusicPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(mediaPlayerListener);
        mediaPlayer.setOnCompletionListener(mediaPlayerListener);
        mediaPlayer.setOnErrorListener(mediaPlayerListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        handlerProgress.removeCallbacks(updateProgressTask);
        stopForeground(true);
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void setMusic(List<Song> songs){
        this.songs = songs;
    }

    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }

    public void beginSong(int songPosition) {
        mediaPlayer.stop();
        mediaPlayer.reset();

        this.currentPosition = songPosition;
        Song playMusic = songs.get(currentPosition);
        long currSong = playMusic.getId();
        Uri trackUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSong);

        try {
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void pauseSong() {
        mediaPlayer.pause();
        callback.onPauseSong();
        handlerProgress.removeCallbacks(updateProgressTask);
    }

    public void playSong() {
        if (currentPosition == -1) {
            currentPosition = 0;
            beginSong(currentPosition);
            return;
        }
        mediaPlayer.start();
        callback.onPlaySong();
        handlerProgress.postDelayed(updateProgressTask, PROGRESS_UPDATE_DELAY);
    }

    public void setSongProgress(int progress) {
        mediaPlayer.seekTo(progress);
    }

    class MediaPlayerListener implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if(songs != null) {
                mediaPlayer.getTrackInfo();
                callback.onFinishSong();
                handlerProgress.removeCallbacks(updateProgressTask);
            }
        }

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            return false;
        }

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
            callback.onBeginSong(currentPosition);
            callback.onDurationChange(mediaPlayer.getDuration());
            handlerProgress.postDelayed(updateProgressTask, PROGRESS_UPDATE_DELAY);
        }
    }

    class UpdateProgressTask implements Runnable {
        @Override
        public void run() {
            callback.onProgressChange(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());
            handlerProgress.postDelayed(updateProgressTask, PROGRESS_UPDATE_DELAY);
        }
    }
}