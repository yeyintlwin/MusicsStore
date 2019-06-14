package com.yeyintlwin.musicsstore.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.MainController;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.SettingsActivity;

import java.util.ArrayList;
import java.util.Objects;

import at.markushi.ui.CircleButton;

public class ThemesDialog extends BottomSheetDialog {

    private View bottomSheetView;
    private ThemesDialog themesDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    /*private final int[] ids={
            R.id.dialogCircleButton1,
            R.id.dialogCircleButton2,
            R.id.dialogCircleButton3,
            R.id.dialogCircleButton4,
            R.id.dialogCircleButton5,
            R.id.dialogCircleButton6,
            R.id.dialogCircleButton7,
            R.id.dialogCircleButton8,
            R.id.dialogCircleButton9};

    private final int[] themes={
            R.style.SkyBlue,
            R.style.Green,
            R.style.SweetPink,
            R.style.PurpleGrapes,
            R.style.FashionBlue,
            R.style.AppleGreen,
            R.style.MintBlue,
            R.style.FieryOrange,
            R.style.LakeTeal};

    private ArrayList<CircleButton> buttons;
*/
    private Context context;
    //private Activity activity;
    public ThemesDialog(@NonNull Context mContext) {
        super(mContext);
        this.context=mContext;
    }

    private static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(Objects.requireNonNull(getOwnerActivity()));
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        if (getWindow() != null)
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }


    private void setup(Activity activity){
        bottomSheetView = activity.getLayoutInflater().from(getContext()).inflate(R.layout.themes_dialog, null);
        themesDialog = new ThemesDialog(context);
        themesDialog.setOwnerActivity(activity);
        themesDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View)bottomSheetView.getParent());
        bottomSheetBehavior.setPeekHeight(350);

      /*  buttons = new ArrayList<>();
        for (int id:ids)
        {
            CircleButton cb=bottomSheetView.findViewById(id);
            buttons.add(cb);
            //cb.setOnClickListener(cbOnClickListener);
        }*/

        /*for (int i=0;i < ids.length;i++)
        {
            if (MainController.getInt(Constants.THEME, Constants.DEFAULT_THEME) == themes[i])
            {
                buttons.get(i).setImageResource(R.drawable.ic_action_tick);
                break;
            }
        }*/
    }

    /*public void changeActivity(Context context,Activity activity, Intent intent)
    {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.overridePendingTransition(0, 0);
       context.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }*/

    /*private CircleButton.OnClickListener cbOnClickListener=new CircleButton.OnClickListener(){

        @Override
        public void onClick(View p1)
        {
            themesDialog.cancel();
            for (int i=0;i < ids.length;i++)
            {
                if (ids[i] == p1.getId())
                {
                    MainController.putInt(Constants.THEME, themes[i]);
                    break;
                }
            }
            changeActivity(context,activity, new Intent(activity, SettingsActivity.class));
        }
    };*/

    public  void show(Activity activity){
        setup(activity);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        themesDialog.show();
    }

}
