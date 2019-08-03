package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.QueueAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;
import pl.tajchert.waitingdots.DotsTextView;

import java.util.ArrayList;
import java.util.List;

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

    public QueueFragment() {
    }

    public static QueueFragment getInstance() {
        if (queueFragment == null) queueFragment = new QueueFragment();
        return queueFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download_queue, container, false);
        swipeRefreshLayout = view.findViewById(R.id.queue_swipeRefresh);
        recyclerView = view.findViewById(R.id.queue_recyclerView);
        emptyView = view.findViewById(R.id.queue_empty);
        loadingView = view.findViewById(R.id.queue_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);

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

        loadData();
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
                // Utils.fontstands(emptyText);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case SHOW_LOADING:
                loadingView.setVisibility(View.VISIBLE);
                loadingDotsText.start();
                //Utils.fontstands(loadingText);
                swipeRefreshLayout.setRefreshing(true);
                break;
        }
    }

    private void loadData() {
        viewControl(SHOW_RECYCLER);
        for (int i = 0; i < 10; i++) {
            QueueInfo info = new QueueInfo();
            info.setId(Integer.toString(i));
            info.setTitle("Hello World");
            infos.add(info);
        }
        adapter.setData(infos);
        adapter.notifyDataSetChanged();


    }
}
