package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    private static HomeFragment homeFragment;

    public HomeFragment() {

    }

    public static HomeFragment getInstance() {
        if (homeFragment == null) homeFragment = new HomeFragment();
        return homeFragment;
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
