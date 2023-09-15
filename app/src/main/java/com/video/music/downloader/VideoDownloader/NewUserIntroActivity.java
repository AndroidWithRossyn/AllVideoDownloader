package com.video.music.downloader.VideoDownloader;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.video.music.downloader.R;

public class NewUserIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setButtonBackVisible(false);

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_1))
                .image(R.drawable.intro_1)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_2))
                .image(R.drawable.intro_2)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());


        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_3))
                .image(R.drawable.intro_3)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_4))
                .image(R.drawable.intro_4)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_5))
                .image(R.drawable.intro_5)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_6))
                .image(R.drawable.intro_6)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_7))
                .image(R.drawable.intro_7)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_8))
                .image(R.drawable.intro_8)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_8))
                .image(R.drawable.intro_8)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_9))
                .image(R.drawable.intro_9)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(getResources().getString(R.string.intro_txt_10))
                .image(R.drawable.intro_10)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .image(R.drawable.intro_11)
                .background(R.color.text_shadow)
                .scrollable(false)
                .build());


        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setNavigation(true, true);


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4113){
            if(checkIfBatteryOptimizationIgnored()){
                setNavigation(true, false);
                nextSlide();
            }
        }
    }


    private boolean checkIfBatteryOptimizationIgnored(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            return pm.isIgnoringBatteryOptimizations(packageName);
        } else {
            return true;
        }
    }

    private void setNavigation(final boolean forward, final boolean backward){
        setNavigationPolicy(new NavigationPolicy() {
            @Override
            public boolean canGoForward(int i) {
                return forward;
            }

            @Override
            public boolean canGoBackward(int i) {
                return backward;
            }
        });
    }
}
