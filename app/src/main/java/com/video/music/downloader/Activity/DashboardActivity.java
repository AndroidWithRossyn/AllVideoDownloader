package com.video.music.downloader.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.video.music.downloader.MusicPlayer.view.MusicPlayerActivity;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.MainActivity;
import com.video.music.downloader.statusandgallery.activity.WhatsAppStatusSaverActivity;
import com.video.music.downloader.videoplayer.VideoPlayerActivity;

public class DashboardActivity extends AppCompatActivity {

    RelativeLayout video_downloader_btn, status_saver_btn, video_player_btn, music_player_btn;
    ImageView settings, menu;
    private DrawerLayout drawerLayout;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_dashboard);
        activity = DashboardActivity.this;
//        AdUtils.showNativeAd(activity, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), findViewById(R.id.native_ads), true);
        video_downloader_btn = findViewById(R.id.video_downloader_btn);
        status_saver_btn = findViewById(R.id.status_saver_btn);
        video_player_btn = findViewById(R.id.video_player_btn);
        music_player_btn = findViewById(R.id.music_player_btn);
        settings = findViewById(R.id.settings);
        menu = findViewById(R.id.menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_lay);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        video_downloader_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(DashboardActivity.this, MainActivity.class));

            }
        });

        music_player_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(DashboardActivity.this, MusicPlayerActivity.class));

            }
        });

        video_player_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(DashboardActivity.this, VideoPlayerActivity.class));

            }
        });

        status_saver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(DashboardActivity.this, WhatsAppStatusSaverActivity.class));

            }
        });

    }

    public void videoplayer(View view) {


                startActivity(new Intent(DashboardActivity.this, VideoPlayerActivity.class));

    }

    public void musicplayer(View view) {


                startActivity(new Intent(DashboardActivity.this, MusicPlayerActivity.class));

    }

    public void downloader(View view) {

                startActivity(new Intent(DashboardActivity.this, MainActivity.class));

    }

    public void status(View view) {


                startActivity(new Intent(DashboardActivity.this, WhatsAppStatusSaverActivity.class));

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        exitDialog();
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle(getResources().getString(R.string.app_name));
//        builder.setMessage(getResources().getString(R.string.if_you_like_this_app_than_don_t_forget_to_rate_this_app_it_won_t_take_more_than_minutes));
        builder.setMessage("Are you sure to exit?");

        builder.setIcon(getResources().getDrawable(R.drawable.ic_logo));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                dialog.dismiss();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Rate App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rateApp();
                dialog.dismiss();
            }
        });

        /*builder.setNegativeButtonIcon(getResources().getDrawable(R.drawable.btn_share));
        builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.btn_rate));
        builder.setNeutralButtonIcon(getResources().getDrawable(R.drawable.btn_privacy));*/

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.black));


    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}