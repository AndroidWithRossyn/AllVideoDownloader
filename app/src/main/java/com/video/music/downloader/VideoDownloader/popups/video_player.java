package com.video.music.downloader.VideoDownloader.popups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.video.music.downloader.VideoDownloader.Models.downloadable_resource_model;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Models.file_type;


public class video_player extends AppCompatDialogFragment {

    private downloadable_resource_model model;
    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    Context mContext;
    private ImageView imageView;
    private ConstraintLayout cl2;

    public video_player(downloadable_resource_model _model){
        model=_model;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.video_player,null);
        mContext=this.getContext();
        builder.setView(view)
                .setPositiveButton(mContext.getString(R.string.Close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(mContext.getString(R.string.DownloadNow), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();

                rename_dialog _dialog=new rename_dialog(model);
                _dialog.show(fragmentManager,"Example");
            }
        });

        mVideoView = view.findViewById(R.id.videoview);
        mBufferingTextView = view.findViewById(R.id.buffering_textview);
        imageView = view.findViewById(R.id.image_View);
        cl2 = view.findViewById(R.id.cl2);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        if (model.getFile_type().equals(file_type.IMAGE)) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(model.getURL()).into(imageView);
            cl2.setVisibility(View.GONE);
        } else {
            MediaController controller = new MediaController(view.getContext());
            controller.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(controller);
            imageView.setVisibility(View.GONE);
            cl2.setVisibility(View.VISIBLE);
        }


//        MediaController controller = new MediaController(view.getContext());
//        controller.setMediaPlayer(mVideoView);
//        mVideoView.setMediaController(controller);

        return  builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (model.getFile_type().equals(file_type.VIDEO)||model.getFile_type().equals(file_type.AUDIO)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if (model.getFile_type().equals(file_type.VIDEO)||model.getFile_type().equals(file_type.AUDIO)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                mVideoView.pause();
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if (model.getFile_type().equals(file_type.VIDEO)||model.getFile_type().equals(file_type.AUDIO)) {
            releasePlayer();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if (model.getFile_type().equals(file_type.VIDEO)||model.getFile_type().equals(file_type.AUDIO)) {
            outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
        }
    }

    private void initializePlayer(){
        mBufferingTextView.setVisibility(VideoView.VISIBLE);
        Uri videoUri = getMedia(model.getURL());
        mVideoView.setVideoURI(videoUri);

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //Toasty.error(mContext,"Sorry ! this file can\'t be played !", Toast.LENGTH_LONG,true);
                return false;
            }
        });

        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            mVideoView.seekTo(1);
                        }

                        mVideoView.start();
                    }
                });
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mVideoView.seekTo(0);
                    }
                });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {

            // you can also put a video file in raw package and get file from there as shown below

            return Uri.parse("android.resource://" + getActivity().getPackageName() +
                    "/raw/" + mediaName);


        }
    }

}
