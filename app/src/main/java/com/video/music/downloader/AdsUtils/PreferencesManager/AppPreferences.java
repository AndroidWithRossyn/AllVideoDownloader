package com.video.music.downloader.AdsUtils.PreferencesManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;


import com.google.gson.Gson;
import com.video.music.downloader.R;

public class AppPreferences {

    private static AppPreferences appPreferences;

    private Context applicationContext;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context applicationContext) {

        this.applicationContext = applicationContext;
        Resources res = this.applicationContext.getResources();
        // Logger.debug("res: "+res);
        gson = new Gson();
        String preferencesName = res.getString(R.string.app_name);
        sharedPreferences = this.applicationContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    public static AppPreferences getAppPreferences(Context applicationContext) {

        if (appPreferences != null) {

            return appPreferences;
        }
        appPreferences = new AppPreferences(applicationContext);
        return appPreferences;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }


    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
