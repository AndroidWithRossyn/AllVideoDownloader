package com.video.music.downloader.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.anthonycr.progress.AnimatedProgressBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.video.music.downloader.MusicPlayer.view.MusicPlayerActivity;
import com.video.music.downloader.R;

import com.video.music.downloader.databinding.ActivityVideoDownloaderBinding;
//import com.video.music.downloader.statusandgallery.Grabber.CustomSearch;
//import com.video.music.downloader.statusandgallery.Statics.static_variables;
import com.video.music.downloader.statusandgallery.activity.WhatsAppStatusSaverActivity;
//import com.video.music.downloader.statusandgallery.configs.SettingsManager;
//import com.video.music.downloader.statusandgallery.model.Alldownloader.CustomGrabberModel;
//import com.video.music.downloader.statusandgallery.model.Alldownloader.downloadable_resource_model;
//import com.video.music.downloader.statusandgallery.model.Alldownloader.resourse_holder_model;
//import com.video.music.downloader.statusandgallery.popups.available_files_dialog;
import com.video.music.downloader.statusandgallery.utils.AppLangSessionManager;
import com.video.music.downloader.videoplayer.VideoPlayerActivity;

import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class VideoDownloaderActivity extends AppCompatActivity  {
    ActivityVideoDownloaderBinding binding;
    AnimatedProgressBar loadingPageProgress;
    CountDownTimer countDownTimer;
    AutoCompleteTextView et_search_bar;
    WebView simpleWebView;
    Context mContext;
    Timer timer = null;
    LinearLayout b1;
    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private SSLSocketFactory defaultSSLSF;
    FloatingActionButton download_fab, download_video_fab, download_audio_fab, download_images_fab;
    TextView download_video_fab_text, download_audio_fab_text, download_images_fab_text;
    Boolean isAllFabsVisible;
    ImageView btn_home, btn_search, btn_search_cancel, btn_settings, rvWhatsApp, rvFB, rvChingari, rvInsta, rvShareChat, rvRoposo, rvTwitter, rvSnack, rvMX, rvLikee, rvJosh, rvMoj, rvMitron, rvTikTok;
//    available_files_dialog _available_files_dialog;
    boolean fab_enabled = false;
    private boolean isRedirected;
    private ClipboardManager clipBoard;

    String CopyKey = "";
    String CopyValue = "";
    AppLangSessionManager appLangSessionManager;
    boolean isMainLLvisible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_downloader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        binding.mainLl.setVisibility(View.GONE);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new FragmentDownload()).commit();

//        binding.category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//                    @Override
//                    public void adLoadState(boolean isLoaded) {
//                        startActivity(new Intent(VideoDownloaderActivity.this, AllCategoriesActivity.class));
//                    }
//                });
//            }
//        });
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity(new Intent(VideoDownloaderActivity.this, SettingsActivity.class));
            }
        });

        binding.searchViewFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                binding.fragmentView.setVisibility(View.VISIBLE);
//                binding.mainLl.setVisibility(View.GONE);
//                isMainLLvisible=false;
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, new FragmentDownload()).commit();

            }
        });
//        mContext = VideoDownloaderActivity.this;
        defaultSSLSF = HttpsURLConnection.getDefaultSSLSocketFactory();
        appLangSessionManager = new AppLangSessionManager(this);
//        init_components();
//        initview();

//
//        set_button_click_events();
//        wv_go_to_home();
//        disable_fab_button();
//        try {
//            onSharedIntent();
//        } catch (Exception ex) {
//        }
//
//        havePermissionForWriteStorage();
//
//        Intent appLinkIntent = this.getIntent();
//        Uri appLinkData = appLinkIntent.getData();
//        if (appLinkData != null) {
//            et_search_bar.setText(appLinkData.toString());
//            navigate_browser();
//        }
    }

    public void videoplayer(View view) {
        startActivity(new Intent(VideoDownloaderActivity.this, VideoPlayerActivity.class));

    }

    public void musicplayer(View view) {
        startActivity(new Intent(VideoDownloaderActivity.this, MusicPlayerActivity.class));

    }

    public void downloader(View view) {
        startActivity(new Intent(VideoDownloaderActivity.this, VideoDownloaderActivity.class));

    }

    public void status(View view) {
        startActivity(new Intent(VideoDownloaderActivity.this, WhatsAppStatusSaverActivity.class));

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
//        if (isMainLLvisible){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//        }else {
//            binding.fragmentView.setVisibility(View.GONE);
//            binding.mainLl.setVisibility(View.VISIBLE);
//            binding.b1.setVisibility(View.VISIBLE);
//            isMainLLvisible=true;
//        }
    }
    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//       
