package com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder.listener.OnCategoryItemClickListener;
import com.yeyintlwin.musicsstore.ui.fragment.category.entity.CategoryInfo;

public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
    public CategoryInfo categoryInfo;
    private OnCategoryItemClickListener listener;

    public CategoryItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onCategoryItemClick(categoryInfo);
            }
        });
    }

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener categoryItemClickListener) {
        listener = categoryItemClickListener;
    }
}
