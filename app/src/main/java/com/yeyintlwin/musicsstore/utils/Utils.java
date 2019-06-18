package com.yeyintlwin.musicsstore.utils;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {
    public static boolean isConnected(Context context) {
        return ((ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
