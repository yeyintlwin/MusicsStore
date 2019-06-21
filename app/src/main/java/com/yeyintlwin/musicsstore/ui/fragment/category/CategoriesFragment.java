package com.yeyintlwin.musicsstore.ui.fragment.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;

public class CategoriesFragment extends BaseFragment {

    private int action;
    private OnFragmentNextStepListener mListener;

    public CategoriesFragment() {
    }

    public static CategoriesFragment getInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) action = bundle.getInt(MainActivity.BUNDLE_ACTION_CATEGORY);

        Button button = new Button(getContext());
        button.setText("Categories " + action);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onNextStep(action);
            }
        });
        return button;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setOnFragmentNextStepListener(OnFragmentNextStepListener listener) {
        mListener = listener;
    }
}
