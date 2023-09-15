package com.video.music.downloader.VideoDownloader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anthonycr.progress.AnimatedProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
////import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.android.play.core.appupdate.AppUpdateInfo;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.InstallState;
//import com.google.android.play.core.install.InstallStateUpdatedListener;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.InstallStatus;
//import com.google.android.play.core.install.model.UpdateAvailability;
//import com.google.android.play.core.tasks.OnSuccessListener;
import com.video.music.downloader.Activity.DashboardActivity;
import com.video.music.downloader.Activity.SettingsActivity;

import com.video.music.downloader.MusicPlayer.view.MusicPlayerActivity;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Adapters.AutoCompleteAdapter;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.VideoDownloader.Grabber.AdBlocker;
import com.video.music.downloader.VideoDownloader.Grabber.ContentSearch;
import com.video.music.downloader.VideoDownloader.Grabber.CustomSearch;
import com.video.music.downloader.VideoDownloader.Grabber.URLAddFilter;
import com.video.music.downloader.VideoDownloader.Models.CustomGrabberModel;
import com.video.music.downloader.VideoDownloader.Models.downloadable_resource_model;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.VideoDownloader.Models.resourse_holder_model;
import com.video.music.downloader.VideoDownloader.Statics.static_variables;
import com.video.music.downloader.VideoDownloader.StatusSaver.BaseDrawerActivity;
import com.video.music.downloader.VideoDownloader.Utils.Commons;
import com.video.music.downloader.VideoDownloader.Utils.Utils;
import com.video.music.downloader.VideoDownloader.popups.available_files_dialog;
import com.video.music.downloader.statusandgallery.activity.WhatsAppStatusSaverActivity;
import com.video.music.downloader.videoplayer.VideoPlayerActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private AdBlocker adBlock;
    AnimatedProgressBar loadingPageProgress;
    CountDownTimer countDownTimer;
    AutoCompleteTextView et_search_bar;
    WebView simpleWebView;
    Context mContext;
    Timer timer=null;
    private SSLSocketFactory defaultSSLSF;
    FloatingActionButton download_fab, download_video_fab, download_audio_fab,download_images_fab;
    TextView download_video_fab_text,download_audio_fab_text,download_images_fab_text;
    Boolean isAllFabsVisible;
    ImageView btn_home, btn_search,btn_search_cancel,btn_settings, help;
    available_files_dialog _available_files_dialog;
    boolean fab_enabled=false;
    private boolean isRedirected;
    DrawerLayout drawerLayout;
    ImageView menu, settings;
    AdView adViewMainAct;
    boolean ishtmlvisibe = true;
//    //com.facebook.ads.AdView fb_AdView;
//    AppUpdateManager mAppUpdateManager;


    @Override
    protected void onStart() {
        super.onStart();
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRunHelp", true);
        if (isFirstRun) {
            helpDialog();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRunHelp", false).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main2);
        mContext=this;
        defaultSSLSF=HttpsURLConnection.getDefaultSSLSocketFactory();
        init_components();
        set_button_click_events();
        wv_go_to_home();
        disable_fab_button();
        try {
            onSharedIntent();
        }
        catch (Exception ex)
        {}
        menu =findViewById(R.id.menu);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.btn_help);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpDialog();
            }
        });

        if (ishtmlvisibe){
            set_searchbar_text("");
        }
//        checkAppUpdate();


//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        AudienceNetworkAds.initialize(this);
//        if( getResources().getString(R.string.Ads).equals("ADMOB") ){
//            AdRequest adRequest = new AdRequest.Builder().build();
//
//            adViewMainAct.setAdListener(new AdListener() {
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
//            adViewMainAct.loadAd(adRequest);
//        }
//        else if (getResources().getString(R.string.Ads).equals("FACEBOOK")){
//            adViewMainAct.setVisibility(View.GONE);
//            fb_AdView=new com.facebook.ads.AdView(this, getResources().getString(R.string.FB_Banner_Ad_PlacemaneId_1), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
//            LinearLayout adContainer = (LinearLayout) findViewById(R.id.fb_banner_container);
//            adContainer.addView(fb_AdView);
//            fb_AdView.loadAd();
//        }

        havePermissionForWriteStorage();

        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData != null)
        {
            et_search_bar.setText(appLinkData.toString());
            navigate_browser();
        }
        PrepareForAdBlockers();
    }

    private void helpDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getResources().getString(R.string.app_name));
