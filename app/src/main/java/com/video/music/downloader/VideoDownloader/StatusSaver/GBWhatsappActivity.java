package com.video.music.downloader.VideoDownloader.StatusSaver;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

//import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.StatusSaver.fragments.GBImageFragment;
import com.video.music.downloader.VideoDownloader.StatusSaver.fragments.GBVideoFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

public class GBWhatsappActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabslayout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    private AdView adView;
    //com.facebook.ads.AdView fb_AdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity);

        ButterKnife.bind(this);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GBWhatsappActivity.super.onBackPressed();
            }
        });

        toolbar.setTitle("GB WhatsApp Statuses");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tabslayout);


        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

//        MobileAds.initialize(this, "ca-app-pub-5358683458645157~2811577273");
//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        adView=findViewById(R.id.adViewgb);
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        AudienceNetworkAds.initialize(this);
//        if( getResources().getString(R.string.Ads).equals("ADMOB") ){
//            AdRequest adRequest = new AdRequest.Builder().build();
//
//            adView.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    Log.d("adError","Ad LOaded");
//                }
//
//                @Override
//                public void onAdFailedToLoad(LoadAdError adError) {
//                    // Code to be executed when an ad request fails.
//                    Log.d("adError",adError.getMessage());
//                }
//
//                @Override
//                public void onAdOpened() {
//                    // Code to be executed when an ad opens an overlay that
//                    // covers the screen.
//                }
//
//                @Override
//                public void onAdClicked() {
//                    // Code to be executed when the user clicks on an ad.
//                }
//
//                @Override
//                public void onAdClosed() {
//                    Log.d("adError","Ad Ad Closed");
//                }
//            });
//
//
//
//            adView.loadAd(adRequest);
//        }
//        else if (getResources().getString(R.string.Ads).equals("FACEBOOK")){
//            adView.setVisibility(View.GONE);
//            fb_AdView=new com.facebook.ads.AdView(this, getResources().getString(R.string.FB_Banner_Ad_PlacemaneId_5), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
//            LinearLayout adContainer = (LinearLayout) findViewById(R.id.fb_banner_container_gb);
//            adContainer.addView(fb_AdView);
//            fb_AdView.loadAd();
//        }

    }
    @Override
    public void onPause(){
        if(adView!=null){
            adView.pause();
        }
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy(){
        if(adView!=null){
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_other, menu);
        return true;
    }


    class PagerAdapter extends FragmentPagerAdapter {
        private GBImageFragment imageFragment;
        private GBVideoFragment videoFragment;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            imageFragment = new GBImageFragment();
            videoFragment = new GBVideoFragment();
        }

        @Override
        public Fragment getItem(int i) {
            if(i == 0)
            {
                return imageFragment;
            }
            else{
                return videoFragment;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "images";
            }
            else{
                return "videos";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}