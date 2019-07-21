package com.yeyintlwin.musicsstore.ui.fragment.category.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryInfo> infos;

    public CategoryAdapter() {
        infos = new ArrayList<>();
    }

    public void setData(List<CategoryInfo> categoryInfoList) {
        this.infos.clear();
        this.infos.addAll(categoryInfoList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