//        builder.setMessage(getResources().getString(R.string.if_you_like_this_app_than_don_t_forget_to_rate_this_app_it_won_t_take_more_than_minutes));
            builder.setMessage(R.string.help_txt);

            builder.setIcon(getResources().getDrawable(R.drawable.ic_logo));

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })/*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setNeutralButton("Rate App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })*/;

        /*builder.setNegativeButtonIcon(getResources().getDrawable(R.drawable.btn_share));
        builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.btn_rate));
        builder.setNeutralButtonIcon(getResources().getDrawable(R.drawable.btn_privacy));*/

            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            /*alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.black));*/



    }

    private void onSharedIntent()
    {
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();
        if (receivedAction.equals(Intent.ACTION_SEND))
        {
            if (receivedType.startsWith("text/"))
            {
                String receivedText = receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    CheckUrls(receivedText);
                }
            }
        }
    }

    private void CheckUrls(String text){
        List<String> result= Commons.extractUrls(text);
        if(result.size()==0)
        {
            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(mContext.getString(R.string.Wait))
                    .setContentText(mContext.getString(R.string.NoUrlFound))
                    .show();
        }
        else
        {
            et_search_bar.setText(result.get(0));
            navigate_browser();
        }
    }

    private void PrepareForAdBlockers() {

        File file = new File(mContext.getFilesDir(), "ad_filters.dat");
        try {
            if (file.exists()) {
                Log.d("debug", "file exists");
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                adBlock = (AdBlocker) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } else {
                adBlock = new AdBlocker();
                Log.d("debug", "file not exists");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(adBlock);
                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            adBlock = new AdBlocker();
        }
        updateAdFilters();
    }

    private boolean havePermissionForWriteStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.d("Permission Allowed", "true");
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 950);
                return false;
            } else {
                initFolers();
                return true;
            }
        } else {
            initFolers();
            return true;
        }
    }


    public void updateAdFilters() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                adBlock.update(mContext);
            }
        });

    }
    public boolean checkUrlIfAds(String url) {
        return adBlock.checkThroughFilters(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 950:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initFolers();
                } else {
                    Toast.makeText(mContext,getString(R.string.Permissiondenied), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void initFolers()
    {
        try {
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO));
        }
        catch (Exception ex)
        {

        }
    }
    private void mkdirs(File _dir)
    {
        if(!_dir.exists())
            _dir.mkdir();
    }

    private  void enable_fab_button(){
        download_fab.setEnabled(true);
        download_fab.setClickable(true);
    }

    private void enable_audio_fab(){
        enable_fab_button();
        download_audio_fab.setEnabled(true);
        download_audio_fab.setClickable(true);
    }

    private void enable_video_fab(){
        enable_fab_button();
        download_video_fab.setEnabled(true);
        download_video_fab.setClickable(true);
    }

    private void enable_images_fab(){
        enable_fab_button();
        download_images_fab.setEnabled(true);
        download_images_fab.setClickable(true);
    }

    private void update_audio_fab_text(){
        try {
            download_audio_fab_text.setText(static_variables.resourse_holder.getAudio_files().size() + "");
        }
        catch (Exception ex){
        }
    }

    private void update_image_fab_text(){
        try {
            download_images_fab_text.setText(static_variables.resourse_holder.getImage_files().size()+"" );
        }
        catch (Exception e){}
    }

    private void update_video_fab_text(){
        try {
            download_video_fab_text.setText(static_variables.resourse_holder.getVideo_files().size()+"" );
        }
        catch (Exception ex)
        {

        }
    }

    private  void disable_fab_button(){

        if(isAllFabsVisible){
            toggle_fab_buttons();
        }
        download_fab.setEnabled(false);
        download_fab.setClickable(false);
        download_audio_fab_text.setText("0");
        download_video_fab_text.setText("0");
        download_images_fab_text.setText("0");
        download_audio_fab.setEnabled(false);
        download_images_fab.setEnabled(false);
        download_video_fab.setEnabled(false);
    }

    private void  wv_go_to_home(){
        simpleWebView.loadUrl( getResources().getString(R.string.index_page) );

    }

    private void init_components(){
        loadingPageProgress=findViewById(R.id.loadingPageProgress);
        simpleWebView=findViewById(R.id.simpleWebView);
        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.getSettings().setDomStorageEnabled(true);
        simpleWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        simpleWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        simpleWebView.setWebViewClient(new customWebClient());

//        adViewMainAct=findViewById(R.id.adViewMainAct);
        et_search_bar=findViewById(R.id.et_search_bar);
        int layout = android.R.layout.simple_list_item_1;
        AutoCompleteAdapter adapter = new AutoCompleteAdapter (mContext, layout);
        et_search_bar.setAdapter(adapter);

        download_fab=findViewById(R.id.download_fab);
        download_fab.bringToFront();
        download_video_fab=findViewById(R.id.download_video_fab);
        download_video_fab.bringToFront();

        download_audio_fab=findViewById(R.id.download_audio_fab);
        download_audio_fab.bringToFront();

        download_images_fab=findViewById(R.id.download_images_fab);
        download_images_fab.bringToFront();

        download_video_fab_text=findViewById(R.id.download_video_fab_text);
        download_audio_fab_text=findViewById(R.id.download_audio_fab_text);
        download_images_fab_text=findViewById(R.id.download_images_fab_text);


        btn_home=findViewById(R.id.btn_home);
        btn_search_cancel=findViewById(R.id.btn_search_cancel);
        btn_search=findViewById(R.id.btn_search);
        btn_settings=findViewById(R.id.btn_settings);

        registerForContextMenu(btn_settings);

        isAllFabsVisible=false;
    }

    public void videoplayer(View view) {
        startActivity(new Intent(MainActivity.this, VideoPlayerActivity.class));

    }

    public void musicplayer(View view) {
        startActivity(new Intent(MainActivity.this, MusicPlayerActivity.class));

    }

    public void downloader(View view) {
        startActivity(new Intent(MainActivity.this, MainActivity.class));

    }

    public void dashboard(View view) {
        startActivity(new Intent(MainActivity.this, DashboardActivity.class));

    }

    public void status(View view) {
        startActivity(new Intent(MainActivity.this, WhatsAppStatusSaverActivity.class));

    }
    @Override
    public void onCreateContextMenu(
            ContextMenu menu,
            View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
    }

    private void toggle_fab_buttons()
    {
        if (!isAllFabsVisible){
            download_video_fab.show();
            download_audio_fab.show();
            download_images_fab.show();
            download_video_fab_text.setVisibility(View.VISIBLE);
            download_audio_fab_text.setVisibility(View.VISIBLE);
            download_images_fab_text.setVisibility(View.VISIBLE);
            isAllFabsVisible = true;
        }
        else
        {
            download_video_fab.hide();
            download_audio_fab.hide();
            download_images_fab.hide();
            download_video_fab_text.setVisibility(View.GONE);
            download_audio_fab_text.setVisibility(View.GONE);
            download_images_fab_text.setVisibility(View.GONE);

            isAllFabsVisible = false;
        }
    }

    private void set_button_click_events(){
        download_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_fab_buttons();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv_go_to_home();
                set_searchbar_text("");
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate_browser();
            }
        });

        btn_search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_searchbar_text("");
            }
        });

        et_search_bar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus && et_search_bar.getText().toString().equals(getResources().getString(R.string.home)) ){
                    set_searchbar_text("");
                }
            }
        });

        et_search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            navigate_browser();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });



        download_video_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog=new available_files_dialog( file_type.VIDEO);
                _available_files_dialog.show(getSupportFragmentManager(),"Videos");
            }
        });

        download_images_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog=new available_files_dialog( file_type.IMAGE);
                _available_files_dialog.show(getSupportFragmentManager(),"Images");
            }
        });

        download_audio_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog=new available_files_dialog( file_type.AUDIO);
                _available_files_dialog.show(getSupportFragmentManager(),"Audios");
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    btn_settings.showContextMenu(0,0);
                }
                else
                {
                    btn_settings.showContextMenu();
                }
            }
        });

        et_search_bar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigate_browser();
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void findLocal()
    {
        if(et_search_bar.getText().toString().contains("facebook.com"))
        {
            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {

                           try {
                               if(html !=null)
                               {
                                   JsonReader reader = new JsonReader(new StringReader(html));
                                   reader.setLenient(true);
                                   try {
                                       if(reader.peek() == JsonToken.STRING) {
                                           String domStr = reader.nextString();
                                           if(domStr != null) {
                                               Document document=Jsoup.parse(domStr);
                                               String videoUrl= document.select("meta[property=\"og:video\"]").last().attr("content");
                                               ArrayList<downloadable_resource_model> video_files=new ArrayList<>();
                                               static_variables.resourse_holder.setVideo_files(video_files);
                                               static_variables.resourse_holder.add_Video(null,"video",videoUrl,"Video","page");
                                                   runOnUiThread(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           update_image_fab_text();
                                                           enable_Buttons();
                                                       }
                                                   });

                                           }
                                       }
                                   } catch (IOException e) {
                                       // handle exception
                                   } finally {
                                      // IoUtil.close(reader);
                                   }
                               }

                           }
                           catch (Exception ex)
                           {}
                        }
                    });
        }

        // Customised Searches
        String[] customised_searches_filters = mContext.getResources().getStringArray(R.array.customised_searches);
        boolean MatchCustomSearch=false;
        for (String filter : customised_searches_filters)
        {
            if (et_search_bar.getText().toString().contains(filter))
            {
                MatchCustomSearch=true;
            }
        }
        if(MatchCustomSearch)
        {
            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            List<CustomGrabberModel> customGrabberModelList= CustomSearch.Search(mContext,et_search_bar.getText().toString(),html);
                            if(customGrabberModelList.size() > 0)
                            {
                                static_variables.resourse_holder=new resourse_holder_model();
                            }
                            for (CustomGrabberModel customGrabberModel  : customGrabberModelList)
                            {
                                if( customGrabberModel.getVideoUrl() !=null && customGrabberModel.getVideoUrl() !="")
                                {
                                    if(customGrabberModel.getM3u8())
                                    {
                                        static_variables.resourse_holder.add_Video(null,"m3u8",customGrabberModel.getVideoUrl(),"Video","page");
                                    }
                                    else
                                    {
                                        if(static_variables.resourse_holder==null)
                                        {
                                            static_variables.resourse_holder=new resourse_holder_model();
                                        }
                                        static_variables.resourse_holder.add_Video(null,"video",customGrabberModel.getVideoUrl(),"Video","page");
                                    }
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_image_fab_text();
                                    enable_Buttons();
                                }
                            });

                        }
                    });
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
//            case R.id.action_pp: {
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert.setTitle(getString(R.string.PrivacyPolicy));
//                WebView wv = new WebView(this);
//                wv.loadUrl("file:///android_asset/PrivacyPolicy.html");
//                wv.setWebViewClient(new WebViewClient());
//                alert.setView(wv);
//                alert.setNegativeButton(getString(R.string.Close), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//                alert.show();
//                return true;
//            }
            case R.id.action_nonworking: {
                et_search_bar.setText("https://forms.gle/gvoduNgZ4VVCXMd38");
                navigate_browser();
                return true;
            }
