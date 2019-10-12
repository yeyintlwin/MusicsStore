package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.multithreaddownload.DownloadInfo;
import com.aspsine.multithreaddownload.DownloadManager;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.database.downloadqueue.DownloadDatabaseManager;
import com.yeyintlwin.musicsstore.database.downloadqueue.entity.DownloadMusicInfo;
import com.yeyintlwin.musicsstore.service.downloader.DownloadCallBack;
import com.yeyintlwin.musicsstore.service.downloader.DownloadReceiverQueue;
import com.yeyintlwin.musicsstore.service.downloader.DownloadServiceManager;
import com.yeyintlwin.musicsstore.service.downloader.listener.OnRemoveListener;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.QueueAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.listener.OnDownloadBtnClickListener;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.tajchert.waitingdots.DotsTextView;

public class QueueFragment extends BaseFragment {
    @SuppressLint("StaticFieldLeak")
    private static QueueFragment queueFragment;

    private final int SHOW_RECYCLER = 0;
    private final int SHOW_EMPTY = 1;
    private final int SHOW_LOADING = 2;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private View loadingView;
    private TextView emptyText;
    private TextView loadingText;
    private DotsTextView loadingDotsText;

    private QueueAdapter adapter;
    private List<QueueInfo> infos;
    private DownloadReceiverQueue downloadReceiverQueue;

    public QueueFragment() {
    }

    public static QueueFragment getInstance() {
        if (queueFragment == null) queueFragment = new QueueFragment();
        return queueFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download_queue, container, false);
        swipeRefreshLayout = view.findViewById(R.id.queue_swipeRefresh);
        recyclerView = view.findViewById(R.id.queue_recyclerView);
        emptyView = view.findViewById(R.id.queue_empty);
        loadingView = view.findViewById(R.id.queue_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);

        emptyText.setText(Utils.fontStand(emptyText.getText().toString()));
        loadingText.setText(Utils.fontStand(loadingText.getText().toString()));

        infos = new ArrayList<>();
        adapter = new QueueAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewControl(SHOW_LOADING);
        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewControl(SHOW_LOADING);
                infos.clear();
                loadData();
            }
        });

        adapter.setOnDownloadBtnClickListener(new OnDownloadBtnClickListener() {
            @Override
            public void onDownloadAndResume(QueueInfo queueInfo) {
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setId(queueInfo.getId());
                musicInfo.setTitle(queueInfo.getTitle());
                musicInfo.setArtist(queueInfo.getArtist());
                musicInfo.setGenre(queueInfo.getGenre());
                musicInfo.setAlbum(queueInfo.getAlbum());
                musicInfo.setCover(queueInfo.getCover());
                musicInfo.setLink(queueInfo.getLink());
                DownloadServiceManager.getInstance(getContext()).startDownload(musicInfo);
            }

            @Override
            public void onPause(QueueInfo queueInfo) {
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setId(queueInfo.getId());
                musicInfo.setTitle(queueInfo.getTitle());
                musicInfo.setArtist(queueInfo.getArtist());
                musicInfo.setGenre(queueInfo.getGenre());
                musicInfo.setAlbum(queueInfo.getAlbum());
                musicInfo.setCover(queueInfo.getCover());
                musicInfo.setLink(queueInfo.getLink());
                DownloadServiceManager.getInstance(getContext()).pauseDownload(musicInfo);
            }
        });
    }

    private void viewControl(int viewType) {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        loadingDotsText.stop();
        switch (viewType) {
            case SHOW_RECYCLER:
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case SHOW_EMPTY:
                emptyView.setVisibility(View.VISIBLE);
                Utils.fontStand(emptyText.getText().toString());
                swipeRefreshLayout.setRefreshing(false);
                break;
            case SHOW_LOADING:
                loadingView.setVisibility(View.VISIBLE);
                loadingDotsText.start();
                Utils.fontStand(loadingText.getText().toString());
                swipeRefreshLayout.setRefreshing(true);
                break;
        }
    }

    private void loadData() {

        List<DownloadMusicInfo> downloadMusicInfos = DownloadDatabaseManager.getInstance(getContext()).getAllMusic();
        if (downloadMusicInfos.size() == 0) {
            viewControl(SHOW_EMPTY);
            return;
        }

        viewControl(SHOW_RECYCLER);
        for (DownloadMusicInfo downloadMusicInfo : downloadMusicInfos) {
            QueueInfo info = new QueueInfo();
            info.setId(downloadMusicInfo.getId());
            info.setTitle(downloadMusicInfo.getTitle());
            info.setArtist(downloadMusicInfo.getArtist());
            info.setGenre(downloadMusicInfo.getGenre());
            info.setAlbum(downloadMusicInfo.getAlbum());
            info.setCover(downloadMusicInfo.getCover());
            info.setLink(downloadMusicInfo.getLink());
            DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(downloadMusicInfo.getLink());
            if (downloadInfo != null) {
                info.setProgress(downloadInfo.getProgress());
                info.setPerSize(Utils.getDownloadPerSize(
                        downloadInfo.getFinished(),
                        downloadInfo.getLength()));
            }
            infos.add(info);
        }
        adapter.setData(infos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadReceiverQueue = DownloadReceiverQueue.getInstance().setRecyclerView(recyclerView);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadCallBack.ACTION_DOWNLOAD_BROAD_CAST);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                .registerReceiver(downloadReceiverQueue, intentFilter);
        downloadReceiverQueue.setOnRemoveListener(new OnRemoveListener() {
            @Override
            public void onRemove(String id) {
                try {
                    int position = getItemPositionById(id);
                    Log.v("QueueFragment del", "position -> " + position);
                    infos.remove(position);
                    adapter.setData(infos);
                    adapter.notifyItemRemoved(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private int getItemPositionById(String id) throws Exception {
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getId().equals(id)) return i;
        }
        throw new Exception("Position not found.");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (downloadReceiverQueue != null)
            LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext()))
                    .unregisterReceiver(downloadReceiverQueue);
    }
}
