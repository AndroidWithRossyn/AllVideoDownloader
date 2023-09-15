package com.video.music.downloader.statusandgallery.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLangSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AppLangPref";


    public static final String KEY_APP_LANGUAGE = "en";




    public AppLangSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }





    public void setLanguage(String lang) {
        editor.putString(KEY_APP_LANGUAGE,lang);
        editor.commit();
    }





    public String getLanguage() {
        return pref.getString(KEY_APP_LANGUAGE, "");
    }








}

