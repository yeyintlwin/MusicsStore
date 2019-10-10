package com.yeyintlwin.musicsstore.service.downloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.aspsine.multithreaddownload.CallBack;
import com.aspsine.multithreaddownload.DownloadException;
import com.aspsine.multithreaddownload.DownloadManager;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.io.File;

public class DownloadCallBack implements CallBack {

    public static final String ACTION_DOWNLOAD_BROAD_CAST = "download_broad_cast";

    private LocalBroadcastManager broadcastManager;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private MusicInfo mMusicInfo;
    private Context mContext;

    private long lastTime;

    DownloadCallBack(Context context, MusicInfo musicInfo) {
        this.mMusicInfo = musicInfo;
        this.mContext = context;
        notificationManager = NotificationManagerCompat.from(context);
        broadcastManager = LocalBroadcastManager.getInstance(context);
        //noinspection deprecation
        notificationBuilder = new NotificationCompat.Builder(context);
    }

    @Override
    public void onStarted() {
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cover));
        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(mMusicInfo.getTitle())
                .setContentText("Init Download")
                .setProgress(100, 0, true)
                .setTicker("Start download " + mMusicInfo.getTitle());
        updateNotification(Integer.parseInt(mMusicInfo.getId()));
    }

    @Override
    public void onConnecting() {
        mMusicInfo.setStatus(MusicInfo.STATUS_CONNECTING);
        sendBroadCast(mMusicInfo);
        notificationBuilder.setContentText("Connecting").setProgress(100, 0, true);
        updateNotification(Integer.parseInt(mMusicInfo.getId()));
    }

    @Override
    public void onConnected(long total, boolean isRangeSupport) {
        notificationBuilder.setContentText("Connected").setProgress(100, 0, true);
        updateNotification(Integer.parseInt(mMusicInfo.getId()));
    }

    @Override
    public void onProgress(long finished, long total, int progress) {
        mMusicInfo.setStatus(MusicInfo.STATUS_DOWNLOADING);
        mMusicInfo.setProgress(progress);
        mMusicInfo.setPerSize(Utils.getDownloadPerSize(finished, total));

        if (lastTime == 0) lastTime = System.currentTimeMillis();

        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTime > 500) {
            notificationBuilder.setContentText("Downloading");
            notificationBuilder.setProgress(100, progress, false);
            updateNotification(Integer.parseInt(mMusicInfo.getId()));
            sendBroadCast(mMusicInfo);
            lastTime = currentTimeMillis;
        }
    }

    @Override
    public void onCompleted() {
        mMusicInfo.setStatus(MusicInfo.STATUS_COMPLETE);
        mMusicInfo.setProgress(100);
        sendBroadCast(mMusicInfo);

        notificationBuilder.setContentText("Download Complete");
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setTicker(mMusicInfo.getTitle() + " download complete.");
        updateNotification(Integer.parseInt(mMusicInfo.getId()));

        File file = new File(Utils.getDownloadDir(mContext), mMusicInfo.getTitle() + ".mp3.tmp");
        if (file.isFile() && file.exists()) {
            File file2 = new File(file.getParent(), mMusicInfo.getTitle() + ".mp3");
            //noinspection ResultOfMethodCallIgnored
            file.renameTo(file2);
            //AudioTag.getInstance(context).setAudioTag(file2, musicInfo);

            Toast.makeText(mContext, mMusicInfo.getTitle() + ".mp3 is download complete.", Toast.LENGTH_SHORT).show();
            ((Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0, 50, 100, 100, 100, 100}, -1);

            //Delete item data from downloader database
            DownloadManager.getInstance().delete(mMusicInfo.getLink());
        }
    }

    @Override
    public void onDownloadPaused() {
        mMusicInfo.setStatus(MusicInfo.STATUS_PAUSED);
        sendBroadCast(mMusicInfo);

        notificationBuilder.setContentText("Download Paused");
        notificationBuilder.setTicker(mMusicInfo.getTitle() + " download Paused");
        notificationBuilder.setProgress(100, mMusicInfo.getProgress(), false);
        updateNotification(Integer.parseInt(mMusicInfo.getId()));
    }

    @Override
    public void onDownloadCanceled() {
        mMusicInfo.setStatus(MusicInfo.STATUS_NOT_DOWNLOAD);
        mMusicInfo.setProgress(0);
        mMusicInfo.setPerSize("");
        sendBroadCast(mMusicInfo);

        notificationBuilder.setContentText("Download Canceled");
        notificationBuilder.setTicker(mMusicInfo.getTitle() + " download canceled.");
        updateNotification(Integer.parseInt(mMusicInfo.getId()));

        //there is 1000 ms memory leak, shouldn't be a problem
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.cancel(Integer.parseInt(mMusicInfo.getId()) + 1000);
            }
        }, 1000);
    }

    @Override
    public void onFailed(DownloadException e) {
        Log.w("DownloadCallBack", e);
        mMusicInfo.setStatus(MusicInfo.STATUS_DOWNLOAD_ERROR);
        mMusicInfo.setPerSize("");
        sendBroadCast(mMusicInfo);

        notificationBuilder.setContentText("Download Failed");
        notificationBuilder.setTicker(mMusicInfo.getTitle() + " download failed");
        notificationBuilder.setProgress(100, mMusicInfo.getProgress(), false);
        updateNotification(Integer.parseInt(mMusicInfo.getId()));
    }

    private void updateNotification(int notificationId) {
        notificationManager.notify(notificationId + 1000, notificationBuilder.build());
    }

    private void sendBroadCast(MusicInfo info) {
        Log.w("DownloadCallBack", "sendBroadCast()\n Title -> " + info.getTitle() + "\nStatus -> " + info.getStatusText());
        Intent intent = new Intent();
        intent.setAction(ACTION_DOWNLOAD_BROAD_CAST);
        intent.putExtra(DownloadService.EXTRA_INFO, info);
        broadcastManager.sendBroadcast(intent);
    }
}
