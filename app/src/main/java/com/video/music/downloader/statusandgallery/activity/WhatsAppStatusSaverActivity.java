package com.video.music.downloader.statusandgallery.activity;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.video.music.downloader.statusandgallery.utils.Utils.createFileFolder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.video.music.downloader.R;
import com.video.music.downloader.databinding.ActivityWhatsAppStatusSaverBinding;
import com.video.music.downloader.databinding.DialogWhatsappPermissionBinding;
////import com.video.music.downloader.statusandgallery.AdsUtils.FirebaseADHandlers.AdUtils;
////import com.video.music.downloader.statusandgallery.AdsUtils.Interfaces.AppInterfaces;
//import com.video.music.downloader.statusandgallery.AdsUtils.PreferencesManager.AppPreferencesManger;
//import com.video.music.downloader.statusandgallery.AdsUtils.Utils.Constants;
import com.video.music.downloader.statusandgallery.fragment.WhatsappQImageFragment;
import com.video.music.downloader.statusandgallery.fragment.WhatsappQVideoFragment;
import com.video.music.downloader.statusandgallery.fragment.statussaver.WhatsappImageFragment;
import com.video.music.downloader.statusandgallery.fragment.statussaver.WhatsappVideoFragment;
import com.video.music.downloader.statusandgallery.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WhatsAppStatusSaverActivity extends AppCompatActivity {
    private ActivityWhatsAppStatusSaverBinding binding;
    private WhatsAppStatusSaverActivity activity;
    LinearLayout adContainer;
//    private AppPreferencesManger appPreferencesManger;

    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_whats_app_status_saver);
        activity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.main_color2));
        }
        adContainer = findViewById(R.id.native_ads);
//        appPreferencesManger = new AppPreferencesManger(this);
        createFileFolder();
        initViews();



    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    private void initViews() {

        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AdUtils.showInterstitialAd(WhatsAppStatusSaverActivity.this, new AppInterfaces.InterStitialADInterface() {
//                    @Override
//                    public void adLoadState(boolean isLoaded) {
                        finish();
//                    }
//                });
            }
        });


//        binding.LLOpenWhatsapp.setOnClickListener(v -> {
//            Utils.OpenApp(activity,"com.whatsapp");
//        });
        fileArrayList = new ArrayList<>();
        initProgress();

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q){
            if (getContentResolver().getPersistedUriPermissions().size() > 0) {
                progressDialog.show();
                new LoadAllFiles().execute();
                binding.tvAllowAccess.setVisibility(View.GONE);
            }else{
                binding.tvAllowAccess.setVisibility(View.VISIBLE);
            }
        }else {
            setupViewPager(binding.viewpager);
            binding.tabs.setupWithViewPager(binding.viewpager);

            for (int i = 0; i < binding.tabs.getTabCount(); i++) {
                TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
                binding.tabs.getTabAt(i).setCustomView(tv);
            }
        }
        binding.tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(activity, R.style.SheetDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DialogWhatsappPermissionBinding dialogWhatsappPermissionBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_whatsapp_permission, null, false);
                dialog.setContentView(dialogWhatsappPermissionBinding.getRoot());
                dialogWhatsappPermissionBinding.tvAllow.setOnClickListener(v -> {
                    try {
                        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
                            StorageManager sm = (StorageManager) activity.getSystemService(Context.STORAGE_SERVICE);
                            Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                            String startDir = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
                            Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                            String scheme = uri.toString();
                            scheme = scheme.replace("/root/", "/document/");
                            scheme += "%3A" + startDir;
                            uri = Uri.parse(scheme);
                            intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                            startActivityForResult(intent, 2001);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                });
                dialog.show();
            }
        });
    }
    public void initProgress(){
        progressDialog = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Status. Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new WhatsappImageFragment(), getResources().getString(R.string.images));
        adapter.addFragment(new WhatsappVideoFragment(), getResources().getString(R.string.videos));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2001 && resultCode == RESULT_OK) {
                Uri dataUri = data.getData();
                if (dataUri.toString().contains(".Statuses")) {
                    getContentResolver().takePersistableUriPermission(dataUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    progressDialog.show();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        new LoadAllFiles().execute();
                    }
                }else{
                    Utils.infoDialog(activity,activity.getResources().getString(R.string.wrong_folder), activity.getResources().getString(R.string.selected_wrong_folder));
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    class LoadAllFiles extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... furl) {
            DocumentFile documentFile = DocumentFile.fromTreeUri(activity, activity.getContentResolver().getPersistedUriPermissions().get(0).getUri());
            for (DocumentFile file : documentFile.listFiles()) {
                if (file.isDirectory()) {

                } else {
                    if (!file.getName().equals(".nomedia")) {
                        fileArrayList.add(file.getUri());
                    }
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... progress) {

        }
        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            adapter.addFragment(new WhatsappQImageFragment(fileArrayList), getResources().getString(R.string.images));
            adapter.addFragment(new WhatsappQVideoFragment(fileArrayList), getResources().getString(R.string.videos));
            binding.viewpager.setAdapter(adapter);
            binding.viewpager.setOffscreenPageLimit(1);
            binding.tabs.setupWithViewPager(binding.viewpager);
            binding.tvAllowAccess.setVisibility(View.GONE);
            for (int i = 0; i < binding.tabs.getTabCount(); i++) {
                TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
                binding.tabs.getTabAt(i).setCustomView(tv);
            }
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }




}
