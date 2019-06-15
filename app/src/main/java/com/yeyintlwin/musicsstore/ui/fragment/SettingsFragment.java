package com.yeyintlwin.musicsstore.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.yeyintlwin.musicsstore.BuildConfig;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.SettingsActivity;
import com.yeyintlwin.musicsstore.ui.widget.themesdialog.OnThemesDialogItemClickListener;
import com.yeyintlwin.musicsstore.ui.widget.themesdialog.ThemesDialog;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static PreferenceFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findPreference("themes").setOnPreferenceClickListener(this);
        findPreference("dev_email").setOnPreferenceClickListener(this);
        findPreference("dev_facebook").setOnPreferenceClickListener(this);
        findPreference("app_facebook").setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "themes":
                ThemesDialog.getInstance(getActivity()).show();
                ThemesDialog.setOnThemesDialogItemClickListener(new OnThemesDialogItemClickListener() {
                    @Override
                    public void onThemesDialogItemClick(Activity activity) {
                        Intent intent = new Intent(activity, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        activity.overridePendingTransition(0, 0);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                    }
                });
                return true;
            case "dev_email":
                Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{getString(R.string.developer_email)});
                intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name) + "_" + BuildConfig.VERSION_NAME);
                intent.setType("plain/text");
                startActivity(intent);
                return true;
            case "dev_facebook":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.developer_facebook))));
                return true;
            case "app_facebook":
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.app_facebook))));
                return true;
            default:
                return false;
        }
    }
}
