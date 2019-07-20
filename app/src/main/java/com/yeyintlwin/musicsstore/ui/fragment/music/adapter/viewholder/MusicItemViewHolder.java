package com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;

public class MusicItemViewHolder extends RecyclerView.ViewHolder {

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

    public MusicItemViewHolder(@NonNull View itemView) {
        super(itemView);
        cover = itemView.findViewById(R.id.itemmusicImageViewCover);
        title = itemView.findViewById(R.id.itemmusicTextViewTitle);
        artist = itemView.findViewById(R.id.itemmusicTextViewArtist);
        genre = itemView.findViewById(R.id.itemmusicTextViewGenre);
        album = itemView.findViewById(R.id.itemmusicTextViewAlbum);
        country = itemView.findViewById(R.id.itemmusicTextViewCountry);
        counter = itemView.findViewById(R.id.itemmusicTextViewCounter);
        persize = itemView.findViewById(R.id.itemmusicTextViewPerSize);
        progress = itemView.findViewById(R.id.itemmusicProgressBar);
        download = itemView.findViewById(R.id.itemmusicTextViewDownload);
        status = itemView.findViewById(R.id.itemmusicTextViewStatus);
        progressLayout = itemView.findViewById(R.id.itemmusicRelativeLayoutProgressLayout);
    }
}
