package com.yeyintlwin.musicsstore.ui.fragment.category.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;

public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
    public TextView name;

    public CategoryItemViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_categoryTextView);
    }


}
