package com.yeyintlwin.musicsstore.ui.fragment.music;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.MusicAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicsFragment extends BaseFragment {
    private static MusicsFragment musicsFragment;
    private int action;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<MusicInfo> musicInfoList;
    private MusicAdapter musicAdapter;

    public MusicsFragment() {
    }

    public static MusicsFragment getInstance() {
        if (musicsFragment == null) musicsFragment = new MusicsFragment();
        return musicsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            action = bundle.getInt(MainActivity.BUNDLE_ACTION_MUSIC);
            String selectedId = bundle.getString(MainActivity.BUNDLE_ACTION_SELECTED_ID);

            Log.w("msfsid", selectedId);
        }

        Log.w("MusicFragment", action + "");

        View view = inflater.inflate(R.layout.fragment_musics, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.music_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.music_swipeRefresh);
    }

    private void test() {

        for (int i = 0; i <= 10; i++) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setTitle("Over The Horizon");
            musicInfo.setArtist("Samsung Music Band");
            musicInfo.setGenre("Blue");
            musicInfo.setAlbum("Samsung");
            musicInfo.setCountry("English");
            musicInfoList.add(musicInfo);
        }
        musicAdapter.setData(musicInfoList);
        musicAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Utils.getColum(getActivity(), 300), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        musicInfoList = new ArrayList<>();
        musicAdapter = new MusicAdapter();
        recyclerView.setAdapter(musicAdapter);

        test();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
    }
}
