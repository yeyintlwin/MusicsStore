package com.yeyintlwin.musicsstore.service.downloader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.DownloadRequest;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.io.File;
import java.util.Objects;

public class DownloadService extends Service {

    public static final String EXTRA_INFO = "extra_app_info";
    //  public static final String EXTRA_POSITION = "extra_position";
    //  public static final String EXTRA_TAG = "extra_tag";

    public static final String ACTION_DOWNLOAD = "download";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_PAUSE_ALL = "pause_all";
    public static final String ACTION_CANCEL = "cancel";
    public static final String ACTION_CANCEL_ALL = "cancel_all";


    private DownloadManager downloadManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadManager = DownloadManager.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("DownloadService", "onStartCommand()");
        if (intent != null) {
            MusicInfo info = (MusicInfo) intent.getSerializableExtra(EXTRA_INFO);
            String link = info.getLink();
            switch (Objects.requireNonNull(intent.getAction())) {
                case ACTION_DOWNLOAD:
                    downloadManager.download(new DownloadRequest.Builder()
                                    .setName(info.getTitle() + ".mp3.tmp")  //OUTPUT FILE NAME
                                    .setUri(link)  //DOWNLOAD LINK
                                    .setFolder(new File(Utils.getDownloadDir(this)))  //OUTPUT DIRECTION
                                    .build(),
                            link,
                            new DownloadCallBack(this, info));
                    break;
                case ACTION_PAUSE:
                    downloadManager.pause(link);
                    break;
                case ACTION_PAUSE_ALL:
                    downloadManager.pauseAll();
                    break;
                case ACTION_CANCEL:
                    downloadManager.cancel(link);
                    break;
                case ACTION_CANCEL_ALL:
                    downloadManager.cancelAll();
                    break;

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadManager.pauseAll();
    }
}
