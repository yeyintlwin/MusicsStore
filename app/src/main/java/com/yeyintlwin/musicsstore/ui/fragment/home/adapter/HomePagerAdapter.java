package com.yeyintlwin.musicsstore.ui.fragment.home.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomePagerInfo;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends PagerAdapter {

    private List<HomePagerInfo> infos;
    private Activity activity;

    public HomePagerAdapter(Activity activity) {
        this.activity = activity;
        infos = new ArrayList<>();
    }

    public void setData(List<HomePagerInfo> data) {
        infos.clear();
        infos = data;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_home_pager, container, false);

        ImageView imageView = view.findViewById(R.id.exclusiveImageView);
        Picasso.get().load(infos.get(position).getExclusiveCover()).centerCrop().fit().into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.printf();
                Log.wtf("wtf", infos.get(position).getId());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
