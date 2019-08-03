package com.yeyintlwin.musicsstore.ui.fragment.favorite.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;

public class FavoriteItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView cover;
    public TextView title;
    public TextView artist;
    public TextView genre;
    public TextView album;
    public TextView country;
    public TextView download;
    public TextView counter;
    public TextView status;
    public TextView persize;
    public ProgressBar progress;
    public RelativeLayout progressLayout;

    public FavoriteItemViewHolder(@NonNull View itemView) {
        super(itemView);
        cover = itemView.findViewById(R.id.item_favorite_ImageViewCover);
        title = itemView.findViewById(R.id.item_favorite_TextViewTitle);
        artist = itemView.findViewById(R.id.item_favorite_TextViewArtist);
        genre = itemView.findViewById(R.id.item_favorite_TextViewGenre);
        album = itemView.findViewById(R.id.item_favorite_TextViewAlbum);
        country = itemView.findViewById(R.id.item_favorite_TextViewCountry);
        counter = itemView.findViewById(R.id.item_favorite_TextViewCounter);
        persize = itemView.findViewById(R.id.item_favorite_TextViewPerSize);
        progress = itemView.findViewById(R.id.item_favorite_ProgressBar);
        download = itemView.findViewById(R.id.item_favorite_TextViewDownload);
        status = itemView.findViewById(R.id.item_favorite_TextViewStatus);
        progressLayout = itemView.findViewById(R.id.item_favorite_RelativeLayoutProgressLayout);
    }
}