////    }
//
//    public void initview() {
//        clipBoard = (ClipboardManager) VideoDownloaderActivity.this.getSystemService(CLIPBOARD_SERVICE);
//        if (VideoDownloaderActivity.this.getIntent().getExtras() != null) {
//            for (String key : VideoDownloaderActivity.this.getIntent().getExtras().keySet()) {
//                CopyKey = key;
//                String value = VideoDownloaderActivity.this.getIntent().getExtras().getString(CopyKey);
////                if (CopyKey.equals("android.intent.extra.TEXT")) {
////                    CopyValue = VideoDownloaderActivity.this.getIntent().getExtras().getString(CopyKey);
////                    CopyValue = extractLinks(CopyValue);
////                    callText(value);
////                } else {
////                    CopyValue = "";
////                    callText(value);
////                }
//            }
//        }
//        if (clipBoard != null) {
//            clipBoard.addPrimaryClipChangedListener(new ClipboardListener() {
//                @Override
//                public void onPrimaryClipChanged() {
//                    try {
////                        showNotification(Objects.requireNonNull(clipBoard.getPrimaryClip().getItemAt(0).getText()).toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        if (Build.VERSION.SDK_INT >= 23) {
////            checkPermissions(0);
//        }
//        binding.rvLikee.setOnClickListener(this);
//        binding.rvInsta.setOnClickListener(this);
//        binding.rvWhatsApp.setOnClickListener(this);
//        binding.rvTikTok.setOnClickListener(this);
//        binding.rvFB.setOnClickListener(this);
//        binding.rvTwitter.setOnClickListener(this);
//        binding.rvSnack.setOnClickListener(this);
//        binding.rvShareChat.setOnClickListener(this);
//        binding.rvRoposo.setOnClickListener(this);
//        binding.rvJosh.setOnClickListener(this);
//        binding.rvChingari.setOnClickListener(this);
//        binding.rvMitron.setOnClickListener(this);
//        binding.rvMoj.setOnClickListener(this);
//        binding.rvMX.setOnClickListener(this);
//
//    }

