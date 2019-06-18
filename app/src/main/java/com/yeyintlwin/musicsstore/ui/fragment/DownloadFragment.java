package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class DownloadFragment extends BaseFragment {
    private static DownloadFragment downloadFragment;

    public DownloadFragment() {
    }

    public static DownloadFragment getInstance() {
        if (downloadFragment == null) downloadFragment = new DownloadFragment();
        return downloadFragment;
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
