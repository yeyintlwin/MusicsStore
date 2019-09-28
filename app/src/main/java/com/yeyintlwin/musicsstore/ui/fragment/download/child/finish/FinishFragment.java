package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
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
import android.widget.Toast;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.FinishAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity.FinishInfo;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.listener.OnDeleteSongListener;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.tajchert.waitingdots.DotsTextView;

public class FinishFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_finish, container, false);

        swipeRefreshLayout = view.findViewById(R.id.finish_swipeRefresh);
        recyclerView = view.findViewById(R.id.finish_recyclerView);
        emptyView = view.findViewById(R.id.finish_empty);
        loadingView = view.findViewById(R.id.finish_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);

        emptyText.setText(Utils.fontStand(emptyText.getText().toString()));
        loadingText.setText(Utils.fontStand(loadingText.getText().toString()));

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utils.getColumn(
                Objects.requireNonNull(getActivity()), 260)));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        adapter.setOnDeleteSongListener(new OnDeleteSongListener() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void onDeleteSong(FinishInfo info) {
                Toast.makeText(getContext(), "name: " + info.getTitle() + "\npath: "
                        + info.getPath(), Toast.LENGTH_SHORT).show();
                new File(info.getPath()).delete();
                infos.remove(info);
                adapter.setData(infos);
                adapter.notifyDataSetChanged();
                viewControl(Objects.requireNonNull(recyclerView.getLayoutManager())
                        .getItemCount() == 0 ? SHOW_EMPTY : SHOW_RECYCLER);
            }
        });

        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utils.getColumn(
                Objects.requireNonNull(getActivity()), 260)));
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
        viewControl(SHOW_LOADING);

        infos.clear();

        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();

        ArrayList<String> filePaths;
        try {
            filePaths = Utils.getMp3Files(Objects.requireNonNull(getContext()));
            if (filePaths.isEmpty()) {
                viewControl(SHOW_EMPTY);
                return;
            } else {
                viewControl(SHOW_RECYCLER);
            }
            for (String absolutePath : filePaths) {

                metadataRetriever.setDataSource(absolutePath);
                String title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String genre = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                String album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

                if (title == null) {
                    File file = new File(absolutePath);
                    title = file.getName().substring(0, file.getName().length() - 4);
                }

                if (artist == null) artist = "<Unknown>";
                if (genre == null) genre = "<Unknown>";
                if (album == null) album = "<Unknown>";

                FinishInfo info = new FinishInfo();
                info.setTitle(title);
                info.setArtist(artist);
                info.setGenre(genre);
                info.setAlbum(album);
                info.setPath(absolutePath);

                infos.add(info);


            }
        } catch (Exception e) {
            e.printStackTrace();
            viewControl(SHOW_EMPTY);
        }

        metadataRetriever.release();
        adapter.setData(infos);
        adapter.notifyDataSetChanged();
        viewControl(Objects.requireNonNull(
                recyclerView.getLayoutManager()).getItemCount() == 0 ? SHOW_EMPTY : SHOW_RECYCLER);

    }

}
