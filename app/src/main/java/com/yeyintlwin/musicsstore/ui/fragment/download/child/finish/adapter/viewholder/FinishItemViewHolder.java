package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity.FinishInfo;

public class FinishItemViewHolder extends RecyclerView.ViewHolder {
    public TextView album;
    public TextView artist;
    public ImageView cover;
    public TextView genre;
    public TextView title;

    public FinishInfo info;

    public FinishItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.cover = itemView.findViewById(R.id.item_downloadedImageViewCover);
        this.title = itemView.findViewById(R.id.item_downloadedTextViewTitle);
        this.artist = itemView.findViewById(R.id.item_downloadedTextViewArtist);
        this.genre = itemView.findViewById(R.id.item_downloadedTextViewGenre);
        this.album = itemView.findViewById(R.id.item_downloadedTextViewAlbum);
    }
}
