package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
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

    public View view;


    public FinishItemViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        this.cover = itemView.findViewById(R.id.item_downloadedImageViewCover);
        this.title = itemView.findViewById(R.id.item_downloadedTextViewTitle);
        this.artist = itemView.findViewById(R.id.item_downloadedTextViewArtist);
        this.genre = itemView.findViewById(R.id.item_downloadedTextViewGenre);
        this.album = itemView.findViewById(R.id.item_downloadedTextViewAlbum);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(info.getPath()), "audio/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                v.getContext().startActivity(intent);
            }
        });


    }


}
