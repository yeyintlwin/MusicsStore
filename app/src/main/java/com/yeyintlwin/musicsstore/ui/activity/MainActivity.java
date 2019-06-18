package com.yeyintlwin.musicsstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MenuItem menuItemCarrier;
    private boolean isMenuItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (isMenuItemSelected)
                    switch (menuItemCarrier.getItemId()) {
                        case R.id.nav_home:
                        case R.id.nav_musics:
                        case R.id.nav_artist:
                        case R.id.nav_genre:
                        case R.id.nav_album:
                        case R.id.nav_country:
                        case R.id.nav_favorite:
                            Log.w("Clicked: ", menuItemCarrier.getTitle().toString());
                            break;
                        case R.id.nav_download:
                            break;
                        case R.id.nav_settings:
                            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                            overridePendingTransition(0, 0);
                            finish();
                            break;
                        case R.id.nav_about:
                            break;
                    }
                isMenuItemSelected = false;

            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuItemCarrier = item;
        isMenuItemSelected = true;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
