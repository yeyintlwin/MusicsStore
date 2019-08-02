package com.yeyintlwin.musicsstore.utils;

import android.app.Activity;
import android.content.Context;

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
}
