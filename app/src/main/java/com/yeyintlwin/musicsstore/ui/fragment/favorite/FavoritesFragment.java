package com.yeyintlwin.musicsstore.ui.fragment.favorite;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.adapter.FavoriteAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.entity.FavoriteInfo;
import com.yeyintlwin.musicsstore.utils.Utils;
import pl.tajchert.waitingdots.DotsTextView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends BaseFragment {
    @SuppressLint("StaticFieldLeak")
    private static FavoritesFragment favoritesFragment;

    private final int SHOW_RECYCLER = 0;
    private final int SHOW_EMPTY = 1;
    private final int SHOW_OFFLINE = 2;
    private final int SHOW_LOADING = 3;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private View offlineView;
    private View loadingView;
    private TextView emptyText;
    private TextView offlineText;
    private TextView loadingText;
    private DotsTextView loadingDotsText;

    private List<FavoriteInfo> infos;
    private FavoriteAdapter adapter;


    public FavoritesFragment() {
    }

    public static FavoritesFragment getInstance() {
        if (favoritesFragment == null) favoritesFragment = new FavoritesFragment();
        return favoritesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        swipeRefreshLayout = view.findViewById(R.id.fragment_favorite_swipeRefresh);
        recyclerView = view.findViewById(R.id.fragment_favorite_recyclerView);
        emptyView = view.findViewById(R.id.fragment_favorite_empty);
        offlineView = view.findViewById(R.id.fragment_favorite_offline);
        loadingView = view.findViewById(R.id.fragment_favorite_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        offlineText = offlineView.findViewById(R.id.layout_offlineTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);

        infos = new ArrayList<>();
        adapter = new FavoriteAdapter();
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                Utils.getColumn(getActivity(), 300), 1));
        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                Utils.getColumn(getActivity(), 300), 1));
    }

    private void viewControl(int viewType) {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        offlineView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        loadingDotsText.stop();
        switch (viewType) {
            case SHOW_RECYCLER:
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case SHOW_EMPTY:
                swipeRefreshLayout.setRefreshing(false);
                emptyView.setVisibility(View.VISIBLE);
                //Utils.fontstands(emptyText);
                break;
            case SHOW_OFFLINE:
                swipeRefreshLayout.setRefreshing(false);
                offlineView.setVisibility(View.VISIBLE);
                //Utils.fontstands(offlineText);
                break;
            case SHOW_LOADING:
                swipeRefreshLayout.setRefreshing(true);
                loadingView.setVisibility(View.VISIBLE);
                loadingDotsText.start();
                //Utils.fontstands(loadingText);
                break;
        }
    }

    private void loadData() {
        viewControl(SHOW_RECYCLER);
        for (int i = 0; i < 10; i++) {
            FavoriteInfo info = new FavoriteInfo();
            info.setId(Integer.toString(i));
            info.setTitle("Title");
            info.setArtist("Artist");
            info.setGenre("Genre");
            info.setAlbum("Album");
            info.setCountry("Country");
            infos.add(info);
        }
        adapter.setData(infos);
        adapter.notifyDataSetChanged();
    }
}
