package com.yeyintlwin.musicsstore.database.downloadqueue;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yeyintlwin.musicsstore.database.downloadqueue.entity.DownloadMusicInfo;

import java.util.List;

public class DownloadDatabaseManager {
    @SuppressLint("StaticFieldLeak")
    private static DownloadDatabaseManager databaseManager;
    private MusicInfoDao musicInfoDao;

    private DownloadDatabaseManager(Context context) {
        musicInfoDao = MusicInfoDao.getInstance(context);
    }

    public static DownloadDatabaseManager getInstance(Context context) {
        if (databaseManager == null)
            databaseManager = new DownloadDatabaseManager(context);
        return databaseManager;
    }

    public void addMusic(DownloadMusicInfo data) {
        musicInfoDao.insertMusic(data);
    }

    public void delMusic(String id) {
        musicInfoDao.deleteMusic(id);
    }

    public List<DownloadMusicInfo> getAllMusic() {
        return musicInfoDao.getAllMusics();
    }


}
