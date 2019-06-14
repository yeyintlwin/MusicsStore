package com.yeyintlwin.musicsstore.ui.fragment;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yeyintlwin.musicsstore.R;

public class SettingsFragment extends PreferenceFragment {
    private static SettingsFragment fragment;
    public static PreferenceFragment getInstance(){
        if(fragment==null)
            fragment=new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
