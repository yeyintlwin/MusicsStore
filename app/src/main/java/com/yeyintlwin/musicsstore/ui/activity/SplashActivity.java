package com.yeyintlwin.musicsstore.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.BuildConfig;
import com.yeyintlwin.musicsstore.MainController;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.base.BaseActivity;

@SuppressLint("Registered")
public class SplashActivity extends BaseActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((TextView) findViewById(R.id.splash_title)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Lobster-Regular.ttf"));
        ((TextView) findViewById(R.id.splash_version)).setText("v" + BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class aClass = MainController.getBoolean("isOneTime", false) ? MainActivity.class : SetupActivity.class;
                Intent intent = new Intent(SplashActivity.this, aClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        //Disable Back Key.
    }
}