//
//    private boolean checkPermissions(int type) {
//        int result;
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p : permissions) {
//            result = ContextCompat.checkSelfPermission(VideoDownloaderActivity.this, p);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions((Activity) (VideoDownloaderActivity.this), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), type);
//            return false;
//        } else {
//            if (type == 100) {
//                callLikeeActivity();
//            } else if (type == 101) {
//                callInstaActivity();
//            } else if (type == 102) {
//                callWhatsappActivity();
//            } else if (type == 103) {
//                callTikTokActivity();
//            } else if (type == 104) {
//                callFacebookActivity();
//            } else if (type == 106) {
//                callTwitterActivity();
//            } else if (type == 107) {
//                callShareChatActivity();
//            } else if (type == 108) {
//                callRoposoActivity();
//            } else if (type == 109) {
//                callSnackVideoActivity();
//            } else if (type == 110) {
//                callJoshActivity();
//            } else if (type == 111) {
//                callChingariActivity();
//            } else if (type == 112) {
//                callMitronActivity();
//            } else if (type == 113) {
//                callMXActivity();
//            } else if (type == 114) {
//                callMojActivity();
//            }
//
//        }
//        return true;
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callLikeeActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 101) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callInstaActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 102) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callWhatsappActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 103) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callTikTokActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 104) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callFacebookActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 106) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callTwitterActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 107) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callShareChatActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 108) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callRoposoActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 109) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callSnackVideoActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 110) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callJoshActivity();
//            }
//        } else if (requestCode == 111) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callChingariActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 112) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callMitronActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 113) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callMXActivity();
//            } else {
//            }
//            return;
//        } else if (requestCode == 114) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callMojActivity();
//            } else {
//            }
//            return;
//        }
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        Intent i = null;
//
//        switch (v.getId()) {
//            case R.id.rvLikee:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(100);
//                } else {
//                    callLikeeActivity();
//                }
//                break;
//            case R.id.rvInsta:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(101);
//                } else {
//                    callInstaActivity();
//                }
//                break;
//
//            case R.id.rvWhatsApp:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(102);
//                } else {
//                    callWhatsappActivity();
//                }
//                break;
//            case R.id.rvTikTok:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(103);
//                } else {
//                    callTikTokActivity();
//                }
//                break;
//            case R.id.rvFB:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(104);
//                } else {
//                    callFacebookActivity();
//                }
//                break;
//
//            case R.id.rvTwitter:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(106);
//                } else {
//                    callTwitterActivity();
//                }
//                break;
//
//            case R.id.rvShareChat:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(107);
//                } else {
//                    callShareChatActivity();
//                }
//                break;
//            case R.id.rvRoposo:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(108);
//                } else {
//                    callRoposoActivity();
//                }
//                break;
//            case R.id.rvSnack:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(109);
//                } else {
//                    callSnackVideoActivity();
//                }
//                break;
//            case R.id.rvJosh:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(110);
//                } else {
//                    callJoshActivity();
//                }
//                break;
//            case R.id.rvChingari:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(111);
//                } else {
//                    callChingariActivity();
//                }
//                break;
//            case R.id.rvMitron:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(112);
//                } else {
//                    callMitronActivity();
//                }
//                break;
//            case R.id.rvMX:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(113);
//                } else {
//                    callMXActivity();
//                }
//                break;
//
//            case R.id.rvMoj:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(114);
//                } else {
//                    callMojActivity();
//                }
//                break;
////            case R.id.rvGames:
////                callGamesActivity();
////                break;
//
//        }
//    }
//
//
//    private void callText(String CopiedText) {
//        try {
//            if (CopiedText.contains("likee")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(100);
//                } else {
//                    callLikeeActivity();
//                }
//            } else if (CopiedText.contains("instagram.com")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(101);
//                } else {
//                    callInstaActivity();
//                }
//            } else if (CopiedText.contains("facebook.com") || CopiedText.contains("fb")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(104);
//                } else {
//                    callFacebookActivity();
//                }
//            } else if (CopiedText.contains("tiktok.com")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(103);
//                } else {
//                    callTikTokActivity();
//                }
//            } else if (CopiedText.contains("twitter.com")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(106);
//                } else {
//                    callTwitterActivity();
//                }
//            } else if (CopiedText.contains("sharechat")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(107);
//                } else {
//                    callShareChatActivity();
//                }
//            } else if (CopiedText.contains("roposo")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(108);
//                } else {
//                    callRoposoActivity();
//                }
//            } else if (CopiedText.contains("snackvideo") || CopiedText.contains("sck.io")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(109);
//                } else {
//                    callSnackVideoActivity();
//                }
//            } else if (CopiedText.contains("josh")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(110);
//                } else {
//                    callJoshActivity();
//                }
//            } else if (CopiedText.contains("chingari")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(111);
//                } else {
//                    callChingariActivity();
//                }
//            } else if (CopiedText.contains("mitron")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(112);
//                } else {
//                    callMitronActivity();
//                }
//            } else if (CopiedText.contains("mxtakatak")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(113);
//                } else {
//                    callMXActivity();
//                }
//            } else if (CopiedText.contains("moj")) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkPermissions(114);
//                } else {
//                    callMojActivity();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    public void callJoshActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, JoshActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callChingariActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, ChingariActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callMitronActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, MitronActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callMXActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, MXTakaTakActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callMojActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, MojActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//
//    public void callLikeeActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, LikeeActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callInstaActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, InstagramActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//
//    public void callWhatsappActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, WhatsAppStatusSaverActivity.class);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callTikTokActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, TikTokActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callFacebookActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, FacebookActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//    }
//
//    public void callTwitterActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, TwitterActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callRoposoActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, ReposeActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void callShareChatActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, SharechatActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//
//            }
//        });
//
//    }
//
//    public void callSnackVideoActivity() {
//        AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                Intent i = new Intent(VideoDownloaderActivity.this, SnackVideoActivity.class);
//                i.putExtra("CopyIntent", CopyValue);
//                startActivity(i);
//            }
//        });
//
//
//    }
//
//    public void showNotification(String Text) {
//        if (Text.contains("instagram.com") || Text.contains("facebook.com") || Text.contains("fb") || Text.contains("tiktok.com") || Text.contains("twitter.com") || Text.contains("likee") || Text.contains("sharechat") || Text.contains("roposo") || Text.contains("snackvideo") || Text.contains("sck.io") || Text.contains("chingari") || Text.contains("myjosh") || Text.contains("mitron")) {
//            Intent intent = new Intent(VideoDownloaderActivity.this, FragmentDownload.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("Notification", Text);
//            PendingIntent pendingIntent = PendingIntent.getActivity(VideoDownloaderActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            NotificationManager notificationManager = (NotificationManager) VideoDownloaderActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
//                mChannel.enableLights(true);
//                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//                notificationManager.createNotificationChannel(mChannel);
//            }
//            NotificationCompat.Builder notificationBuilder;
//            notificationBuilder = new NotificationCompat.Builder(VideoDownloaderActivity.this, getResources().getString(R.string.app_name)).setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setColor(getResources().getColor(R.color.black)).setLargeIcon(BitmapFactory.decodeResource(VideoDownloaderActivity.this.getResources(), R.mipmap.ic_launcher)).setDefaults(Notification.DEFAULT_ALL).setPriority(NotificationCompat.PRIORITY_HIGH).setContentTitle("Copied text").setContentText(Text).setChannelId(getResources().getString(R.string.app_name)).setFullScreenIntent(pendingIntent, true);
//            notificationManager.notify(1, notificationBuilder.build());
//        }
//    }
//
//    private void onSharedIntent() {
//        Intent receiverdIntent = VideoDownloaderActivity.this.getIntent();
//        String receivedAction = receiverdIntent.getAction();
//        String receivedType = receiverdIntent.getType();
//        if (receivedAction.equals(Intent.ACTION_SEND)) {
//            if (receivedType.startsWith("text/")) {
//                String receivedText = receiverdIntent.getStringExtra(Intent.EXTRA_TEXT);
//                if (receivedText != null) {
//                    CheckUrls(receivedText);
//                }
//            }
//        }
//    }
//
//    private void CheckUrls(String text) {
//        List<String> result = Commons.extractUrls(text);
//        if (result.size() == 0) {
//            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE).setTitleText(mContext.getString(R.string.Wait)).setContentText(mContext.getString(R.string.NoUrlFound)).show();
//            b1.setVisibility(View.VISIBLE);
//        } else {
//            et_search_bar.setText(result.get(0));
//            navigate_browser();
//        }
//    }
//
//    private boolean havePermissionForWriteStorage() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ContextCompat.checkSelfPermission(VideoDownloaderActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Log.d("Permission Allowed", "true");
//                }
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 950);
//                return false;
//            } else {
//                initFolers();
//                return true;
//            }
//        } else {
//            initFolers();
//            return true;
//        }
//    }
//
////    @Override
////    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        switch (requestCode) {
////            case 950:
////                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    initFolers();
////                } else {
////                    Toast.makeText(mContext, getString(R.string.Permissiondenied), Toast.LENGTH_LONG).show();
////                }
////                break;
////        }
////    }
//
//    public void setLocale(String lang) {
//
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//
//        Intent refresh = new Intent(VideoDownloaderActivity.this, FragmentDownload.class);
//        startActivity(refresh);
//        VideoDownloaderActivity.this.finish();
//    }
//
//    public static String extractLinks(String text) {
//        Matcher m = Patterns.WEB_URL.matcher(text);
//        String url = "";
//        while (m.find()) {
//            url = m.group();
//            Log.d("New URL", "URL extracted: " + url);
//
//            break;
//        }
//        return url;
//    }
//
//
//    private void initFolers() {
//        try {
//            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER));
//            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES));
//            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO));
//            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO));
//        } catch (Exception ex) {
//
//        }
//    }
//
//    private void mkdirs(File _dir) {
//        if (!_dir.exists()) _dir.mkdir();
//    }

