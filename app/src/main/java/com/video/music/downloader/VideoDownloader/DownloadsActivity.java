package com.video.music.downloader.VideoDownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.video.music.downloader.VideoDownloader.Adapters.TabAdapter;
import com.video.music.downloader.R;

public class DownloadsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageView backbt, whatsapp;
    AdView adViewDetailAct;
//    com.facebook.ads.AdView fb_banner_container_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_downloads);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        backbt = findViewById(R.id.imBack);
        whatsapp = findViewById(R.id.whatsapp);


        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), DownloadActivity.class));
//            }
//        });

        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Audios"));
        tabLayout.addTab(tabLayout.newTab().setText("Images"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabAdapter tabAdapter = new TabAdapter(this);
        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText("Videos");
                        } else if (position == 1) {
                            tab.setText("Audios");
                        } else if (position == 2) {
                            tab.setText("Images");
                        }
                    }
                }).attach();

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        adViewDetailAct=findViewById(R.id.adViewDetailAct);
//        AudienceNetworkAds.initialize(this);
//        if( getResources().getString(R.string.Ads).equals("ADMOB") ){
//            AdRequest adRequest = new AdRequest.Builder().build();
//            adViewDetailAct.loadAd(adRequest);
//        }
//        else if (getResources().getString(R.string.Ads).equals("FACEBOOK")){
//            adViewDetailAct.setVisibility(View.GONE);
//            fb_banner_container_detail=new com.facebook.ads.AdView(this, getResources().getString(R.string.FB_Banner_Ad_PlacemaneId_1), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
//            LinearLayout adContainer = (LinearLayout) findViewById(R.id.fb_banner_container_detail);
//            adContainer.addView(fb_banner_container_detail);
//            fb_banner_container_detail.loadAd();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DownloadsActivity.this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}