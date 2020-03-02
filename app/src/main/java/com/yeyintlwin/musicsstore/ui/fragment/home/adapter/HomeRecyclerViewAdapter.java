package com.yeyintlwin.musicsstore.ui.fragment.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomeRecyclerViewInfo;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomeSectionListDataInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<HomeRecyclerViewInfo> infos;
    private Context mContext;

    public HomeRecyclerViewAdapter(Context context) {
        infos = new ArrayList<>();
        mContext = context;
    }

    public void setData(List<HomeRecyclerViewInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_recycler_view, viewGroup, false);
        return new ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ItemRowHolder holder = (ItemRowHolder) viewHolder;
        HomeRecyclerViewInfo info = infos.get(i);

        holder.textView.setText(info.getSectionName());

        HomeSectionListDataAdapter sectionAdapter = new HomeSectionListDataAdapter();

        // holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(sectionAdapter);
        //holder.recyclerView.setNestedScrollingEnabled(true);
        holder.recyclerView.setItemViewCacheSize(12);


        List<HomeSectionListDataInfo> hslInfo = new ArrayList<>();

        for (HomeSectionListDataInfo sinf : info.getSectionListData()) {
            hslInfo.add(sinf);
        }
        sectionAdapter.setData(hslInfo);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView textView;

        public ItemRowHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.home_section_recycler_view);
            textView = itemView.findViewById(R.id.home_section_name);
        }
    }

}
