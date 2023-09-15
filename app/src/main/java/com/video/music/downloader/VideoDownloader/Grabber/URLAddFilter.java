package com.video.music.downloader.VideoDownloader.Grabber;

import android.content.Context;

import com.video.music.downloader.R;

public class URLAddFilter {

    public static boolean IsContainsAdURL(Context context, String  URL)
    {
        String urlLowerCase=URL.toLowerCase();
        boolean urlMightAd = false;
        String[] ad_filters = context.getResources().getStringArray(R.array.ad_site_filters);

        for (String filter : ad_filters) {
            if (urlLowerCase.contains(filter)) {
                urlMightAd = true;
                break;
            }
        }
        return urlMightAd;
    }


    public static boolean DoNotCheckIf(Context context, String  URL)
    {
        String urlLowerCase=URL.toLowerCase();
        boolean urlristricted = false;
        String[] url_filters = context.getResources().getStringArray(R.array.ristricted_sites);

        for (String filter : url_filters) {
            if (urlLowerCase.contains(filter)) {
                urlristricted = true;
                break;
            }
        }
        return urlristricted;
    }
}
