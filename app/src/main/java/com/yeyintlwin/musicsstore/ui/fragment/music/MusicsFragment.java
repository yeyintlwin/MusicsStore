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
import android.webkit.URLUtil;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.MainController;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.MusicAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.utils.Rabbit;
import com.yeyintlwin.musicsstore.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.tajchert.waitingdots.DotsTextView;

public class MusicsFragment extends BaseFragment {
    private final int SHOW_RECYCLER = 0;
    private final int SHOW_EMPTY = 1;
    private final int SHOW_OFFLINE = 2;
    private final int SHOW_LOADING = 3;

    private View emptyView;
    private View offlineView;
    private View loadingView;
    private TextView emptyText;
    private TextView offlineText;
    private TextView loadingText;
    private DotsTextView loadingDotsText;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<MusicInfo> musicInfoList;
    private MusicAdapter musicAdapter;
    private Activity activity;
    private boolean canRemove = false;
    private boolean isLoading = false;
    private boolean requestFromLoadMore = false;
    private int startPoint = 0;
    private String searchQuery = "";
    private int action;
    private String selectedId;
    private int numRows;
    private RequestQueue requestQueue;

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
            action = bundle.getInt(MainActivity.BUNDLE_ACTION_MUSIC);

            if (action != MainActivity.ACTION_MUSICS) {
                selectedId = bundle.getString(MainActivity.BUNDLE_ACTION_SELECTED_ID);
            }
        }

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musics, container, false);
        recyclerView = view.findViewById(R.id.music_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.music_swipeRefresh);

        emptyView = view.findViewById(R.id.music_empty);
        offlineView = view.findViewById(R.id.music_offline);
        loadingView = view.findViewById(R.id.music_loading);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        offlineText = offlineView.findViewById(R.id.layout_offlineTextView);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);
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
                Utils.getColumn(activity, 300), 1));

        musicInfoList = new ArrayList<>();
        musicAdapter = new MusicAdapter();
        recyclerView.setAdapter(musicAdapter);

        setFontStand();
        viewControl(SHOW_LOADING);
        loadData("", 0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewControl(SHOW_LOADING);
                stopRequest();
                loadData(searchQuery, startPoint);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//Listen to scroll down action.
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)
                            recyclerView.getLayoutManager();
                    assert layoutManager != null;
                    int lastItemPosition = Utils.max(layoutManager
                            .findLastVisibleItemPositions(new int[layoutManager.getSpanCount()])) + 1;
                    int itemsCount = layoutManager.getItemCount();
                    if (!isLoading && (itemsCount == lastItemPosition) && (numRows > itemsCount)) {

                        Log.w("load_more", "load now");
                        insertLoadMore();
                        requestFromLoadMore = true;
                        if (startPoint < 0) startPoint = 0;
                        startPoint = startPoint + Integer.parseInt(
                                MainController.getString("loadMoreLimit", "20"));
                        loadData(searchQuery, startPoint);
                    }

                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                Utils.getColumn(activity, 300), 1));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(
                getActivity()).getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewControl(SHOW_LOADING);
                if (!Utils.isUnicode()) s = Rabbit.zg2uni(s);
                searchQuery = s;
                stopRequest();
                loadData(searchQuery, startPoint);
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
                viewControl(SHOW_LOADING);
                searchQuery = "";
                stopRequest();
                loadData(searchQuery, startPoint);
                return true;
            }
        });
    }

    private void setFontStand() {
        if (Utils.isUnicode()) return;
        emptyText.setText(Utils.fontStand(emptyText.getText().toString()));
        offlineText.setText(Utils.fontStand(offlineText.getText().toString()));
        loadingText.setText(Utils.fontStand(loadingText.getText().toString()));
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
                break;
            case SHOW_OFFLINE:
                swipeRefreshLayout.setRefreshing(false);
                offlineView.setVisibility(View.VISIBLE);
                break;
            case SHOW_LOADING:
                swipeRefreshLayout.setRefreshing(true);
                loadingView.setVisibility(View.VISIBLE);
                loadingDotsText.start();
                break;
        }
    }

    private void insertLoadMore() {
        if (!canRemove) {
            canRemove = true;
            musicInfoList.add(null);
            musicAdapter.setData(musicInfoList);
            musicAdapter.notifyItemInserted(musicInfoList.size() - 1);
        }
    }

    private void removeLoadMore() {
        isLoading = false;
        if (canRemove) {
            canRemove = false;
            musicInfoList.remove(musicInfoList.size() - 1);
            musicAdapter.setData(musicInfoList);
            musicAdapter.notifyItemRemoved(musicInfoList.size());
        }
    }

    private void stopRequest() {
        requestQueue.stop();
        musicInfoList.clear();
        musicAdapter.setData(musicInfoList);
        musicAdapter.notifyDataSetChanged();
        startPoint = 0;
    }

    private boolean isFromCategory(int action) {
        return action != MainActivity.ACTION_MUSICS;
    }


    private String getTargetType(int action) {
        switch (action) {
            case MainActivity.ACTION_ARTIST:
                return "0";
            case MainActivity.ACTION_GENRE:
                return "1";
            case MainActivity.ACTION_ALBUM:
                return "2";
            case MainActivity.ACTION_COUNTRY:
                return "3";
            default:
                return "";
        }
    }

    private void loadData(final String query, final int firstItem) {
        isLoading = true;
        requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.API_V2 + (isFromCategory(action) ? "searcher.php" : "musics.php"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        removeLoadMore();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            numRows = Integer.parseInt(jsonObject.getString("num_rows"));
                            String objectString = jsonObject.getString("musics");
                            objectString = Utils.isUnicode() ? objectString : Rabbit.uni2zg(objectString);
                            Log.w("response", objectString);
                            JSONArray jsonArray = new JSONArray(objectString);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String link = object.getString("link");
                                if (!URLUtil.isValidUrl(link)) {
                                    numRows--;
                                    continue;
                                }
                                MusicInfo musicInfo = new MusicInfo();
                                musicInfo.setId(object.getString("id"));
                                musicInfo.setTitle(object.getString("title"));
                                musicInfo.setArtist(object.getString("artist"));
                                musicInfo.setGenre(object.getString("genre"));
                                musicInfo.setAlbum(object.getString("album"));
                                musicInfo.setCountry(object.getString("country"));
                                musicInfo.setCover(object.getString("cover"));
                                musicInfo.setLink(link);
                                String counter = object.getString("counter");
                                musicInfo.setCounter(counter.equals("null") ? "0" : counter);
                                musicInfoList.add(musicInfo);
                            }
                            musicAdapter.setData(musicInfoList);
                            musicAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            if (requestFromLoadMore)
                                startPoint = firstItem - Integer.parseInt(
                                        MainController.getString("loadMoreLimit", "20"));
                            e.printStackTrace();
                        }
                        viewControl(Objects.requireNonNull(recyclerView.getLayoutManager())
                                .getItemCount() == 0 ? SHOW_EMPTY : SHOW_RECYCLER);
                        requestFromLoadMore = false;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                removeLoadMore();
                if (requestFromLoadMore) {
                    startPoint = firstItem - Integer.parseInt(
                            MainController.getString("loadMoreLimit", "20"));
                    viewControl(Objects.requireNonNull(recyclerView.getLayoutManager())
                            .getItemCount() == 0 ? SHOW_EMPTY : SHOW_RECYCLER);
                } else {
                    viewControl(SHOW_OFFLINE);
                }
                requestFromLoadMore = false;
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("from", Integer.toString(firstItem));
                hashMap.put("limit", MainController.getString("loadMoreLimit", "20"));
                hashMap.put("title", query);

                if (isFromCategory(action)) {
                    hashMap.put("id", selectedId);
                    hashMap.put("type", getTargetType(action));
                }
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

}
