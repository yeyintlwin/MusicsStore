package com.yeyintlwin.musicsstore.ui.fragment.category;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.CategoryAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder.listener.OnCategoryItemClickListener;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;
import com.yeyintlwin.musicsstore.ui.fragment.category.listener.OnFragmentNextStepListener;
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

public class CategoriesFragment extends BaseFragment {

    private final int SHOW_RECYCLER = 0;
    private final int SHOW_EMPTY = 1;
    private final int SHOW_LOADING = 2;
    private final int SHOW_OFFLINE = 3;
    private int action;
    private OnFragmentNextStepListener mListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View loadingView;
    private View offlineView;
    private View emptyView;
    private ImageView emptyLogo;
    private TextView emptyText;
    private TextView offlineText;
    private TextView loadingText;
    private DotsTextView loadingDotsText;
    private List<CategoryInfo> categoryInfoList;
    private CategoryAdapter categoryAdapter;
    private String searchQuery = "";
    private RequestQueue requestQueue;

    public CategoriesFragment() {
    }

    public static CategoriesFragment getInstance() {
        return new CategoriesFragment();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.cat_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.cat_swipeRefresh);

        loadingView = view.findViewById(R.id.cat_layout_loading);
        loadingText = loadingView.findViewById(R.id.layout_loadingTextView);
        loadingDotsText = loadingView.findViewById(R.id.loading_dotsTextView);
        offlineView = view.findViewById(R.id.cat_layout_offline);
        offlineText = offlineView.findViewById(R.id.layout_offlineTextView);
        emptyView = view.findViewById(R.id.cat_layout_empty);
        emptyText = emptyView.findViewById(R.id.layout_emptyTextView);
        emptyLogo = emptyView.findViewById(R.id.layout_emptyImageView);

    }

    private void setFontStand() {
        if (Utils.isUnicode()) return;
        loadingText.setText(Utils.fontStand(loadingText.getText().toString()));
        offlineText.setText(Utils.fontStand(offlineText.getText().toString()));
        emptyText.setText(Utils.fontStand(emptyText.getText().toString()));
    }

    private void viewControl(int viewType) {
        recyclerView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        offlineView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        loadingDotsText.stop();
        switch (viewType) {
            case SHOW_RECYCLER:
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case SHOW_EMPTY:
                emptyView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);

                switch (action) {
                    case MainActivity.ACTION_ARTIST:
                        emptyLogo.setImageResource(R.drawable.ic_empty_artist);
                        emptyText.setText(R.string.empty_artist);
                        break;
                    case MainActivity.ACTION_GENRE:
                        emptyLogo.setImageResource(R.drawable.ic_empty_genre);
                        emptyText.setText(R.string.empty_genre);
                        break;
                    case MainActivity.ACTION_ALBUM:
                        emptyLogo.setImageResource(R.drawable.ic_empty_album);
                        emptyText.setText(R.string.empty_album);
                        break;
                    case MainActivity.ACTION_COUNTRY:
                        emptyLogo.setImageResource(R.drawable.ic_empty_country);
                        emptyText.setText(R.string.empty_country);
                        break;
                }
                emptyText.setText(Utils.fontStand(emptyText.getText().toString()));

                break;
            case SHOW_LOADING:
                loadingView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
                loadingDotsText.start();
                break;
            case SHOW_OFFLINE:
                offlineView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        categoryInfoList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) action = bundle.getInt(MainActivity.BUNDLE_ACTION_CATEGORY);


        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        init(view);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryItemClickListener(new OnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClick(CategoryInfo categoryInfo) {
                if (mListener != null) mListener.onNextStep(action, categoryInfo);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Utils.getColum(getActivity(), 230), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopRequest();
                loadData(searchQuery);
            }
        });

        setFontStand();
        loadData(searchQuery);
    }

    private void stopRequest() {
        requestQueue.stop();
        categoryInfoList.clear();
        categoryAdapter.setData(categoryInfoList);
        categoryAdapter.notifyDataSetChanged();
    }

    public void setOnFragmentNextStepListener(OnFragmentNextStepListener listener) {
        mListener = listener;
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
                searchQuery = Utils.isUnicode() ? s : Rabbit.zg2uni(s);
                stopRequest();
                loadData(searchQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        if (!searchQuery.equals("")) {
            if (!menuItem.isActionViewExpanded()) menuItem.expandActionView();
            searchView.setQuery(searchQuery, true);
            searchView.clearFocus();
        }

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchQuery = "";
                stopRequest();
                loadData(searchQuery);
                return true;
            }
        });

    }

    private void loadData(final String query) {
        viewControl(SHOW_LOADING);
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.API_V2 + "categories.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = Utils.fontStand(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category");

                    if (jsonArray.length() == 0) {
                        viewControl(SHOW_EMPTY);
                        return;
                    } else {
                        viewControl(SHOW_RECYCLER);
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        CategoryInfo categoryInfo = new CategoryInfo();
                        categoryInfo.setId(object.getString("id"));
                        categoryInfo.setName(object.getString("name"));
                        categoryInfoList.add(categoryInfo);
                    }
                    categoryAdapter.setData(categoryInfoList);
                    categoryAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    viewControl(SHOW_OFFLINE);
                    e.printStackTrace();
                    Log.w("Fuck", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewControl(SHOW_OFFLINE);
                Log.w("Volley Err", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("type", getTargetType(action));
                hashMap.put("name", query);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();

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

}