//    private void enable_fab_button() {
//        download_fab.setEnabled(true);
//        download_fab.setClickable(true);
//    }
//
//    private void enable_audio_fab() {
//        enable_fab_button();
//        download_audio_fab.setEnabled(true);
//        download_audio_fab.setClickable(true);
//    }
//
//    private void enable_video_fab() {
//        enable_fab_button();
//        download_video_fab.setEnabled(true);
//        download_video_fab.setClickable(true);
//    }
//
//    private void enable_images_fab() {
//        enable_fab_button();
//        download_images_fab.setEnabled(true);
//        download_images_fab.setClickable(true);
//    }
//
//    private void update_audio_fab_text() {
//        try {
//            download_audio_fab_text.setText(static_variables.resourse_holder.getAudio_files().size() + "");
//        } catch (Exception ex) {
//        }
//    }
//
//    private void update_image_fab_text() {
//        try {
//            download_images_fab_text.setText(static_variables.resourse_holder.getImage_files().size() + "");
//        } catch (Exception e) {
//        }
//    }
//
//    private void update_video_fab_text() {
//        try {
//            download_video_fab_text.setText(static_variables.resourse_holder.getVideo_files().size() + "");
//        } catch (Exception ex) {
//
//        }
//    }
//
//
//    private void disable_fab_button() {
//
//        if (isAllFabsVisible) {
//            toggle_fab_buttons();
//        }
//        download_fab.setEnabled(false);
//        download_fab.setClickable(false);
//        download_audio_fab_text.setText("0");
//        download_video_fab_text.setText("0");
//        download_images_fab_text.setText("0");
//        download_audio_fab.setEnabled(false);
//        download_images_fab.setEnabled(false);
//        download_video_fab.setEnabled(false);
//    }
//
//    private void wv_go_to_home() {
//        simpleWebView.loadUrl(getResources().getString(R.string.index_page));
//    }


