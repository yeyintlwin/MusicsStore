package com.yeyintlwin.musicsstore.ui.activity.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yeyintlwin.musicsstore.Constants;
import com.yeyintlwin.musicsstore.MainController;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainController.getInt(Constants.THEME,Constants.DEFAULT_THEME));
    }
}
