package com.yeyintlwin.musicsstore.ui.widget.themesdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.view.LayoutInflater;
import android.view.View;

import com.yeyintlwin.musicsstore.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ThemesDialog {

    private static final String THEME = "theme";
    private static final int DEFAULT_THEME = R.style.SweetPink;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static OnThemesDialogItemClickListener mListener;
    @SuppressLint("StaticFieldLeak")
    private static Activity mActivity;
    private final int[] ids = {
            R.id.dialogCircleButton1,
            R.id.dialogCircleButton2,
            R.id.dialogCircleButton3,
            R.id.dialogCircleButton4,
            R.id.dialogCircleButton5,
            R.id.dialogCircleButton6,
            R.id.dialogCircleButton7,
            R.id.dialogCircleButton8,
            R.id.dialogCircleButton9};

    private final int[] themes = {
            R.style.SkyBlue,
            R.style.Green,
            R.style.SweetPink,
            R.style.PurpleGrapes,
            R.style.FashionBlue,
            R.style.AppleGreen,
            R.style.MintBlue,
            R.style.FieryOrange,
            R.style.LakeTeal};
    private ThemesBottomSheetDialog bottomSheetDialog;
    private CircleButton.OnClickListener cbOnClickListener = new CircleButton.OnClickListener() {

        @Override
        public void onClick(View p1) {
            bottomSheetDialog.cancel();
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == p1.getId()) {
                    putInt(themes[i]);
                    break;
                }
            }

            if (mListener != null) mListener.onThemesDialogItemClick(mActivity);
        }
    };
    private BottomSheetBehavior bottomSheetBehavior;

    @SuppressLint("CommitPrefEdits")
    private ThemesDialog(Activity activity) {
        mActivity = activity;
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        setup();
    }

    public static ThemesDialog getInstance(Activity activity) {
        return new ThemesDialog(activity);
    }

    public static void setOnThemesDialogItemClickListener(OnThemesDialogItemClickListener listener) {
        mListener = listener;
    }

    public static int getCurrentTheme(Context context) {
        return getInt(context);
    }

    private static void putInt(Integer value) {
        editor.putInt(ThemesDialog.THEME, value).apply();
    }

    @SuppressLint("CommitPrefEdits")
    private static int getInt(Context context) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor = preferences.edit();
        }
        return preferences.getInt(ThemesDialog.THEME, ThemesDialog.DEFAULT_THEME);
    }

    private void setup() {
        mActivity.getLayoutInflater();
        @SuppressLint("InflateParams")
        View bottomSheetView = LayoutInflater.from(mActivity).inflate(R.layout.themes_dialog, null);
        bottomSheetDialog = new ThemesBottomSheetDialog(mActivity);
        bottomSheetDialog.setOwnerActivity(mActivity);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setPeekHeight(350);

        ArrayList<CircleButton> buttons = new ArrayList<>();
        for (int id : ids) {
            CircleButton cb = bottomSheetView.findViewById(id);
            buttons.add(cb);
            cb.setOnClickListener(cbOnClickListener);
        }

        for (int i = 0; i < ids.length; i++) {
            if (getInt(mActivity) == themes[i]) {
                buttons.get(i).setImageResource(R.drawable.ic_action_tick);
                break;
            }
        }
    }

    public void show() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }
}
