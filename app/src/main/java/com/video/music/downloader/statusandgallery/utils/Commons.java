package com.video.music.downloader.statusandgallery.utils;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.VideoDownloader.Models.file_type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public  class Commons {

    public static String SanitizeTitle(String titleUrl)
    {
        String NewTitle="";
        int maxLength=15;
        if(titleUrl.length() > maxLength)
        {
            NewTitle= titleUrl.substring(0, Math.min(titleUrl.length(), maxLength));
        }
        else
        {
            NewTitle=titleUrl;
        }
        return NewTitle.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public static long startDownload(String str, String str2, Context context2, String str3, Context mContext, file_type _file_type)
    {
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
            request.setAllowedNetworkTypes(3);
            request.setNotificationVisibility(1);
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            request.setTitle(sb.toString());
            String str4 = SettingsManager.DOWNLOAD_FOLDER_DIR_NAME;
            if(_file_type==file_type.AUDIO)
            {
                str4= str4 + "/" + SettingsManager.DOWNLOAD_FOLDER_AUDIO_NAME ;
            }
            else if(_file_type==file_type.VIDEO)
            {
                str4= str4 + "/" + SettingsManager.DOWNLOAD_FOLDER_VIDEO_NAME ;
            }
            else if(_file_type==file_type.IMAGE)
            {
                str4= str4 + "/" + SettingsManager.DOWNLOAD_FOLDER_IMAGES_NAME ;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,str4 + "/"+ sb2.toString());
            Toast.makeText(mContext,mContext.getString(R.string.FileDownloadstarted),Toast.LENGTH_LONG).show();
            return ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
        }
        catch (Exception ex)
        {
            Toast.makeText(mContext,"Unable to download this file",Toast.LENGTH_LONG).show();
            return  0;
        }
    }

    public static  boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static List<String> extractUrls(String input) {
        List<String> result = new ArrayList<String>();

        input=input.trim();
        if(URLUtil.isValidUrl(input))
        {
            result.add(input);
        }
        else
        {
            Pattern pattern = Pattern.compile(
                    "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                            "(\\w+:)?(([-\\w]+\\.)+(com|org|net|gov" +
                            "|mil|biz|info|mobi|name|aero|jobs|museum" +
                            "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                result.add(matcher.group());
            }
        }

        return result;
    }





}
