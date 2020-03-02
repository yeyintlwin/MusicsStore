package com.yeyintlwin.musicsstore.ui.fragment.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.home.entity.HomeSectionListDataInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeSectionListDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeSectionListDataInfo> infos;

    public HomeSectionListDataAdapter() {
        infos = new ArrayList<>();
    }

    public void setData(List<HomeSectionListDataInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_single_card, viewGroup, false);
        return new SingleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        HomeSectionListDataInfo info = infos.get(i);
        SingleItemViewHolder holder = (SingleItemViewHolder) viewHolder;
        holder.textView.setText(info.getName());
        Picasso.get().load(info.getImageUrl()).centerCrop().fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    class SingleItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SingleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_item_cover);
            textView = itemView.findViewById(R.id.home_item_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
