package com.yeyintlwin.musicsstore.ui.fragment.offline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.offline.listener.OnOfflineRetryListener;
import com.yeyintlwin.musicsstore.utils.Utils;

public class OfflineFragment extends BaseFragment implements View.OnClickListener {
    private static OfflineFragment offlineFragment;
    private OnOfflineRetryListener mListener;
    private int mSectionId;

    public OfflineFragment() {
    }

    public static OfflineFragment getInstance() {
        if (offlineFragment == null)
            offlineFragment = new OfflineFragment();
        return offlineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        TextView offlineText = view.findViewById(R.id.fragment_offlineTextView);
        offlineText.setText(Utils.fontStand(offlineText.getText().toString()));

        (view.findViewById(R.id.fragment_offlineat_markushi_ui_CircleButton))
                .setOnClickListener(this);
        return view;
    }

    public OfflineFragment setOfflineRetryListener(OnOfflineRetryListener listener) {
        mListener = listener;
        return offlineFragment;
    }

    public OfflineFragment setCurrentSectionId(int sectionId) {
        mSectionId = sectionId;
        return offlineFragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_offlineat_markushi_ui_CircleButton && mListener != null)
            mListener.onRetry(mSectionId);
    }
}