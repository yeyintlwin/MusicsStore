package com.yeyintlwin.musicsstore.service.downloader;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder.MusicItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Utils;

public class DownloadReceiverMusic extends BroadcastReceiver {
    @SuppressLint("StaticFieldLeak")
    private static DownloadReceiverMusic receiverMusic = null;
    private RecyclerView mRecyclerView;

    DownloadReceiverMusic() {
    }

    public static DownloadReceiverMusic getInstance() {
        // if (receiverMusic == null)
        receiverMusic = new DownloadReceiverMusic();
        return receiverMusic;
    }

    public DownloadReceiverMusic setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        return receiverMusic;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.w("DownloadReceiverMusic", "onReceive()");
        String action = intent.getAction();
        if (action != null && action.equals(DownloadCallBack.ACTION_DOWNLOAD_BROAD_CAST)) {
            MusicInfo musicInfo = (MusicInfo) intent.getSerializableExtra(DownloadService.EXTRA_INFO);
            if (musicInfo != null) {
                MusicItemViewHolder musicItemViewHolder = getViewHolderItem(musicInfo.getId());
                if (musicItemViewHolder != null) {
                    if (musicItemViewHolder.progressLayout.getVisibility() == View.GONE)
                        musicItemViewHolder.progressLayout.setVisibility(View.VISIBLE);
                    musicItemViewHolder.progress.setIndeterminate(false);
                    switch (musicInfo.getStatus()) {
                        case MusicInfo.STATUS_NOT_DOWNLOAD:
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.progress.setProgress(musicInfo.getProgress());
                            musicItemViewHolder.perSize.setText(musicInfo.getPerSize());
                            musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                            break;
                        case MusicInfo.STATUS_CONNECTING:
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.progress.setIndeterminate(true);
                            break;
                        case MusicInfo.STATUS_DOWNLOADING:
                            musicItemViewHolder.perSize.setText(musicInfo.getPerSize());
                            musicItemViewHolder.progress.setProgress(musicInfo.getProgress());
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            break;
                        case MusicInfo.STATUS_PAUSED:
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                            break;
                        case MusicInfo.STATUS_DOWNLOAD_ERROR:
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.perSize.setText(musicInfo.getPerSize());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                            break;
                        case MusicInfo.STATUS_COMPLETE:
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.perSize.setText(musicInfo.getPerSize());
                            musicItemViewHolder.progress.setProgress(musicInfo.getProgress());
                            musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                            break;

                    }
                }
            }
        }


    }

    private MusicItemViewHolder getViewHolderItem(String id) {
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
        assert layoutManager != null;
        int[] firstItemsList = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
        int[] lastItemsList = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
        int firstItemPosition = Utils.min(firstItemsList);
        int lastItemPosition = Utils.max(lastItemsList);
        for (int i = firstItemPosition; i <= lastItemPosition; i++) {
            MusicItemViewHolder holder;
            try {
                holder = (MusicItemViewHolder) mRecyclerView.findViewHolderForLayoutPosition(i);
            } catch (ClassCastException e) {
                //Fuck this isn't MusicItemViewHolder, that is ProgressItemViewHolder
                continue;
            }
            if (holder == null) continue;

            //noinspection ConstantConditions
            if (!(holder instanceof MusicItemViewHolder)) continue;
            if (holder.info.getId().equals(id)) return holder;
        }
        return null;
    }
}
