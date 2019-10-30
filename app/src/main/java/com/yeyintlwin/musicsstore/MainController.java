package com.yeyintlwin.musicsstore;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;

public class MainController extends Application {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(String key, Boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static void putInt(String key, Integer value) {
        editor.putInt(key, value).apply();
    }

    public static Integer getInt(String key, Integer defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        initDownloader();
    }

    private void initDownloader() {
        DownloadConfiguration downloadConfiguration = new DownloadConfiguration();
        downloadConfiguration.setMaxThreadNum(10);
        downloadConfiguration.setThreadNum(1);
        DownloadManager.getInstance().init(this, downloadConfiguration);
    }
}