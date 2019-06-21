package com.yeyintlwin.musicsstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class MusicsFragment extends BaseFragment {
    private static MusicsFragment musicsFragment;
    private int action;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            action = bundle.getInt(MainActivity.BUNDLE_ACTION_MUSIC);
        }
        TextView textView = new TextView(getContext());
        textView.setText("Musics" + action);
        return textView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
