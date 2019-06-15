package com.yeyintlwin.musicsstore.ui.widget.themesdialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.view.LayoutInflater;
import android.view.View;

import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.MainController;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.SettingsActivity;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ThemesDialog {

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

    private Activity activity;
    private ThemesBottomSheetDialog bottomSheetDialog;

    private CircleButton.OnClickListener cbOnClickListener = new CircleButton.OnClickListener() {

        @Override
        public void onClick(View p1) {
            bottomSheetDialog.cancel();
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == p1.getId()) {
                    MainController.putInt(Constants.THEME, themes[i]);
                    break;
                }
            }
            changeActivity(activity, new Intent(activity, SettingsActivity.class));
        }
    };
    private BottomSheetBehavior bottomSheetBehavior;

    private ThemesDialog(Activity activity) {
        this.activity = activity;
        setup();
    }

    public static ThemesDialog getInstance(Activity activity) {
        return new ThemesDialog(activity);
    }

    private void setup() {
        activity.getLayoutInflater();
        @SuppressLint("InflateParams")
        View bottomSheetView = LayoutInflater.from(activity).inflate(R.layout.themes_dialog, null);
        bottomSheetDialog = new ThemesBottomSheetDialog(activity);
        bottomSheetDialog.setOwnerActivity(activity);
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
            if (MainController.getInt(Constants.THEME, Constants.DEFAULT_THEME) == themes[i]) {
                buttons.get(i).setImageResource(R.drawable.ic_action_tick);
                break;
            }
        }
    }

    private void changeActivity(Activity activity, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }

    public void show() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }
}