//    @SuppressLint("SetJavaScriptEnabled")
//    private void init_components() {
//        loadingPageProgress = VideoDownloaderActivity.this.findViewById(R.id.loadingPageProgress);
//        simpleWebView = VideoDownloaderActivity.this.findViewById(R.id.simpleWebView);
//        simpleWebView.getSettings().setJavaScriptEnabled(true);
//        simpleWebView.getSettings().setDomStorageEnabled(true);
//        simpleWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        simpleWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        simpleWebView.setWebViewClient(new VideoDownloaderActivity.customWebClient());
//
//        et_search_bar = findViewById(R.id.et_search_bar);
//        int layout = android.R.layout.simple_list_item_1;
//        AutoCompleteAdapter adapter = new AutoCompleteAdapter(mContext, layout);
//        et_search_bar.setAdapter(adapter);
//
//        download_fab = VideoDownloaderActivity.this.findViewById(R.id.download_fab);
//        b1=VideoDownloaderActivity.this.findViewById(R.id.b1);
//        download_fab.bringToFront();
//        download_video_fab = VideoDownloaderActivity.this.findViewById(R.id.download_video_fab);
//        download_video_fab.bringToFront();
//
//        download_audio_fab = VideoDownloaderActivity.this.findViewById(R.id.download_audio_fab);
//        download_audio_fab.bringToFront();
//
//        download_images_fab = VideoDownloaderActivity.this.findViewById(R.id.download_images_fab);
//        download_images_fab.bringToFront();
//
//        download_video_fab_text = VideoDownloaderActivity.this.findViewById(R.id.download_video_fab_text);
//        download_audio_fab_text = VideoDownloaderActivity.this.findViewById(R.id.download_audio_fab_text);
//        download_images_fab_text = VideoDownloaderActivity.this.findViewById(R.id.download_images_fab_text);
//
//
//        btn_home = VideoDownloaderActivity.this.findViewById(R.id.btn_home);
//        btn_search_cancel = VideoDownloaderActivity.this.findViewById(R.id.btn_search_cancel);
//        btn_search = VideoDownloaderActivity.this.findViewById(R.id.btn_search);
//        btn_settings = VideoDownloaderActivity.this.findViewById(R.id.btn_settings);
//
//        registerForContextMenu(btn_settings);
//
//        isAllFabsVisible = false;
//    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        VideoDownloaderActivity.this.getMenuInflater().inflate(R.menu.toolbar, menu);
//    }

    private void toggle_fab_buttons() {
        if (!isAllFabsVisible) {
            download_video_fab.show();
            download_audio_fab.show();
            download_images_fab.show();
            download_video_fab_text.setVisibility(View.VISIBLE);
            download_audio_fab_text.setVisibility(View.VISIBLE);
            download_images_fab_text.setVisibility(View.VISIBLE);
            isAllFabsVisible = true;
        } else {
            download_video_fab.hide();
            download_audio_fab.hide();
            download_images_fab.hide();
            download_video_fab_text.setVisibility(View.GONE);
            download_audio_fab_text.setVisibility(View.GONE);
            download_images_fab_text.setVisibility(View.GONE);

            isAllFabsVisible = false;
        }
    }
