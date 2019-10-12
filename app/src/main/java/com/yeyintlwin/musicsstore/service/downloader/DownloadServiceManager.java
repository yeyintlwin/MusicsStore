package com.yeyintlwin.musicsstore.service.downloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.yeyintlwin.musicsstore.database.downloadqueue.DownloadDatabaseManager;
import com.yeyintlwin.musicsstore.database.downloadqueue.entity.DownloadMusicInfo;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

public class DownloadServiceManager {
    @SuppressLint("StaticFieldLeak")
    private static DownloadServiceManager serviceManager = null;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private DownloadServiceManager() {

    }

    public static DownloadServiceManager getInstance(Context context) {
        mContext = context;
        if (serviceManager == null) serviceManager = new DownloadServiceManager();
        return serviceManager;
    }

    public void startDownload(MusicInfo data) {
        DownloadMusicInfo downloadMusicInfo = new DownloadMusicInfo();
        downloadMusicInfo.setId(data.getId());
        downloadMusicInfo.setTitle(data.getTitle());
        downloadMusicInfo.setArtist(data.getArtist());
        downloadMusicInfo.setGenre(data.getGenre());
        downloadMusicInfo.setAlbum(data.getAlbum());
        downloadMusicInfo.setCover(data.getCover());
        downloadMusicInfo.setLink(data.getLink());
        DownloadDatabaseManager.getInstance(mContext).addMusic(downloadMusicInfo);

        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD);
        intent.putExtra(DownloadService.EXTRA_INFO, data);
        mContext.startService(intent);
    }

    public void pauseDownload(MusicInfo data) {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setAction(DownloadService.ACTION_PAUSE);
        intent.putExtra(DownloadService.EXTRA_INFO, data);
        mContext.startService(intent);

    }

    public void pauseAllDownload() {
        Intent intent = new Intent(mContext, DownloadService.class);
        mContext.startService(intent);
    }

    public void destory() {
        Intent intent = new Intent(mContext, DownloadService.class);
        mContext.startService(intent);
    }

}
