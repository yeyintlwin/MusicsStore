package com.yeyintlwin.musicsstore.ui.fragment.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.MusicAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicsFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<MusicInfo> musicInfoList;
    private MusicAdapter musicAdapter;

    private Activity activity;

    public MusicsFragment() {
    }

    public static MusicsFragment getInstance() {
        return new MusicsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        activity = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            int action = bundle.getInt(MainActivity.BUNDLE_ACTION_MUSIC);

            if (action != MainActivity.ACTION_MUSICS) {
                String selectedId = bundle.getString(MainActivity.BUNDLE_ACTION_SELECTED_ID);

                //TODO here is important place.
                Toast.makeText(getContext(), "From MusicFragment\naction: " + getSomething(action)
                        + "\nid: " + selectedId, Toast.LENGTH_LONG).show();
            }
        }

    }

    private String getSomething(int action) {
        switch (action) {
            case MainActivity.ACTION_ARTIST:
                return "Artist";
            case MainActivity.ACTION_GENRE:
                return "Genre";
            case MainActivity.ACTION_ALBUM:
                return "Album";
            case MainActivity.ACTION_COUNTRY:
                return "Country";
            default:
                return "";
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musics, container, false);
        recyclerView = view.findViewById(R.id.music_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.music_swipeRefresh);
        return view;
    }

    private void loadData() {

        for (int i = 0; i <= 10; i++) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setTitle("Title (" + i + ")");
            musicInfo.setArtist("Artist");
            musicInfo.setGenre("Genre");
            musicInfo.setAlbum("Album");

            musicInfo.setCountry("Country");
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Utils.getColumn(activity, 300), 1));

        musicInfoList = new ArrayList<>();
        musicAdapter = new MusicAdapter();
        recyclerView.setAdapter(musicAdapter);

        loadData();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//Listen to scroll down action.
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    assert layoutManager != null;
                    int lastItemPosition = Utils.max(layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()])) + 1;
                    int itemsCount = layoutManager.getItemCount();
                    if (/*!isLoading &&*/ (itemsCount == lastItemPosition) /*&& (numRows > itemsCount)*/) {

                        Log.w("load_more", "load now");
                        /*insertLoadMore();
                        requestFromLoadmore = true;
                        if (startPoint < 0)startPoint = 0;
                        startPoint = startPoint + Integer.parseInt(MainController.getString("loadMoreLimit", "20"));
                        webSocket(searchQuery, startPoint);
*/
                    }

                }
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Utils.getColumn(activity, 300), 1));
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

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
    }
}
