package com.yeyintlwin.musicsstore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PicassoCacheRecycle {

    @SuppressLint("StaticFieldLeak")
    private static PicassoCacheRecycle picassoCacheRecycle;
    private Context mContext;

    private PicassoCacheRecycle(Context context) {
        mContext = context;
    }

    public static PicassoCacheRecycle with(Context context) {
        if (picassoCacheRecycle == null) picassoCacheRecycle = new PicassoCacheRecycle(context);
        return picassoCacheRecycle;
    }

    public Bitmap getBitmap(String url) throws IOException {
        return BitmapFactory.decodeFile(getPath(url));
    }

    public File getFile(String url) throws IOException {
        return new File(getPath(url));
    }

    private String getPath(String url) throws IOException {
        final String CACHE_PATH = mContext.getCacheDir().getAbsolutePath() + "/picasso-cache/";
        File[] files = new File(CACHE_PATH).listFiles();
        for (File file : files) {
            String fname = file.getName();
            if (fname.contains(".") && fname.substring(fname.lastIndexOf(".")).equals(".0")) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                if (br.readLine().equals(url)) {
                    String image_path = CACHE_PATH + fname.replace(".0", ".1");
                    if (new File(image_path).exists()) {
                        return image_path;
                    }
                }
            }
        }
        return "";
    }
}
