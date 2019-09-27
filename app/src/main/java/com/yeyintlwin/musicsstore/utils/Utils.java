package com.yeyintlwin.musicsstore.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.yeyintlwin.musicsstore.R;

import java.io.File;
import java.util.ArrayList;

public class Utils {
    public static boolean isConnected(Context context) {
        // return ((ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
        return true;
    }

    public static int getColumn(Activity activity, int widgetMaxWidth) {
        float f = activity.getResources().getDisplayMetrics().density * widgetMaxWidth;
        return (int) (activity.getWindowManager().getDefaultDisplay().getWidth() / f);
    }

    public static int max(int[] arr) {
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > maxValue)
                maxValue = arr[i];
        return maxValue;
    }

    public static String fontStand(String text) {
        return text;
    }

    public static ArrayList<String> getMp3Files(Context context) throws Exception {
        ArrayList<String> mp3Files = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(),
                "/" + context.getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdir();
        } else {
            File[] files = file.listFiles();
            if (files.length == 0) {
                throw new Exception("File not found.");
            } else {
                for (File file1 : files) {
                    if (isMp3File(file1)) {
                        mp3Files.add(file1.getAbsolutePath());
                    }
                }
            }

        }

        return mp3Files;
    }

    private static boolean isMp3File(File file) {
        if (!file.isFile()) return false;
        String name = file.getName();
        if (!name.contains(".")) return false;
        String exe = name.substring(name.lastIndexOf("."));
        return exe.equals(".mp3") || exe.equals(".MP3") || exe.equals(".Mp3") || exe.equals(".mP3");
    }
}
