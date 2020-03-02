package com.yeyintlwin.musicsstore.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.base.BaseActivity;
import com.yeyintlwin.musicsstore.ui.fragment.AboutsFragment;
import com.yeyintlwin.musicsstore.ui.fragment.category.CategoriesFragment;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;
import com.yeyintlwin.musicsstore.ui.fragment.category.listener.OnFragmentNextStepListener;
import com.yeyintlwin.musicsstore.ui.fragment.download.DownloadFragment;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.FavoritesFragment;
import com.yeyintlwin.musicsstore.ui.fragment.home.HomeFragment;
import com.yeyintlwin.musicsstore.ui.fragment.music.MusicsFragment;
import com.yeyintlwin.musicsstore.ui.fragment.offline.OfflineFragment;
import com.yeyintlwin.musicsstore.ui.fragment.offline.listener.OnOfflineRetryListener;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.util.Objects;
import java.util.Stack;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DownloadFragment.TabLayoutSetupCallback {

    public static final String BUNDLE_ACTION_CATEGORY = "bundle_category";
    public static final String BUNDLE_ACTION_MUSIC = "bundle_music";
    public static final String BUNDLE_ACTION_SELECTED_ID = "bundle_selected_id";

    public static final int ACTION_MUSICS = R.id.nav_musics;
    public static final int ACTION_ARTIST = R.id.nav_artist;
    public static final int ACTION_GENRE = R.id.nav_genre;
    public static final int ACTION_ALBUM = R.id.nav_album;
    public static final int ACTION_COUNTRY = R.id.nav_country;

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1010;
    private static final String TAG = "MainActivity";

    private Stack<Fragment> fragmentStack;
    private MenuItem menuItemCarrier;
    private boolean isMenuItemSelected = false;
    private long back_press;
    private View stacksLayout;
    private RelativeLayout firstStack;
    private RelativeLayout secondStack;
    private TabLayout tabLayout;
    private View.OnClickListener sbSecondStackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fragmentStack.size() == 2) {//Max stacks size
                fragmentBackFromSecondStack();
            }
        }
    };
    private TextView firstStackText;
    private TextView secondStackText;
    private NavigationView navigationView;

    private void init() {
        stacksLayout = findViewById(R.id.stacks_view);
        firstStack = stacksLayout.findViewById(R.id.first_layout);
        secondStack = stacksLayout.findViewById(R.id.second_layout);
        firstStackText = stacksLayout.findViewById(R.id.first_text);
        secondStackText = stacksLayout.findViewById(R.id.second_text);
        firstStack.setOnClickListener(sbSecondStackClickListener);

        tabLayout = findViewById(R.id.main_tabs);

        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Storage permission is granted.");

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return;
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        } else {
            Log.v(TAG, "Permission has already granted");
        }

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);


                int resId;
                try {
                    resId = menuItemCarrier.getItemId();
                } catch (NullPointerException e) {
                    Log.w("log", e.toString());
                    return;
                }

                if (isMenuItemSelected)
                    switch (resId) {
                        case R.id.nav_home:
                        case R.id.nav_musics:
                        case R.id.nav_artist:
                        case R.id.nav_genre:
                        case R.id.nav_album:
                        case R.id.nav_country:
                        case R.id.nav_favorite:
                            netSections(resId);
                            Log.w("Clicked: ", menuItemCarrier.getTitle().toString());
                            break;
                        case R.id.nav_download:
                            fragmentReplace(DownloadFragment.getInstance());
                            tabLayout.setVisibility(View.VISIBLE);
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
                            fragmentReplace(AboutsFragment.getInstance());
                            break;
                    }
                isMenuItemSelected = false;

            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        netSections(R.id.nav_home);// Home as default.
        fragmentStack = new Stack<>();

        setFontStand();
    }

    private void netSections(final int resId) {

        if (Utils.isConnected(getBaseContext())) //Check connection.
        {
            switch (resId) {
                case R.id.nav_home:
                    fragmentReplace(HomeFragment.getInstance());
                    break;
                case R.id.nav_musics:
                    MusicsFragment musicsFragment = MusicsFragment.getInstance();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(BUNDLE_ACTION_MUSIC, ACTION_MUSICS);
                    musicsFragment.setArguments(bundle1);
                    fragmentReplace(musicsFragment);
                    break;
                case R.id.nav_artist:
                case R.id.nav_genre:
                case R.id.nav_album:
                case R.id.nav_country: {
                    //sbShowHomeLayout();
                    CategoriesFragment categoriesFragment = CategoriesFragment.getInstance();
                    final Bundle bundle = new Bundle();
                    bundle.putInt(BUNDLE_ACTION_CATEGORY, resId);
                    categoriesFragment.setArguments(bundle);
                    fragmentStoreFirstStack(categoriesFragment);
                    categoriesFragment.setOnFragmentNextStepListener(new OnFragmentNextStepListener() {
                        @Override
                        public void onNextStep(int action, CategoryInfo categoryInfo) {
                            MusicsFragment musicsFragment = MusicsFragment.getInstance();
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt(BUNDLE_ACTION_MUSIC, action);
                            bundle1.putString(BUNDLE_ACTION_SELECTED_ID, categoryInfo.getId());
                            musicsFragment.setArguments(bundle1);
                            fragmentStoreSecondStack(musicsFragment, categoryInfo.getName());
                        }
                    });
                    break;
                }
                case R.id.nav_favorite:
                    stacksLayout.setVisibility(View.GONE);
                    fragmentReplace(FavoritesFragment.getInstance());
                    break;
            }
        } else {
            fragmentReplace(OfflineFragment.getInstance()
                    .setCurrentSectionId(resId)
                    .setOfflineRetryListener(new OnOfflineRetryListener() {
                        @Override
                        public void onRetry(int sessionId) {
                            netSections(sessionId);
                            Log.w("retry", resId + "");
                        }
                    }));
        }
    }

    private void fragmentReplace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit();
    }

    private void fragmentStoreFirstStack(CategoriesFragment categoriesFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, categoriesFragment);
        fragmentStack.add(categoriesFragment);
        fragmentTransaction.commit();

        sbShowFirstStack(Objects.requireNonNull(getSupportActionBar()).getTitle());
    }

    private void fragmentStoreSecondStack(MusicsFragment musicsFragment, String secondStackName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame, musicsFragment);
        fragmentStack.lastElement().onPause();
        fragmentTransaction.hide(fragmentStack.lastElement());
        fragmentStack.push(musicsFragment);
        fragmentTransaction.commit();

        sbShowSecondStack(secondStackName);
    }

    private void fragmentBackFromSecondStack() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentStack.lastElement().onPause();
        fragmentTransaction.remove(fragmentStack.pop());
        fragmentStack.lastElement().onResume();
        fragmentTransaction.show(fragmentStack.lastElement());
        fragmentTransaction.commit();

        sbClearSecondStack();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (fragmentStack.size() == 2) {//Max stacks size
            fragmentBackFromSecondStack();
            return;
        }
        if (back_press + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Press Back Bottom again to exist!", Toast.LENGTH_SHORT).show();
        back_press = System.currentTimeMillis();

    }

    private void sbClearAll(int itemId) {
        stacksLayout.setVisibility(isInCategory(itemId) ? View.VISIBLE : View.GONE);
        firstStack.setVisibility(View.GONE);
        secondStack.setVisibility(View.GONE);

    }

    private boolean isInCategory(int id) {
        return id == R.id.nav_artist ||
                id == R.id.nav_genre ||
                id == R.id.nav_album ||
                id == R.id.nav_country;

    }

    private void sbClearSecondStack() {
        secondStack.setVisibility(View.GONE);
    }

    /* private void sbShowHomeLayout() {
         stacksLayout.setVisibility(View.VISIBLE);
     }
 */
    private void sbShowFirstStack(CharSequence categoryName) {
        firstStack.setVisibility(View.VISIBLE);
        firstStackText.setText(categoryName);
    }

    private void sbShowSecondStack(String name) {
        secondStack.setVisibility(View.VISIBLE);
        secondStackText.setText(name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuItemCarrier = item;
        isMenuItemSelected = true;
        Objects.requireNonNull(getSupportActionBar()).setTitle(item.getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragmentStack.clear();//clear stored stack


        sbClearAll(item.getItemId());

        tabLayout.setVisibility(View.GONE);
        return true;
    }

    @Override
    public TabLayout getTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);
        return tabLayout;
    }

    private void setFontStand() {
        if (Utils.isUnicode()) return;
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setTitle(Utils.fontStand(menu.getItem(i).getTitle().toString()));
        Menu submenu = menu.getItem(7).getSubMenu();
        for (int i = 0; i < submenu.size(); i++)
            submenu.getItem(i).setTitle(Utils.fontStand(submenu.getItem(i).getTitle().toString()));
    }
}
