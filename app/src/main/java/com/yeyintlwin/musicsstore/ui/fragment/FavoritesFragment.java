package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("Favorites");
        return textView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
