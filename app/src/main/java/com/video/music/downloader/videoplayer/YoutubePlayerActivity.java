package com.video.music.downloader.videoplayer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import com.video.music.downloader.BuildConfig;
import com.video.music.downloader.R;
import com.video.music.downloader.databinding.ActivityYoutubeBinding;
////import com.video.music.downloader.statusandgallery.AdsUtils.FirebaseADHandlers.AdUtils;
////import com.video.music.downloader.statusandgallery.AdsUtils.Interfaces.AppInterfaces;
//import com.video.music.downloader.statusandgallery.AdsUtils.Utils.Constants;
import com.video.music.downloader.videoplayer.data.model.youtube.YoutubeVideo;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private YoutubeVideo video = null;
    private ActivityYoutubeBinding binding;

    private YouTubePlayer mYoutubePlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = ActivityYoutubeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.main_color3));
        }

        Intent intent = getIntent();
        if (intent != null) {
            video = intent.getParcelableExtra("video");
        }
        binding.youtubePlayer.initialize(BuildConfig.YOTUBE_DATA_TOKEN, this);
        binding.tvTitleVideo.setText(video.getSnippet().getTitle());
        binding.tvTitleArtist.setText(video.getSnippet().getChannelTitle());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            youTubePlayer.cueVideo(video.getId().getVideoId());
        }
        mYoutubePlayer = youTubePlayer;
    }

    @Override
    public void onBackPressed() {
//        AdUtils.showInterstitialAd(YoutubePlayerActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
                YoutubePlayerActivity.super.onBackPressed();
//            }
//        });
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Toast.makeText(YoutubePlayerActivity.this, errorReason.toString(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }

}
