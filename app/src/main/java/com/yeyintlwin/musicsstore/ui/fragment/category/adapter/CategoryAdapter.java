package com.yeyintlwin.musicsstore.ui.fragment.category.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder.CategoryItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryInfo> ifs;

    public CategoryAdapter() {
        ifs = new ArrayList<>();
    }

    public void setData(List<CategoryInfo> categoryInfoList) {
        this.ifs.clear();
        this.ifs.addAll(categoryInfoList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new CategoryItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return ifs.size();
    }
}