//
//    private void set_button_click_events() {
//        download_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle_fab_buttons();
//            }
//        });
//
//        btn_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wv_go_to_home();
//            }
//        });
//
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigate_browser();
//            }
//        });
//
//
//        btn_search_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                set_searchbar_text("");
//                simpleWebView.setVisibility(View.GONE);
//                b1.setVisibility(View.VISIBLE);
//                download_fab.setVisibility(View.GONE);
//                download_audio_fab.setVisibility(View.GONE);
//                download_audio_fab_text.setVisibility(View.GONE);
//                download_images_fab.setVisibility(View.GONE);
//                download_images_fab_text.setVisibility(View.GONE);
//                download_video_fab.setVisibility(View.GONE);
//                download_video_fab_text.setVisibility(View.GONE);
//
//            }
//        });
//
//        et_search_bar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus && et_search_bar.getText().toString().equals(getResources().getString(R.string.enter_url))) {
//                    btn_search_cancel.setVisibility(View.VISIBLE);
//                    if(et_search_bar.equals("")){
//                        b1.setVisibility(View.VISIBLE);
//                        simpleWebView.setVisibility(View.GONE);
//                    }
//                    set_searchbar_text("");
//
//                }
//            }
//        });
//
//        et_search_bar.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                simpleWebView.setVisibility(View.VISIBLE);
//                b1.setVisibility(View.GONE);
//                download_fab.setVisibility(View.VISIBLE);
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    switch (keyCode) {
//                        case KeyEvent.KEYCODE_ENTER:
//                            navigate_browser();
//                            return true;
//                        default:
//                            break;
//                    }
//                }
//                return false;
//            }
//        });
//
//
//        download_video_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                _available_files_dialog = new available_files_dialog(file_type.VIDEO);
//                _available_files_dialog.show(VideoDownloaderActivity.this.getSupportFragmentManager(), "Videos");
//            }
//        });
//
//        download_images_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                _available_files_dialog = new available_files_dialog(file_type.IMAGE);
//                _available_files_dialog.show(VideoDownloaderActivity.this.getSupportFragmentManager(), "Images");
//            }
//        });
//
//        download_audio_fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                _available_files_dialog = new available_files_dialog(file_type.AUDIO);
//                _available_files_dialog.show(VideoDownloaderActivity.this.getSupportFragmentManager(), "Audios");
//            }
//        });
//
//        btn_settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    btn_settings.showContextMenu(0, 0);
//                } else {
//                    btn_settings.showContextMenu();
//                }
//            }
//        });
//
//        et_search_bar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                simpleWebView.setVisibility(View.VISIBLE);
//                b1.setVisibility(View.GONE);
//                download_fab.setVisibility(View.VISIBLE);
//                navigate_browser();
//            }
//        });
//    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) VideoDownloaderActivity.this.getSystemService(VideoDownloaderActivity.this.INPUT_METHOD_SERVICE);
        View view = VideoDownloaderActivity.this.getCurrentFocus();
        if (view == null) {
            view = new View(VideoDownloaderActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
//
//    private void findLocal() {
//        if (et_search_bar.getText().toString().contains("facebook.com")) {
//            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String html) {
//
//                    try {
//                        if (html != null) {
//                            JsonReader reader = new JsonReader(new StringReader(html));
//                            reader.setLenient(true);
//                            try {
//                                if (reader.peek() == JsonToken.STRING) {
//                                    String domStr = reader.nextString();
//                                    if (domStr != null) {
//                                        Document document = Jsoup.parse(domStr);
//                                        String videoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
//                                        ArrayList<downloadable_resource_model> video_files = new ArrayList<>();
//                                        static_variables.resourse_holder.setVideo_files(video_files);
//                                        static_variables.resourse_holder.add_Video(null, "video", videoUrl, "Video", "page");
//                                        VideoDownloaderActivity.this.runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
////                                                update_image_fab_text();
////                                                enable_Buttons();
//                                            }
//                                        });
//
//                                    }
//                                }
//                            } catch (IOException e) {
//                                // handle exception
//                            } finally {
//                                // IoUtil.close(reader);
//                            }
//                        }
//
//                    } catch (Exception ex) {
//                    }
//                }
//            });
//        }
//
//        // Customised Searches
//        String[] customised_searches_filters = mContext.getResources().getStringArray(R.array.customised_searches);
//        boolean MatchCustomSearch = false;
//        for (String filter : customised_searches_filters) {
//            if (et_search_bar.getText().toString().contains(filter)) {
//                MatchCustomSearch = true;
//            }
//        }
//        if (MatchCustomSearch) {
//            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String html) {
//                    List<CustomGrabberModel> customGrabberModelList = CustomSearch.Search(mContext, et_search_bar.getText().toString(), html);
//                    if (customGrabberModelList.size() > 0) {
//                        static_variables.resourse_holder = new resourse_holder_model();
//                    }
//                    for (CustomGrabberModel customGrabberModel : customGrabberModelList) {
//                        if (customGrabberModel.getVideoUrl() != null && customGrabberModel.getVideoUrl() != "") {
//                            if (customGrabberModel.getM3u8()) {
//                                static_variables.resourse_holder.add_Video(null, "m3u8", customGrabberModel.getVideoUrl(), "Video", "page");
//                            } else {
//                                if (static_variables.resourse_holder == null) {
//                                    static_variables.resourse_holder = new resourse_holder_model();
//                                }
//                                static_variables.resourse_holder.add_Video(null, "video", customGrabberModel.getVideoUrl(), "Video", "page");
//                            }
//                        }
//                    }
//                    VideoDownloaderActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            update_image_fab_text();
////                            enable_Buttons();
//                        }
//                    });
//
//                }
//            });
//        }
//
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.action_pp: {
////                AlertDialog.Builder alert = new AlertDialog.Builder(VideoDownloaderActivity.this);
////                alert.setTitle(getString(R.string.PrivacyPolicy));
////                WebView wv = new WebView(VideoDownloaderActivity.this);
////                wv.loadUrl("file:///android_asset/PrivacyPolicy.html");
////                wv.setWebViewClient(new WebViewClient());
////                alert.setView(wv);
////                alert.setNegativeButton(getString(R.string.Close), new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int id) {
////                        dialog.dismiss();
////                    }
////                });
////                alert.show();
////                return true;
////            }
//            case R.id.action_nonworking: {
//                et_search_bar.setText("https://forms.gle/gvoduNgZ4VVCXMd38");
//                navigate_browser();
//                return true;
//            }
//
////            case R.id.action_share_app: {
////                Intent _myIntent = new Intent(Intent.ACTION_SEND);
////                _myIntent.setType("text/plain");
////                String ShareBody = "Hi \nPlease check this Awesome Application. '" + getResources().getString(R.string.app_name) + "'\nYou'll love it. \n\nhttps://play.google.com/store/apps/details?id=" + VideoDownloaderActivity.this.getPackageName();
////                String ShareSub = getString(R.string.hithere);
////                _myIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
////                _myIntent.putExtra(Intent.EXTRA_TEXT, ShareBody);
////                startActivity(Intent.createChooser(_myIntent, getString(R.string.Shareusing)));
////                return true;
////            }
//
//
//            case R.id.action_whatsapp: {
////                AdUtils.showInterstitialAd(VideoDownloaderActivity.this, new AppInterfaces.InterStitialADInterface() {
////                    @Override
////                    public void adLoadState(boolean isLoaded) {
//                        Intent intent = new Intent(VideoDownloaderActivity.this, DownloadActivity.class);
//                        startActivity(intent);
////                    }
////                });
//
//                return true;
//            }
//
//        }
//        return true;
//    }
//
//    private void navigate_browser() {
//        hideKeyboard();
//        if (!Patterns.WEB_URL.matcher(et_search_bar.getText()).matches()) {
//            et_search_bar.setText("https://www.google.com/search?q=" + et_search_bar.getText());
//        }
//        simpleWebView.loadUrl(et_search_bar.getText().toString());
//    }
//
//    private void startTimer() {
//        final int secs = 10;
////        disable_fab_button();
//        loadingPageProgress.setProgress(0);
//        countDownTimer = new CountDownTimer((secs + 1) * 100, 1000) {
//            @Override
//            public final void onTick(final long millisUntilFinished) {
//                if (loadingPageProgress.getProgress() < 80) {
//                    loadingPageProgress.setProgress(loadingPageProgress.getProgress() + 8);
//                }
//            }
//
//            @Override
//            public final void onFinish() {
//
//            }
//        };
//        countDownTimer.start();
//        loadingPageProgress.setVisibility(View.VISIBLE);
//    }
//
//    private void stopTimer() {
//        try {
//            countDownTimer.cancel();
//        } catch (Exception ex) {
//        }
//        loadingPageProgress.setProgress(100);
//
//        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingPageProgress.setProgress(0);
//                loadingPageProgress.setVisibility(View.GONE);
//
//            }
//        }, 500);
//
//    }
//
//    private void set_searchbar_text(String text) {
//        if (text.equals(getResources().getString(R.string.index_page))) {
//            text = getResources().getString(R.string.enter_url);
//        }
//        if (text.equals("")) {
//            btn_search_cancel.setVisibility(View.VISIBLE);
//            b1.setVisibility(View.VISIBLE);
//            et_search_bar.requestFocus();
//        } else {
//            btn_search_cancel.setVisibility(View.VISIBLE);
//        }
//        et_search_bar.setText(text);
//    }
//
//
//    public class customWebClient extends WebViewClient {
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, String request) {
//            String url = request;
//            if ((url.contains("ad") || url.contains("banner") || url.contains("pop"))) {
//                return new WebResourceResponse(null, null, null);
//            }
//            return super.shouldInterceptRequest(view, request);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//            if (url.startsWith("intent://")) {
//                try {
//                    Context context = simpleWebView.getContext();
//                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    if (intent != null) {
//                        PackageManager packageManager = context.getPackageManager();
//                        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                        if ((intent != null) && ((intent.getScheme().equals("https")) || (intent.getScheme().equals("http")))) {
//                            String fallbackUrl = intent.getStringExtra("browser_fallback_url");
//                            simpleWebView.loadUrl(fallbackUrl);
//                            return true;
//                        }
//                        if (info != null) {
//                            context.startActivity(intent);
//                        } else {
//                            String fallbackUrl = intent.getStringExtra("browser_fallback_url");
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
//                            context.startActivity(browserIntent);
//                        }
//                        return true;
//                    } else {
//                        return false;
//                    }
//                } catch (Exception e) {
//                    return false;
//                }
//            }
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            startTimer();
//            isRedirected = false;
//            super.onPageStarted(view, url, favicon);
//            if (timer != null) {
//                timer.cancel();
//                timer = null;
//            }
//
//            set_searchbar_text(url);
//            static_variables.resourse_holder = new resourse_holder_model();
//        }

//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if (static_variables.resourse_holder == null) {
//                static_variables.resourse_holder = new resourse_holder_model();
//            }
//            static_variables.resourse_holder.setPage_title(view.getTitle());
//            if (!isRedirected) {
//                stopTimer();
//                enable_Buttons();
//                findLocal();
//                if (download_fab.isEnabled() == true) {
//                    YoYo.with(Techniques.Tada).duration(300).repeat(5).playOn(VideoDownloaderActivity.this.findViewById(R.id.download_fab));
//                }
//
////// **** Uncomment this code if you want to disable admob ad on any Website Open
////                if( getResources().getString(R.string.Ads).equals("ADMOB") ){
////                    if(! et_search_bar.getText().toString().equals(getResources().getString(R.string.home)) )
////                    {
////                        adViewMainAct.setVisibility(View.GONE);
////                        adViewMainAct.destroy();
////                    }
////                    else
////                    {
////                        adViewMainAct.setVisibility(View.VISIBLE);
////                        AdRequest adRequest = new AdRequest.Builder().build();
////                        adViewMainAct.loadAd(adRequest);
////                    }
////                }
//
//            }
//        }


//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            return super.shouldOverrideUrlLoading(view, request);
//        }
//
//        @Override
//        public void onLoadResource(final WebView view, final String url) {
//            if (!URLAddFilter.DoNotCheckIf(mContext, et_search_bar.getText().toString())) {
//                final String viewUrl = view.getUrl();
//                final String title = view.getTitle();
//                //Log.d("RESFILE",url);
//                new ContentSearch(mContext, url, viewUrl, title) {
//                    @Override
//                    public void onStartInspectingURL() {
//                        Utils.disableSSLCertificateChecking();
//                    }
//
//                    @Override
//                    public void onFinishedInspectingURL(boolean finishedAll) {
//                        HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
//                    }
//
//                    @Override
//                    public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
//                        static_variables.resourse_holder.add_Video(size, type, link, name, page);
//                        if (static_variables.resourse_holder.getVideo_files().size() > 0) {
//                            VideoDownloaderActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    update_image_fab_text();
//                                    enable_Buttons();
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onImageFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
//                        try {
//                            static_variables.resourse_holder.add_Image(size, type, link, name, page);
//                            if (static_variables.resourse_holder.getImage_files().size() > 0) {
//                                VideoDownloaderActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        update_image_fab_text();
//                                        enable_Buttons();
//                                    }
//                                });
//                            }
//                        } catch (Exception ex) {
//                        }
//                    }
//
//                    @Override
//                    public void onAudioFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
//                        static_variables.resourse_holder.add_Audio(size, type, link, name, page);
//                        if (static_variables.resourse_holder.getImage_files().size() > 0) {
//                            VideoDownloaderActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    update_audio_fab_text();
//                                    enable_Buttons();
//                                }
//                            });
//                        }
//                    }
//                }.start();
//            }
//        }
//    }


        private Dialog showDialogBox() {
            Dialog dialog = new Dialog(VideoDownloaderActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_dwnld_url);

            return dialog;

        }

//
//    private void enable_Buttons() {
//        if ((static_variables.resourse_holder.getVideo_files().size() > 0) || (static_variables.resourse_holder.getAudio_files().size() > 0) || (static_variables.resourse_holder.getImage_files().size() > 0)) {
//            enable_audio_fab();
//            enable_fab_button();
//        }
//        if ((static_variables.resourse_holder.getVideo_files().size() > 0)) {
//            enable_video_fab();
//            update_video_fab_text();
//        }
//        if ((static_variables.resourse_holder.getImage_files().size() > 0)) {
//            enable_images_fab();
//            update_video_fab_text();
//        }
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        init_components();
//        set_button_click_events();
//        wv_go_to_home();
//        disable_fab_button();
//    }


//    }

}