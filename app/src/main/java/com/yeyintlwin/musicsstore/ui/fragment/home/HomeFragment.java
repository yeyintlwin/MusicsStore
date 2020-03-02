package com.yeyintlwin.musicsstore.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.base.BaseFragment;
import com.yeyintlwin.musicsstore.ui.fragment.home.adapter.HomePagerAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.home.adapter.HomeRecyclerViewAdapter;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomePagerInfo;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomeRecyclerViewInfo;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomeSectionListDataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment {
    @SuppressLint("StaticFieldLeak")
    private static HomeFragment homeFragment;
    boolean isCanChange = true;

    ImageView[] dots;
    int currentPage = 0;
    SwipeRefreshLayout swipeRefresh;
    private ViewPager viewPager;
    private LinearLayout slighterDotsPanel;
    private RecyclerView recyclerView;


    public HomeFragment() {

    }

    public static HomeFragment getInstance() {
        if (homeFragment == null) homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.home_viewPager);
        slighterDotsPanel = view.findViewById(R.id.slighterDots);

        recyclerView = view.findViewById(R.id.home_recyclerView);
        swipeRefresh = view.findViewById(R.id.home_swipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setItemViewCacheSize(12);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });
        test();
        test2();
    }

    private void test2() {
        HomeRecyclerViewAdapter recyclerViewAdapter = new HomeRecyclerViewAdapter(getContext());
        List<HomeRecyclerViewInfo> sectionInfos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            HomeRecyclerViewInfo sectionInfo = new HomeRecyclerViewInfo();
            sectionInfo.setId(i + "");
            sectionInfo.setSectionName("Section");

            List<HomeSectionListDataInfo> itemInfos = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                HomeSectionListDataInfo itemInfo = new HomeSectionListDataInfo();
                itemInfo.setId(j + "");
                itemInfo.setName("Hello World");
                //  itemInfo.setImageUrl("http://example.com/image/demo.png");
                itemInfo.setImageUrl("http://192.168.43.139/android_api_v2.0/upload/exclusive_cover/pic" + (j + 1) + ".jpg");

                itemInfos.add(itemInfo);
            }
            sectionInfo.setSectionListData(itemInfos);

            sectionInfos.add(sectionInfo);
        }

        recyclerViewAdapter.setData(sectionInfos);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


    @SuppressWarnings("ConstantConditions")
    @SuppressLint("ClickableViewAccessibility")
    private void test() {

        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getActivity());

        List<HomePagerInfo> infos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            HomePagerInfo info = new HomePagerInfo();
            info.setId(Integer.toString(i));
            info.setExclusiveCover("http://192.168.43.139/android_api_v2.0/upload/exclusive_cover/pic" + (i + 1) + ".jpg");
            infos.add(info);
        }

        pagerAdapter.setData(infos);
        viewPager.setAdapter(pagerAdapter);


        //Dots indicators.
        final int dotsCount = pagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            slighterDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        //When we touch on ViewPager area. we don't wanna auto change their items.
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isCanChange = event.getAction() != MotionEvent.ACTION_MOVE && event.getAction() != MotionEvent.ACTION_DOWN;
                return false;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                }
                currentPage = position;
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isCanChange) {
                    if (currentPage == dotsCount) {
                        currentPage = 0;
                    }
                    try {
                        viewPager.setCurrentItem(currentPage++, true);

                    } catch (NullPointerException e) {
                        //Log.e("HomeFragment.Runnable", e.toString());
                    }
                }
            }
        };

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 500, 3000);
    }

}
