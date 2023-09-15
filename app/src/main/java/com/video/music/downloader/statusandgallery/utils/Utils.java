package com.video.music.downloader.statusandgallery.utils;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.video.music.downloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Dialog customDialog;
    private static Context context;


    public static String RootDirectoryFacebook = "/StatusSaver/Facebook/";
    public static String RootDirectoryInsta = "/StatusSaver/Insta/";
    public static String RootDirectoryTikTok = "/StatusSaver/TikTok/";
    public static String RootDirectoryTwitter = "/StatusSaver/Twitter/";
    public static String RootDirectoryLikee = "/StatusSaver/Likee/";
    public static String RootDirectoryShareChat = "/StatusSaver/ShareChat/";
    public static String RootDirectoryRoposo = "/StatusSaver/Roposo/";
    public static String RootDirectorySnackVideo = "/StatusSaver/SnackVideo/";
    public static final String ROOTDIRECTORYJOSH = "/StatusSaver/Josh/";
    public static final String ROOTDIRECTORYCHINGARI = "/StatusSaver/Chingari/";
    public static final String ROOTDIRECTORYMITRON = "/StatusSaver/Mitron/";
    public static final String ROOTDIRECTORYMX = "/StatusSaver/Mxtakatak/";
    public static final String ROOTDIRECTORYMOJ = "/StatusSaver/Moj/";

    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Facebook");
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Insta");
    public static File RootDirectoryTikTokShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/TikTok");
    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Twitter");
    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Whatsapp");
    public static File RootDirectoryLikeeShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Likee");
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/ShareChat");
    public static File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Roposo");
    public static File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/SnackVideo");
    public static final File ROOTDIRECTORYJOSHSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Josh");
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Chingari");
    public static final File ROOTDIRECTORYMITRONSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Mitron");
    public static final File ROOTDIRECTORYMXSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Mxtakatak");
    public static final File ROOTDIRECTORYMOJSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Moj");

    public static String PrivacyPolicyUrl = "\n" + "https://theknowledgeset.com/status/privacy-policy.html";
    public static String TikTokUrl = "http://androidqueue.com/tiktokapi/api.php";

    public Utils(Context _mContext) {
        context = _mContext;
    }

    public static void setToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void createFileFolder() {
        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }
        if (!RootDirectoryTikTokShow.exists()) {
            RootDirectoryTikTokShow.mkdirs();
        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()) {
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()) {
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryShareChatShow.exists()) {
            RootDirectoryShareChatShow.mkdirs();
        }
        if (!RootDirectoryRoposoShow.exists()) {
            RootDirectoryRoposoShow.mkdirs();
        }
        if (!RootDirectorySnackVideoShow.exists()) {
            RootDirectorySnackVideoShow.mkdirs();
        }
        if (!ROOTDIRECTORYJOSHSHOW.exists()) {
            ROOTDIRECTORYJOSHSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYCHINGARISHOW.exists()) {
            ROOTDIRECTORYCHINGARISHOW.mkdirs();
        }
        if (!ROOTDIRECTORYMITRONSHOW.exists()) {
            ROOTDIRECTORYMITRONSHOW.mkdirs();
        }

        if (!ROOTDIRECTORYMXSHOW.exists()) {
            ROOTDIRECTORYMXSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYMOJSHOW.exists()) {
            ROOTDIRECTORYMOJSHOW.mkdirs();
        }

    }

    public static void showProgressDialog(Activity activity) {
        System.out.println("Show");
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View mView = inflater.inflate(R.layout.progress_dialog, null);
        customDialog.setCancelable(false);
        customDialog.setContentView(mView);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog(Activity activity) {
        System.out.println("Hide");
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void startDownload(String downloadPath, String destinationPath, Context context, String FileName) {
        setToast(context, context.getResources().getString(R.string.download_started));
        Uri uri = Uri.parse(downloadPath); // Path where you want to download file.
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  // This will show notification on top when downloading the file.
        request.setTitle(FileName + ""); // Title for notification.
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, destinationPath + FileName);  // Storage directory path
        ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request); // This will start downloading

        try {
            if (Build.VERSION.SDK_INT >= 19) {
                MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName).getAbsolutePath()},
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            } else {
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + destinationPath + FileName))));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void shareImage(Context context, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_txt));
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), filePath, "", null);
            Uri screenshotUri = Uri.parse(path);
            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            intent.setType("image/*");
            context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_image_via)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void shareImageVideoOnWhatsapp(Context context, String filePath, boolean isVideo) {
        Uri imageUri = Uri.parse(filePath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        if (isVideo) {
            shareIntent.setType("video/*");
        } else {
            shareIntent.setType("image/*");
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Utils.setToast(context, context.getResources().getString(R.string.whatsapp_not_installed));
        }
    }

    public static void shareVideo(Context context, String filePath) {
        Uri mainUri = Uri.parse(filePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("video/mp4");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getResources().getString(R.string.no_app_installed), Toast.LENGTH_LONG).show();
        }
    }

    public static void RateApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void MoreApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=Softin+Solutions")));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Softin+Solutions")));
        }
    }

    public static void ShareApp(Context context) {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    public static void OpenApp(Context context, String Package) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(Package);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            setToast(context, context.getResources().getString(R.string.app_not_available));
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0) || (s.equalsIgnoreCase("null")) || (s.equalsIgnoreCase("0"));
    }

    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    public static void infoDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getResources().getString(R.string.ok),
                        (dialog, which) -> dialog.dismiss()).create().show();
    }





    public static String m15861b(String str, int i, boolean z, boolean z2, boolean z3, String str2) {
        int i2=0;
        String str3="";
        int i3=0;
        String str4="";
        int i4=0;
        String str5="";
        int i5=0;
        String str6="";
        String str7 = "\n";
        String str8 = " ";
        String str9 = "";
        int i6 = 0;
        if (z3) {
            if (z && !z2) {
                while (i6 < i) {
                    str9 = str9 + str + str8 + str2;
                    i6++;
                }
                return str9;
            } else if (!z && z2) {
                while (i6 < i) {
                    str9 = str9 + str + str8 + str2 + str7;
                    i6++;
                }
                return str9;
            } else if (!z || !z2) {
                return m15860a(str + str2, i);
            } else {
                while (i5 < i) {
                    str6 = str6 + str + str8 + str2 + str7;
                    i5++;
                }
                return str6;
            }
        } else if (z && !z2) {
            while (i4 < i) {
                str5 = m5901c(str5, str, str8);
                i4++;
            }
            return str5;
        } else if (!z && z2) {
            while (i3 < i) {
                str4 = m5901c(str4, str, str7);
                i3++;
            }
            return str4;
        } else if (!z || !z2) {
            return m15860a(str, i);
        } else {
            while (i2 < i) {
                str3 =m5901c(str3, str, " \n");
                i2++;
            }
            return str3;
        }
    }

    public static String m15860a(String str, int i) {
        String str2 = "";
        for (int i2 = 0; i2 < i; i2++) {
            str2 = m5920v(str2, str);
        }
        return str2;
    }
    public static String m5920v(String str, String str2) {
        return str + str2;
    }

    public static String m5901c(String str, String str2, String str3) {
        return str + str2 + str3;
    }
}