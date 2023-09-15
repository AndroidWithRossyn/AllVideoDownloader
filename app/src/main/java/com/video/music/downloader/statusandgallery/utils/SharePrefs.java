package com.video.music.downloader.statusandgallery.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefs {
    public static String PREFERENCE = "AllInOneDownloader";

    public static String ISSHOWHOWTOFB = "IsShoHowToFB";
    public static String ISSHOWHOWTOTT = "IsShoHowToTT";
    public static String ISSHOWHOWTOINSTA = "IsShoHowToInsta";
    public static String ISSHOWHOWTOTWITTER = "IsShoHowToTwitter";
    public static String ISSHOWHOWTOSHARECHAT = "IsShoHowToSharechat";
    public static String ISSHOWHOWTOROPOSO = "IsShoHowToRoposo";
    public static String ISSHOWHOWTOSNACK = "IsShoHowToSnack";
    public static String ISSHOWHOWTOJOSH = "IsShoHowToJosh";
    public static String ISSHOWHOWTOCHINGARI = "IsShoHowToChingari";
    public static String ISSHOWHOWTOMITRON = "IsShoHowToMitron";
    public static final String ISFBLOGIN = "isFbLogin";
    public static final String FBKEY = "fbKey";
    public static final String FBCOOKIES = "fbCookies";

    private Context ctx;
    private SharedPreferences sharedPreferences;
    private static SharePrefs instance;

    public static String SESSIONID = "session_id";
    public static String USERID = "user_id";
    public static String COOKIES = "Cookies";
    public static String CSRF = "csrf";
    public static String ISINSTALOGIN = "IsInstaLogin";
    public static String ISSHOWHOWTOLIKEE = "IsShoHowToLikee";

    public SharePrefs(Context context) {
        ctx = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharePrefs getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharePrefs(ctx);
        }
        return instance;
    }

    public void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putInt(String key, Integer val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }

    public void putBoolean(String key, Boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void clearSharePrefs() {
        sharedPreferences.edit().clear().apply();
    }
}
