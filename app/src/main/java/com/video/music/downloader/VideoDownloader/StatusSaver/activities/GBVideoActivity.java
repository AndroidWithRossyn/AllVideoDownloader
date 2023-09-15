package com.video.music.downloader.VideoDownloader.StatusSaver.activities;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.video.music.downloader.VideoDownloader.StatusSaver.Adapters.VideoAdapter;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.StatusSaver.utils.MyConstants;

import static com.video.music.downloader.VideoDownloader.StatusSaver.fragments.GBVideoFragment.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class GBVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaController.MediaPlayerControl, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener {
    String video_path = "", myPackage, myPackages, type = "", message;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton saveButton, shareButton, repostButton, deleteButton;
    int video_index;
    private int position = 0;
    ArrayList<String> list = new ArrayList<String>();
    private MediaController mediaController;
    String path = MyConstants.ADD_DIR;
    MediaPlayer mp;

    private Handler handler = new Handler();

    SurfaceView videoSurface;
    SurfaceHolder videoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player2);

        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        saveButton = (FloatingActionButton) findViewById(R.id.save);
        shareButton = (FloatingActionButton) findViewById(R.id.share);
        repostButton = (FloatingActionButton) findViewById(R.id.repost);
        deleteButton = (FloatingActionButton) findViewById(R.id.delete);

        Intent intent = getIntent();
        message = intent.getStringExtra(VideoAdapter.POSITION);
        video_index = Integer.parseInt(message);
        video_path = intent.getStringExtra("video");
        type = intent.getStringExtra("type");
        myPackage = "com.gbwhatsapp";
        myPackages = "GB WhatsApp";

        mp = new MediaPlayer();
        if(mediaController == null){
            mediaController = new MediaController(GBVideoActivity.this);
        }

        playVideo(video_index);

        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index < (data.size() - 1)) {
                    video_index++;
                    mp.reset();
                    nextOrPrev(video_index);
                } else {
                    //video_index = 0;
                    //nextOrPrev(video_index);
                    v.setEnabled(false);
                    v.setAlpha(0.3f);
                }

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index > 0) {
                    video_index--;
                    mp.reset();
                    nextOrPrev(video_index);
                } else {
                    v.setAlpha(0.3f);
                    video_index = 0;
                    nextOrPrev(video_index);
                }
            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                copyFileOrDirectory(data.get(video_index).getFull_path(), path);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareNow(video_path);
            }
        });
        repostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendTo("video/*", video_path, myPackage);

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDialog(video_path);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mediaController.isShowing()) {
                mediaController.hide();
                materialDesignFAM.setEnabled(true);
                // materialDesignFAM.setAlpha(1f);
            } else {
                mediaController.show(3000);
                materialDesignFAM.setEnabled(false);
                // materialDesignFAM.setAlpha(0.3f);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mp == null) {
            return;
        }
        mp.setDisplay(holder);
        mp.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                return  true;
            }else if(event.getAction() == KeyEvent.ACTION_UP){
                ((Activity) this).onBackPressed();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void playVideo(int pos) {
        try {
            mp.setDataSource(data.get(pos).getFull_path());
            //mp.setOnBufferingUpdateListener(this);
            mp.setOnCompletionListener(this);
            mp.setOnPreparedListener(this);
            mp.setScreenOnWhilePlaying(true);
            mp.setOnVideoSizeChangedListener(this);
            mediaController.setMediaPlayer(this);
            // mediaController = new MediaController(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextOrPrev(int pos) {
        try {
            mp.setDataSource(data.get(pos).getFull_path());
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoChangeMaker();
                    mp.start();
                }
            });
            mp.prepareAsync();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        } catch(IllegalStateException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        mp.start();
    }

    @Override
    public void pause() {
        mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mp.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        video_index++;
        if (video_index < (data.size())) {
            mp.reset();
            try {
                mp.setDataSource(data.get(video_index).getFull_path());
                mp.setOnPreparedListener(this);
                mp.prepareAsync();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mp.pause();
            mediaController.show(3000);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("TAG", "onPrepared called");
        videoChangeMaker();
        mp.start();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    public void videoChangeMaker(){
        int videoWidth = mp.getVideoWidth();
        int videoHeight = mp.getVideoHeight();
        float videoProportion = (float) videoWidth/ (float) videoHeight;
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float screenProportion = (float) screenWidth/ (float) screenHeight;
        android.view.ViewGroup.LayoutParams lp = videoSurface.getLayoutParams();
        if(videoProportion > screenProportion){
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth/videoProportion);
        }else {
            lp.width = (int) (videoProportion * (float)screenHeight);
            lp.height = screenHeight;
        }
        videoSurface.setLayoutParams(lp);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.myanchor));
        handler.post(new Runnable() {

            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        position = mp.getCurrentPosition();
        mp.start();
        mp.pause();
    }

    public void tranNow(){
        final int duration = 200;
        final int colorFrom = Color.parseColor("#10000000");
        final int colorTo = Color.parseColor("#000000");
        ColorDrawable[] color = {new ColorDrawable(colorFrom), new ColorDrawable(colorTo)};
        TransitionDrawable transitionDrawable = new TransitionDrawable(color);
        videoSurface.setBackground(transitionDrawable);
        transitionDrawable.startTransition(duration);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position", position);
        mp.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(position > 0) {
            mp.seekTo(position);
            mediaController.show(3000);
        }
        //playVideo(video_index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            Toast.makeText(getApplicationContext(), "Video Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(destFile));
            this.sendBroadcast(intent);

        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void shareNow(String path){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        File file = new File(path);
        Uri uri = FileProvider.getUriForFile(this, "com.techvinz.wahstatussaver.FileProvider", file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("video/*");
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    public void sendTo(String type, String path, String myPackage) {

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(myPackage, PackageManager.GET_META_DATA);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.parse(path);
            sharingIntent.setType(type);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setPackage(myPackage);//package name of the app
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, myPackages+" not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoChangeMaker();
    }

    private void startDialog(final String myPath) {
        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(GBVideoActivity.this);
        myAlertDialog.setTitle("Delete Status");
        myAlertDialog.setMessage("You are about to delete this video permanently. This action cannot be undone");

        myAlertDialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        File file = new File(myPath);
                        file.delete();
                        Toast.makeText(GBVideoActivity.this, "Status Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        myAlertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //
                    }
                });
        myAlertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
