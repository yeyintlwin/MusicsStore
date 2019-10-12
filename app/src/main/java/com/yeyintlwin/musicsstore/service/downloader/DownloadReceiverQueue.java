package com.yeyintlwin.musicsstore.service.downloader;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yeyintlwin.musicsstore.service.downloader.listener.OnRemoveListener;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.viewholder.QueueItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

public class DownloadReceiverQueue extends BroadcastReceiver {
    @SuppressLint("StaticFieldLeak")
    private static DownloadReceiverQueue receiverQueue;
    private RecyclerView mRecyclerVieiw;
    private OnRemoveListener mListener;

    DownloadReceiverQueue() {
    }

    public static DownloadReceiverQueue getInstance() {
        if (receiverQueue == null) receiverQueue = new DownloadReceiverQueue();
        return receiverQueue;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("DownloadReceiverMusic", "onReceive()");
        String action = intent.getAction();
        if (action != null && action.equals(DownloadCallBack.ACTION_DOWNLOAD_BROAD_CAST)) {
            MusicInfo musicInfo = (MusicInfo) intent.getSerializableExtra(DownloadService.EXTRA_INFO);
            if (musicInfo != null) {
                QueueItemViewHolder queueItemViewHolder = getViewHolderItem(musicInfo.getId());
                if (queueItemViewHolder != null) {
                    if (queueItemViewHolder.progressBar.getVisibility() == View.INVISIBLE)
                        queueItemViewHolder.progressBar.setVisibility(View.VISIBLE);
                    queueItemViewHolder.progressBar.setIndeterminate(false);
                    switch (musicInfo.getStatus()) {
                        case MusicInfo.STATUS_NOT_DOWNLOAD:
                            queueItemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            queueItemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            queueItemViewHolder.progressBar.setProgress(musicInfo.getProgress());
                            queueItemViewHolder.textViewPersize.setText(musicInfo.getPerSize());
                            queueItemViewHolder.progressBar.setVisibility(View.INVISIBLE);
                            queueItemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                            break;
                        case MusicInfo.STATUS_CONNECTING:
                            queueItemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            queueItemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            queueItemViewHolder.progressBar.setIndeterminate(true);
                            queueItemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                            break;
                        case MusicInfo.STATUS_DOWNLOADING:
                            queueItemViewHolder.textViewPersize.setText(musicInfo.getPerSize());
                            queueItemViewHolder.progressBar.setProgress(musicInfo.getProgress());
                            queueItemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            queueItemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            queueItemViewHolder.textViewStatus.setTextColor(Color.GREEN);
                            break;
                        case MusicInfo.STATUS_PAUSED:
                            queueItemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            queueItemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            queueItemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                            break;
                        case MusicInfo.STATUS_DOWNLOAD_ERROR:
                            queueItemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            queueItemViewHolder.textViewPersize.setText(musicInfo.getPerSize());
                            queueItemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            queueItemViewHolder.progressBar.setVisibility(View.INVISIBLE);
                            queueItemViewHolder.textViewStatus.setTextColor(Color.RED);
                            break;
                        case MusicInfo.STATUS_COMPLETE:
                            if (mListener != null) {
                                //noinspection deprecation
                                mListener.onRemove(musicInfo.getId());
                            }
                            break;
                    }
                }

            }
        }
    }

    public void setOnRemoveListener(OnRemoveListener listener) {
        this.mListener = listener;
    }

    public DownloadReceiverQueue setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerVieiw = recyclerView;
        return receiverQueue;
    }

    private QueueItemViewHolder getViewHolderItem(String id) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerVieiw.getLayoutManager();
        assert linearLayoutManager != null;
        int fi = linearLayoutManager.findFirstVisibleItemPosition();
        int li = linearLayoutManager.findLastVisibleItemPosition();

        for (int i = fi; i <= li; i++) {
            QueueItemViewHolder holder = null;
            try {
                holder = (QueueItemViewHolder) mRecyclerVieiw.findViewHolderForLayoutPosition(i);
            } catch (ClassCastException e) {
                continue;
            }
            if (holder == null) continue;
            //noinspection ConstantConditions
            if (!(holder instanceof QueueItemViewHolder)) continue;
            if (holder.queueInfo.getId().equals(id)) return holder;
        }
        return null;
    }
}
