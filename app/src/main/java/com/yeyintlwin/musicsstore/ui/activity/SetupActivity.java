package com.yeyintlwin.musicsstore.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.MainController;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.base.BaseActivity;

import at.markushi.ui.CircleButton;

public class SetupActivity extends BaseActivity implements OnClickListener {
    private LinearLayout clickUnicode;
    private LinearLayout clickZawgyi;
    private ImageView checkUnicode;
    private ImageView checkZawgyi;
    private CircleButton setupComplete;
    private TextView insistText;

    private void init() {
        clickUnicode = findViewById(R.id.activity_setupClickArea1);
        clickZawgyi = findViewById(R.id.activity_setupClickArea2);
        checkUnicode = findViewById(R.id.activity_setupImageView1);
        checkZawgyi = findViewById(R.id.activity_setupImageView2);
        setupComplete = findViewById(R.id.activity_setupat_markushi_ui_CircleButton);
        insistText = findViewById(R.id.activity_setupTextView3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        init();
        insistText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zawgyi-One2014.ttf"));
        clickUnicode.setOnClickListener(this);
        clickZawgyi.setOnClickListener(this);
        setupComplete.setOnClickListener(this);
        MainController.putBoolean(Constants.IS_UNICODE, true);
    }

    @Override
    public void onClick(View p1) {
        switch (p1.getId()) {
            case R.id.activity_setupClickArea1:
                checkZawgyi.setImageBitmap(null);
                checkUnicode.setImageResource(R.drawable.ic_action_tick_setup);
                MainController.putBoolean(Constants.IS_UNICODE, true);
                break;
            case R.id.activity_setupClickArea2:
                checkUnicode.setImageBitmap(null);
                checkZawgyi.setImageResource(R.drawable.ic_action_tick_setup);
                MainController.putBoolean(Constants.IS_UNICODE, false);
                break;
            case R.id.activity_setupat_markushi_ui_CircleButton:
                MainController.putBoolean("isOneTime", true);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Disable Back Key.
    }
}