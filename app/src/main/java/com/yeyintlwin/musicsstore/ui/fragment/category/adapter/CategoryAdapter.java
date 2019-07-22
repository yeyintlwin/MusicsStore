package com.yeyintlwin.musicsstore.ui.fragment.category.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder.CategoryItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder.listener.OnCategoryItemClickListener;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryInfo> ifs;
    private OnCategoryItemClickListener listener;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final CategoryInfo categoryInfo = ifs.get(i);
        CategoryItemViewHolder itemViewHolder = (CategoryItemViewHolder) viewHolder;
        itemViewHolder.name.setText(categoryInfo.getName());

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onCategoryItemClick(categoryInfo);
            }
        });
    }

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener categoryItemClickListener) {
        listener = categoryItemClickListener;
    }

    @Override
    public int getItemCount() {
        return ifs.size();
    }
}
