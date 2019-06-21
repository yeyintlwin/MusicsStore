package com.yeyintlwin.musicsstore.ui.fragment.download;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class DownloadFragment extends BaseFragment {
    private static DownloadFragment downloadFragment;
    private TabLayoutSetupCallback tabLayoutSetupCallback;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("Download");
        return textView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            tabLayoutSetupCallback = (TabLayoutSetupCallback) context;
        } else {
            throw new ClassCastException("ClDownloadedFragment :ClassCastException @ TabLayoutSetupCallback");
        }
    }

    public interface TabLayoutSetupCallback {
        TabLayout getTebLayout();
    }
}
