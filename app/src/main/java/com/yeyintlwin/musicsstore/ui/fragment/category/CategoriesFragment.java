package com.yeyintlwin.musicsstore.ui.fragment.category;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.widget.Toast;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.CategoryAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;
import com.yeyintlwin.musicsstore.ui.fragment.category.listener.OnFragmentNextStepListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesFragment extends BaseFragment {

    private int action;
    private OnFragmentNextStepListener mListener;

    private RecyclerView recyclerView;
    private List<CategoryInfo> categoryInfoList;
    private CategoryAdapter categoryAdapter;

    public CategoriesFragment() {
    }

    public static CategoriesFragment getInstance() {
        return new CategoriesFragment();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.cat_recyclerView);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) action = bundle.getInt(MainActivity.BUNDLE_ACTION_CATEGORY);

        //Button button = new Button(getContext());
        //button.setText("Categories " + action);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onNextStep(action);
            }
        });*/
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        init(view);
        recyclerView.setAdapter(categoryAdapter);
        return view;//button;
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

        test();
    }

    private void test() {
        for (int i = 0; i <= 20; i++) {
            CategoryInfo categoryInfo = new CategoryInfo();
            categoryInfo.setId(i + "");
            categoryInfo.setName("Blah " + i);
            categoryInfoList.add(categoryInfo);
        }
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

        /*MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });*/
    }
}
