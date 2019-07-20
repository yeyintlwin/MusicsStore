package com.yeyintlwin.musicsstore.utils;

import android.content.Context;

public class Utils {
    public static boolean isConnected(Context context) {
        // return ((ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
        return true;
    }
}
