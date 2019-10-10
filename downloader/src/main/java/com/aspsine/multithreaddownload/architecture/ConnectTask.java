package com.aspsine.multithreaddownload.architecture;

import com.aspsine.multithreaddownload.DownloadException;

/**
 * Created by Aspsine on 2015/10/29.
 */
public interface ConnectTask extends Runnable {

    void pause();

    void cancel();

    boolean isConnecting();

    boolean isConnected();

    boolean isPaused();

    boolean isCanceled();

    boolean isFailed();

    @Override
    void run();

    interface OnConnectListener {
        void onConnecting();

        void onConnected(long time, long length, boolean isAcceptRanges);

        void onConnectPaused();

        void onConnectCanceled();

        void onConnectFailed(DownloadException de);
    }
}