//            case R.id.action_help:
//            {
//                Intent intent = new Intent(this, NewUserIntroActivity.class);
//                startActivity(intent);
//                return true;
//            }
//            case R.id.action_share_app:
//            {
//                Intent _myIntent =new Intent(Intent.ACTION_SEND);
//                _myIntent.setType("text/plain");
//                String ShareBody="Hi \nPlease check this Awesome Application. '"+ getResources().getString(R.string.app_name) +"'\nYou'll love it. \n\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
//                String ShareSub=getString(R.string.hithere);
//                _myIntent.putExtra(Intent.EXTRA_SUBJECT,ShareSub);
//                _myIntent.putExtra(Intent.EXTRA_TEXT,ShareBody);
//                startActivity( Intent.createChooser(_myIntent ,getString(R.string.Shareusing)) );
//                return true;
//            }
//            case R.id.action_Downloads:
//            {
//                Intent intent = new Intent(this, DownloadsActivity.class);
//                startActivity(intent);
//                return true;
//            }
//            case R.id.action_other_apps:
//            {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("YOUR play store Developer Page URL")));
//                return true;
//            }
            case R.id.action_whatsapp:
            {
                Intent intent = new Intent(this, BaseDrawerActivity.class);
                startActivity(intent);
                return true;
            }

        }
        return true;
    }
    String url, edtext;
    private void navigate_browser(){
        hideKeyboard();
        if(! Patterns.WEB_URL.matcher(et_search_bar.getText()).matches())
        {
//            et_search_bar.setText("https://www.google.com/search?q=" + et_search_bar.getText());
            url = "https://www.google.com/search?q=" + et_search_bar.getText();
            edtext = et_search_bar.getText().toString();
        }
        else{
            url = et_search_bar.getText().toString();
            edtext = et_search_bar.getText().toString();
        }
        simpleWebView.loadUrl(url);
    }

    private void  startTimer(){
        final int secs = 10;
        disable_fab_button();
        loadingPageProgress.setProgress(0);
        countDownTimer= new CountDownTimer((secs +1) * 100, 1000)
        {
            @Override
            public final void onTick(final long millisUntilFinished)
            {
                if(loadingPageProgress.getProgress() < 80){
                    loadingPageProgress.setProgress(loadingPageProgress.getProgress() + 8);
                }
            }
            @Override
            public final void onFinish()
            {

            }
        };
        countDownTimer.start();
        loadingPageProgress.setVisibility(View.VISIBLE);
    }
    private void stopTimer(){
        try {
            countDownTimer.cancel();
        }
        catch (Exception ex){}
        loadingPageProgress.setProgress(100);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingPageProgress.setProgress(0);
                loadingPageProgress.setVisibility(View.GONE);

            }
        }, 500);

    }

    private void set_searchbar_text(String text){
        if(text.equals(getResources().getString(R.string.index_page))){
            text=getResources().getString(R.string.home);
        }
        if(text.equals("")){
            btn_search_cancel.setVisibility(View.INVISIBLE);
            et_search_bar.requestFocus();
        }
        else
        {
            btn_search_cancel.setVisibility(View.VISIBLE);
            text = et_search_bar.getText().toString();

        }
        et_search_bar.setText(text);
    }


    public class customWebClient extends WebViewClient
    {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String request)
        {
            String url=request;
            if ( (url.contains("ad") || url.contains("banner") || url.contains("pop")) && checkUrlIfAds(url)) {
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("intent://")) {
                try {
                    Context context = simpleWebView.getContext();
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        PackageManager packageManager = context.getPackageManager();
                        ResolveInfo info = packageManager.resolveActivity(intent,
                                PackageManager.MATCH_DEFAULT_ONLY);
                        if ((intent != null) && ((intent.getScheme().equals("https"))
                                || (intent.getScheme().equals("http")))) {
                            String fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url");
                            simpleWebView.loadUrl(fallbackUrl);
                            return true;
                        }
                        if (info != null) {
                            context.startActivity(intent);
                        } else {
                            String fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url");
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(fallbackUrl));
                            context.startActivity(browserIntent);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            startTimer();
            isRedirected = false;
            super.onPageStarted(view, url, favicon);
            if(timer !=null)
            {
                timer.cancel();
                timer=null;
            }

            set_searchbar_text(url);
            static_variables.resourse_holder=new resourse_holder_model();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if(static_variables.resourse_holder==null)
            {
                static_variables.resourse_holder=new resourse_holder_model();
            }
            static_variables.resourse_holder.setPage_title(view.getTitle());
            if (!isRedirected){
                stopTimer();
                enable_Buttons();
                findLocal();
                if(download_fab.isEnabled()==true ){
                    YoYo.with(Techniques.Tada)
                            .duration(300)
                            .repeat(5)
                            .playOn(findViewById(R.id.download_fab));
                }

// **** Uncomment this code if you want to disable admob ad on any Website Open
//                if( getResources().getString(R.string.Ads).equals("ADMOB") ){
//                    if(! et_search_bar.getText().toString().equals(getResources().getString(R.string.home)) )
//                    {
////                        adViewMainAct.setVisibility(View.GONE);
////                        adViewMainAct.destroy();
//                    }
//                    else
//                    {
////                        adViewMainAct.setVisibility(View.VISIBLE);
////                        AdRequest adRequest = new AdRequest.Builder().build();
////                        adViewMainAct.loadAd(adRequest);
//                    }
//                }

            }
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onLoadResource(final WebView view, final String url)
        {
            if(!URLAddFilter.DoNotCheckIf(mContext,et_search_bar.getText().toString()))
            {
                final String viewUrl = view.getUrl();
                final String title = view.getTitle();
                //Log.d("RESFILE",url);
                new ContentSearch(mContext,url, viewUrl, title){
                    @Override
                    public void onStartInspectingURL() {
                        Utils.disableSSLCertificateChecking();
                    }

                    @Override
                    public void onFinishedInspectingURL(boolean finishedAll) {
                        HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
                    }

                    @Override
                    public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        static_variables.resourse_holder.add_Video(size,type,link,name,page);
                        if(static_variables.resourse_holder.getVideo_files().size() > 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_image_fab_text();
                                    enable_Buttons();
                                }
                            });
                        }
                    }

                    @Override
                    public void onImageFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                       try {
                           static_variables.resourse_holder.add_Image(size,type,link,name,page);
                           if(static_variables.resourse_holder.getImage_files().size() > 0){
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       update_image_fab_text();
                                       enable_Buttons();
                                   }
                               });
                           }
                       }
                       catch (Exception ex){}
                    }

                    @Override
                    public void onAudioFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        static_variables.resourse_holder.add_Audio(size,type,link,name,page);
                        if(static_variables.resourse_holder.getImage_files().size() > 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_audio_fab_text();
                                    enable_Buttons();
                                }
                            });
                        }
                    }
                }.start();
            }
        }
    }



    private void enable_Buttons() {
        if( (static_variables.resourse_holder.getVideo_files().size() >0)  || (static_variables.resourse_holder.getAudio_files().size() >0) || (static_variables.resourse_holder.getImage_files().size() >0) ){
            enable_audio_fab();
            enable_fab_button();
        }
        if((static_variables.resourse_holder.getVideo_files().size() >0) ){
            enable_video_fab();
            update_video_fab_text();
        }
        if((static_variables.resourse_holder.getImage_files().size() >0) ){
            enable_images_fab();
            update_video_fab_text();
        }
    }

    public void popupSnackbarForCompleteUpdate() {
        try {
            Snackbar make = Snackbar.make(findViewById(R.id.simpleWebView), (CharSequence) "An update has just been downloaded.", 2);
            make.setAction((CharSequence) "RESTART", (View.OnClickListener) new View.OnClickListener() {
                public void onClick(View view) {
//                    if (MainActivity.this.mAppUpdateManager != null) {
//                        MainActivity.this.mAppUpdateManager.completeUpdate();
//                    }
                }
            });
            make.setDuration(50000);
            make.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


//    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
//        public void onStateUpdate(InstallState installState) {
//            try {
//                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
//                    MainActivity.this.popupSnackbarForCompleteUpdate();
//                } else if (installState.installStatus() != InstallStatus.INSTALLED) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("InstallStateUpdatedListener: state: ");
//                    sb.append(installState.installStatus());
//                    Log.i("MainActivity", sb.toString());
//                } else if (MainActivity.this.mAppUpdateManager != null) {
//                    MainActivity.this.mAppUpdateManager.unregisterListener(MainActivity.this.installStateUpdatedListener);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private void checkAppUpdate() {
//        try {
//            this.mAppUpdateManager = AppUpdateManagerFactory.create(this);
//            this.mAppUpdateManager.registerListener(this.installStateUpdatedListener);
//            this.mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
//                public void onSuccess(AppUpdateInfo appUpdateInfo) {
//                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                        try {
//                            MainActivity.this.mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, MainActivity.this, 201);
//                        } catch (IntentSender.SendIntentException e) {
//                            e.printStackTrace();
//                        }
//                    } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                        MainActivity.this.popupSnackbarForCompleteUpdate();
//                    } else {
//                        Log.e("MainActivity", "checkForAppUpdateAvailability: something else");
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (simpleWebView.copyBackForwardList().getCurrentIndex() > 0) {
            simpleWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

}