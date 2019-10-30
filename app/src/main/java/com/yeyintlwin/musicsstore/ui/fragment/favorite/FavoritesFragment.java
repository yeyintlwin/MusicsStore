package com.yeyintlwin.musicsstore.ui.fragment.favorite;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.adapter.FavoriteAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.entity.FavoriteInfo;
import com.yeyintlwin.musicsstore.utils.Rabbit;
import com.yeyintlwin.musicsstore.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.tajchert.waitingdots.DotsTextView;

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
    private RequestQueue requestQueue;

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopRequest();
                loadData();
            }
        });
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
                Utils.fontStand(emptyText.getText().toString());
                break;
            case SHOW_OFFLINE:
                swipeRefreshLayout.setRefreshing(false);
                offlineView.setVisibility(View.VISIBLE);
                Utils.fontStand(offlineText.getText().toString());
                break;
            case SHOW_LOADING:
                swipeRefreshLayout.setRefreshing(true);
                loadingView.setVisibility(View.VISIBLE);
                loadingDotsText.start();
                Utils.fontStand(loadingText.getText().toString());
                break;
        }
    }

    private void stopRequest() {
        requestQueue.stop();
        infos.clear();
        adapter.setData(infos);
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        viewControl(SHOW_LOADING);
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.API_V2
                + "favorites.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = Utils.isUnicode() ? response : Rabbit.uni2zg(response);
                Log.w("f_response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("musics"));
                    if (jsonArray.length() == 0) {
                        viewControl(SHOW_EMPTY);
                        return;
                    } else {
                        viewControl(SHOW_RECYCLER);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String link = object.getString("link");
                        if (!URLUtil.isValidUrl(link)) continue;
                        FavoriteInfo info = new FavoriteInfo();
                        info.setId(object.getString("id"));
                        info.setTitle(object.getString("title"));
                        info.setArtist(object.getString("artist"));
                        info.setGenre(object.getString("genre"));
                        info.setAlbum(object.getString("album"));
                        info.setCountry(object.getString("country"));
                        info.setCover(object.getString("cover"));
                        info.setLink(link);
                        String counter = object.getString("counter");
                        info.setCounter(counter.equals("null") ? "0" : counter);
                        infos.add(info);
                    }
                    adapter.setData(infos);
                    adapter.notifyDataSetChanged();
                    //viewControl(recyclerView.getLayoutManager().getItemCount() == 0 ? SHOW_EMPTY : SHOW_RECYCLER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewControl(SHOW_OFFLINE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("limit", MainController.Companion.getString("favoritesLimit", "20"));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();

    }
}
