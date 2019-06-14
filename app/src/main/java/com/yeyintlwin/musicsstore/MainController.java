package com.yeyintlwin.musicsstore;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainController extends Application {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }

    public static void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(String key, Boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static void putInt(String key, Integer value){
        editor.putInt(key,value).apply();
    }

    public static Integer getInt(String key, Integer defValue){
        return preferences.getInt(key,defValue);
    }
}
