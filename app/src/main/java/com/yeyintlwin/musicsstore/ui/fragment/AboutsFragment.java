package com.yeyintlwin.musicsstore.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.widget.acknowledgement.LicensesDialog;

import java.util.Objects;

public class AboutsFragment extends BaseFragment implements View.OnClickListener {

    private static AboutsFragment aboutsFragment;

    public AboutsFragment() {

    }

    public static AboutsFragment getInstance() {
        if (aboutsFragment == null) aboutsFragment = new AboutsFragment();
        return aboutsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abouts, container, false);
        ((TextView) view.findViewById(R.id.abouts_app_name))
                .setTypeface(Typeface.createFromAsset(
                        Objects.requireNonNull(getContext()).getAssets(),
                        "fonts/Lobster.ttf"));
        ((TextView) view.findViewById(R.id.abouts_developer_name))
                .setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "fonts/GreatVibes.ttf"));
        view.findViewById(R.id.abouts_licenses).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        LicensesDialog.getInstance(getContext()).show();
    }
}
