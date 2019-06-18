package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class MusicsFragment extends BaseFragment {
    private static MusicsFragment musicsFragment;

    public MusicsFragment() {
    }

    public static MusicsFragment getInstance() {
        if (musicsFragment == null) musicsFragment = new MusicsFragment();
        return musicsFragment;
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
