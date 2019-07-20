package com.yeyintlwin.musicsstore.ui.fragment.download.child;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class FinishFragment extends BaseFragment {

    private static FinishFragment finishFragment;

    public FinishFragment() {
    }

    public static FinishFragment getInstance() {
        if (finishFragment == null) finishFragment = new FinishFragment();
        return finishFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_finish, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
