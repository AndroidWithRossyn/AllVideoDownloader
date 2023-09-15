package com.video.music.downloader.MusicPlayer.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.video.music.downloader.R;
import com.video.music.downloader.MusicPlayer.viewmodel.SharedViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerViewFragment extends Fragment {
    View rootView;
    SharedViewModel sharedViewModel;
    ImageView backBtn,blurBg;
    TextView  titleView, progressDuration, totalDuration, prevBtn, nextBtn;
    ConstraintLayout controlsWrapper;
    CircleImageView artworkView;
    ImageButton playPauseBtn;
    SeekBar seekBar;

    ExoPlayer player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_player_view, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //assign
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        blurBg = rootView.findViewById(R.id.blurBg);
        backBtn = rootView.findViewById(R.id.mIVBack);
        artworkView = rootView.findViewById(R.id.artworkView);
        controlsWrapper = rootView.findViewById(R.id.controlsWrapper);
        titleView = rootView.findViewById(R.id.titleView);
        seekBar = rootView.findViewById(R.id.seekBar);
        progressDuration = rootView.findViewById(R.id.progressDuration);
        totalDuration = rootView.findViewById(R.id.totalDuration);
        prevBtn = rootView.findViewById(R.id.prevBtn);
        nextBtn = rootView.findViewById(R.id.nextBtn);
        playPauseBtn = rootView.findViewById(R.id.playPauseBtn);

        //back btn clicked
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        //getting the player
        gettingPlayer();

    }

    private void gettingPlayer() {
        sharedViewModel.getPlayer().observe(requireActivity(),livePlayer->{
            if (livePlayer != null){
                player = livePlayer;
                //player controls
                playerControls(player);
            }
        });
    }

    private void playerControls(ExoPlayer player) {
                //player listener
          player.addListener(new Player.Listener() {
              @Override
              public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                  assert mediaItem != null;
                  titleView.setText(mediaItem.mediaMetadata.title);
                  progressDuration.setText(getReadableTime((int) player.getCurrentPosition()));
                  seekBar.setProgress((int) player.getCurrentPosition());
                  totalDuration.setText(getReadableTime((int) player.getDuration()));
                  seekBar.setMax((int) player.getDuration());
                  playPauseBtn.setImageResource(R.drawable.pause);
                  showCurrentArtwork();
                  updatePlayerPositionProgress();
                  artworkView.setAnimation(loadRotation());
                  if (!player.isPlaying()){
                      player.play();
                  }

              }

              @Override
              public void onPlaybackStateChanged(int playbackState) {
                   if (playbackState == ExoPlayer.STATE_READY){
                       titleView.setText(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.title);
                       playPauseBtn.setImageResource(R.drawable.pause);
                       progressDuration.setText(getReadableTime((int) player.getCurrentPosition()));
                       seekBar.setProgress((int) player.getCurrentPosition());
                       totalDuration.setText(getReadableTime((int) player.getDuration()));
                       seekBar.setMax((int) player.getDuration());
                       showCurrentArtwork();
                       updatePlayerPositionProgress();
                       artworkView.setAnimation(loadRotation());

                   }else {
                       playPauseBtn.setImageResource(R.drawable.play);
                   }
              }
          });
        //checking if the player is playing
        if (player.isPlaying()){
            titleView.setText(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.title);
            progressDuration.setText(getReadableTime((int) player.getCurrentPosition()));
            seekBar.setProgress((int) player.getCurrentPosition());
            totalDuration.setText(getReadableTime((int) player.getDuration()));
            seekBar.setMax((int) player.getDuration());
            playPauseBtn.setImageResource(R.drawable.pause);

            showCurrentArtwork();
            updatePlayerPositionProgress();
            artworkView.setAnimation(loadRotation());
        }

        //player btns
        nextBtn.setOnClickListener(view -> {
            if (player.hasNextMediaItem()){
                player.seekToNext();
                showCurrentArtwork();
                updatePlayerPositionProgress();
                artworkView.setAnimation(loadRotation());
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.hasPreviousMediaItem()){
                    player.seekToPrevious();
                    showCurrentArtwork();
                    updatePlayerPositionProgress();
                    artworkView.setAnimation(loadRotation());
                }
            }
        });

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()){
                    player.pause();
                    playPauseBtn.setImageResource(R.drawable.play);
                    artworkView.clearAnimation();
                }else {
                    player.play();
                    playPauseBtn.setImageResource(R.drawable.pause);
                    artworkView.setAnimation(loadRotation());
                }
            }
        });

        //set seek bar change listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue =0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               seekBar.setProgress(progressValue);
               progressDuration.setText(getReadableTime(progressValue));
               player.seekTo(progressValue);
            }
        });
    }

    private Animation loadRotation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(10000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return  rotateAnimation;
    }

    private void updatePlayerPositionProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying()){
                    progressDuration.setText(getReadableTime((int) player.getCurrentPosition()));
                    seekBar.setProgress((int) player.getCurrentPosition());
                }

                //repeat calling method
                updatePlayerPositionProgress();
            }
        }, 1000);
    }

    private void showCurrentArtwork() {
        artworkView.setImageURI(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.artworkUri);
       blurBg.setImageURI(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.artworkUri);

       if (artworkView.getDrawable() == null){
           artworkView.setImageResource(R.drawable.default_albumart);
           blurBg.setImageResource(R.drawable.default_albumart);
       }

    }

    //get total duration text method
    String getReadableTime(int totalDuration){
        String time;
        int hrs = totalDuration/(1000*60*60);
        int min = (totalDuration%(1000*60*60))/(1000*60);
        int secs = (((totalDuration%(1000*60*60))%(1000*60*60))%(1000*60))/1000;

        if (hrs<1){ time = min +":"+secs; }
        else{
            time = hrs +":"+ min +":"+secs;
        }
        return  time;
    }
}