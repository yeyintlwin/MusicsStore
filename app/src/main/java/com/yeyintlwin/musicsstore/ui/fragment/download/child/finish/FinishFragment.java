package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.FinishAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity.FinishInfo;
import com.yeyintlwin.musicsstore.utils.Utils;
import pl.tajchert.waitingdots.DotsTextView;

import java.util.ArrayList;
import java.util.List;

public class FinishFragment extends BaseFragment {

    private static FinishFragment finishFragment;

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

    private FinishAdapter adapter;
    private List<FinishInfo> infos;

    public FinishFragment() {
    }

    public static FinishFragment getInstance() {
        if (finishFragment == null) finishFragment = new FinishFragment();
        return finishFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_finish, container, false);

        swipeRefreshLayout = view.findViewById(R.id.fragment_downloaded_childandroid_support_v4_widget_SwipeRefreshLayout);
        recyclerView = view.findViewById(R.id.fragment_downloaded_childandroid_support_v7_widget_RecyclerView);
        emptyView = view.findViewById(R.id.fragment_downloaded_childinclude_empty);
        loadingView = view.findViewById(R.id.fragment_downloaded_childinclude_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);

        infos = new ArrayList<>();
        adapter = new FinishAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utils.getColumn(getActivity(), 260)));

        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utils.getColumn(getActivity(), 260)));
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
                //Utils.fontstands(emptyText);
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
            FinishInfo info = new FinishInfo();
            info.setTitle("Title");
            info.setArtist("Artist");
            info.setGenre("Genre");
            info.setAlbum("Album");
            info.setPath("path");

            infos.add(info);
        }
        adapter.setData(infos);
        adapter.notifyDataSetChanged();
    }
}
