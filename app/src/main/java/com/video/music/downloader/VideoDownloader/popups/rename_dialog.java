package com.video.music.downloader.VideoDownloader.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

////import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.VideoDownloader.Models.downloadable_resource_model;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Utils.Commons;
import com.video.music.downloader.VideoDownloader.m3u8DownloaderService;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class rename_dialog  extends AppCompatDialogFragment {

    private downloadable_resource_model result;
    private Context mContext;
    private Activity activity;
    EditText txtFileName;
    Button btnDownload;
//    private com.facebook.ads.InterstitialAd FBinterstitialAd;
    private InterstitialAd mInterstitialAd;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.rename_dialog,null);
        mContext=this.getContext();
        builder.setView(view)
                .setPositiveButton(mContext.getString(R.string.Close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        btnDownload=view.findViewById(R.id.btnDownloadNow);

        activity=getActivity();

//        AdRequest adRequestinter = new AdRequest.Builder().build();
//        InterstitialAd.load(mContext,getResources().getString(R.string.AdmobInterstitial), adRequestinter, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                mInterstitialAd = interstitialAd;
//            }
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                mInterstitialAd = null;
//            }
//        });
//
//        AudienceNetworkAds.initialize(mContext);
//        FBinterstitialAd = new com.facebook.ads.InterstitialAd(mContext, getResources().getString(R.string.FB_Interstitial_Ad_PlacemaneId));
//        FBinterstitialAd.loadAd(FBinterstitialAd.buildLoadAdConfig().build());


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append( Commons.SanitizeTitle(txtFileName.getText().toString()) + "_");
                sb.append(System.currentTimeMillis());

                if(result.getFile_type()== file_type.IMAGE)
                {
                    String extension="jpg";
                    try {
                         extension = MimeTypeMap.getFileExtensionFromUrl(result.getURL());
                    }
                    catch (Exception ex)
                    {}
                    if(extension==null || extension.equals(""))
                    {
                        extension="jpg";
                    }


                    sb.append("."+ extension);
                }
                else if(result.getFile_type()== file_type.VIDEO)
                {
                    sb.append(".mp4");
                }
                else if(result.getFile_type()== file_type.AUDIO)
                {
                    sb.append(".mp3");
                }

                if(result.getFile_type()== file_type.VIDEO && result.getURL().contains("m3u8") )
                {
                    if(Commons.isMyServiceRunning( m3u8DownloaderService.class,mContext))
                    {
                        if(!SettingsManager.IsDownloadComplete==true)
                        {
                            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(mContext.getString(R.string.Wait))
                                    .setContentText(mContext.getString(R.string.Pleasewaituntilfirstdownloadcomplete))
                                    .show();
                        }
                        else
                        {
                            StartM3u8Service();
                        }
                    }
                    else {
                        StartM3u8Service();
                    }

                }
                else
                {
//                    ShowAd();
                    Commons.startDownload( result.getURL() ,"",mContext,sb.toString(),mContext,result.getFile_type());
                    dismiss();
                }
            }
        });

        txtFileName=view.findViewById(R.id.txtFileNameNew);
        txtFileName.setText(result.getTitle());
        return  builder.create();
    }
    public rename_dialog(downloadable_resource_model _result)
    {
        result=_result;
    }

//
//    private void ShowAd()
//    {
//        if( getResources().getString(R.string.Ads).equals("ADMOB") ){
//            if (mInterstitialAd != null) {
//                mInterstitialAd.show(activity);
//            }
//        }
//        else if (getResources().getString(R.string.Ads).equals("FACEBOOK")){
//            if(FBinterstitialAd.isAdLoaded()){
//                FBinterstitialAd.show();
//            }
//        }
//    }

    private void StartM3u8Service()
    {
        Intent serviceIntent=new Intent(mContext, m3u8DownloaderService.class);
        serviceIntent.putExtra("URL",result.getURL());
        serviceIntent.putExtra("FinalOutputFileName",result.getTitle());
        mContext.startService(serviceIntent);
//        ShowAd();
        dismiss();
    }

}
