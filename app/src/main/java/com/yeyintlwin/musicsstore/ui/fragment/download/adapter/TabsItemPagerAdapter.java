package com.yeyintlwin.musicsstore.ui.fragment.download.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsItemPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentsList;
    private List<String> tabNamesList;

    public TabsItemPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentsList = new ArrayList<>();
        this.tabNamesList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String tabName) {
        fragmentsList.add(fragment);
        tabNamesList.add(tabName);
    }

    @Override
    public int getCount() {
        return tabNamesList.size();
    }

    @Override
    public Fragment getItem(int p1) {
        return fragmentsList.get(p1);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNamesList.get(position);
    }

}