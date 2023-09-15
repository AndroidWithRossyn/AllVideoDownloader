package com.video.music.downloader.statusandgallery.socialmediaPages;

import static com.video.music.downloader.statusandgallery.utils.Utils.shareImage;
import static com.video.music.downloader.statusandgallery.utils.Utils.shareImageVideoOnWhatsapp;
import static com.video.music.downloader.statusandgallery.utils.Utils.shareVideo;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;


import com.video.music.downloader.R;
import com.video.music.downloader.databinding.ActivityFullView1Binding;
////import com.video.music.downloader.statusandgallery.AdsUtils.FirebaseADHandlers.AdUtils;
////import com.video.music.downloader.statusandgallery.AdsUtils.Interfaces.AppInterfaces;
import com.video.music.downloader.statusandgallery.socialmediaPages.adapter.ShowImagesAdapter;
import com.video.music.downloader.statusandgallery.utils.AppLangSessionManager;
import com.video.music.downloader.statusandgallery.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class FullViewActivity extends AppCompatActivity {
    private ActivityFullView1Binding binding;
    private FullViewActivity activity;
    private ArrayList<File> fileArrayList;
    private int Position = 0;
    ShowImagesAdapter showImagesAdapter;
    AppLangSessionManager appLangSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_view1);
        activity = this;
        appLangSessionManager = new AppLangSessionManager(activity);
        setLocale(appLangSessionManager.getLanguage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.main_color2));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileArrayList = (ArrayList<File>) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position", 0);
        }

        initViews();

    }

    public void initViews() {
        showImagesAdapter = new ShowImagesAdapter(this, fileArrayList, FullViewActivity.this);
        binding.vpView.setAdapter(showImagesAdapter);
        binding.vpView.setCurrentItem(Position);

        binding.vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Position = arg0;
                System.out.println("Current position==" + Position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });

        binding.imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean b = fileArrayList.get(Position).delete();
                        if (b) {
                            deleteFileAA(Position);
                        }
                    }
                });
                ab.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = ab.create();
                alert.setTitle(getResources().getString(R.string.do_u_want_to_dlt));
                alert.show();
            }
        });
        binding.imShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")) {
                    Log.d("SSSSS", "onClick: " + fileArrayList.get(Position) + "");
                    shareVideo(activity, fileArrayList.get(Position).getPath());
                } else {
                    shareImage(activity, fileArrayList.get(Position).getPath());
                }
            }
        });
        binding.imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")) {
                    shareImageVideoOnWhatsapp(activity, fileArrayList.get(Position).getPath(), true);
                } else {
                    shareImageVideoOnWhatsapp(activity, fileArrayList.get(Position).getPath(), false);
                }
            }
        });
        binding.imClose.setOnClickListener(v -> {
//            AdUtils.showInterstitialAd(FullViewActivity.this, new AppInterfaces.InterStitialADInterface() {
//                @Override
//                public void adLoadState(boolean isLoaded) {
                    FullViewActivity.super.onBackPressed();
//                }
//            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    public void deleteFileAA(int position) {
        fileArrayList.remove(position);
        showImagesAdapter.notifyDataSetChanged();
        Utils.setToast(activity, getResources().getString(R.string.file_deleted));
        if (fileArrayList.size() == 0) {
            onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);



    }

    @Override
    public void onBackPressed() {
//        AdUtils.showInterstitialAd(FullViewActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
                FullViewActivity.super.onBackPressed();
//            }
//        });
    }
}