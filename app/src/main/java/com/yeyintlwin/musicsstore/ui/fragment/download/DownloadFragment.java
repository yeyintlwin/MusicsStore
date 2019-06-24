package com.yeyintlwin.musicsstore.ui.fragment.download;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.activity.MainActivity;
import com.yeyintlwin.musicsstore.ui.fragment.AboutsFragment;
import com.yeyintlwin.musicsstore.ui.fragment.HomeFragment;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.download.adapter.TabsItemPagerAdapter;

public class DownloadFragment extends BaseFragment {
    private static DownloadFragment downloadFragment;
    private TabsItemPagerAdapter adapter;
    private TabLayoutSetupCallback tabLayoutSetupCallback;

    public DownloadFragment() {
    }

    public static DownloadFragment getInstance() {
        if (downloadFragment == null) downloadFragment = new DownloadFragment();
        return downloadFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TabsItemPagerAdapter(getChildFragmentManager());


        adapter.addFragment(HomeFragment.getInstance(), "A");
        adapter.addFragment(AboutsFragment.getInstance(), "B");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);
        final ViewPager viewPager = view.findViewById(R.id.download_pager);

        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = tabLayoutSetupCallback.getTabLayout();
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_download);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_download);
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#88ffffff"), PorterDuff.Mode.SRC_IN);
            tabLayout.setOnTabSelectedListener(
                    new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            super.onTabSelected(tab);
                            if (tab == null) return;
                            Drawable icon = tab.getIcon();
                            if (icon == null) return;
                            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            super.onTabUnselected(tab);
                            tab.getIcon().setColorFilter(Color.parseColor("#88ffffff"), PorterDuff.Mode.SRC_IN);
                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            super.onTabReselected(tab);
                        }
                    }

            );
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            tabLayoutSetupCallback = (TabLayoutSetupCallback) context;
        } else {
            throw new ClassCastException("ClDownloadedFragment :ClassCastException @ TabLayoutSetupCallback");
        }
    }

    public interface TabLayoutSetupCallback {
        TabLayout getTabLayout();
    }
}
