package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class FavoritesFragment extends BaseFragment {
    private static FavoritesFragment favoritesFragment;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
