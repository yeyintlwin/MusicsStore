package com.yeyintlwin.musicsstore.ui.fragment.favorite.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.squareup.picasso.Picasso;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.adapter.viewholder.FavoriteItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.favorite.entity.FavoriteInfo;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FavoriteInfo> infos;

    public FavoriteAdapter() {
        infos = new ArrayList<>();
    }

    public void setData(List<FavoriteInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavoriteItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_favorite, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final FavoriteInfo favoriteInfo = infos.get(i);
        final FavoriteItemViewHolder favoriteItemViewHolder = (FavoriteItemViewHolder) viewHolder;
        favoriteItemViewHolder.title.setText(favoriteInfo.getTitle());
        favoriteItemViewHolder.artist.setText(favoriteInfo.getArtist());
        favoriteItemViewHolder.genre.setText(favoriteInfo.getGenre());
        favoriteItemViewHolder.album.setText(favoriteInfo.getAlbum());
        favoriteItemViewHolder.country.setText(favoriteInfo.getCountry());
        favoriteItemViewHolder.counter.setText(String.valueOf(favoriteInfo.getCounter()));
        if (URLUtil.isValidUrl(favoriteInfo.getCover()))
            Picasso.get().load(favoriteInfo.getCover()).error(R.drawable.cover).fit().centerCrop().into(favoriteItemViewHolder.cover);
        else
            favoriteItemViewHolder.cover.setImageResource(R.drawable.cover);
        //favoriteItemViewHolder.persize.setText(favoriteInfo.getDownloadPerSize());
        //favoriteItemViewHolder.progress.setProgress(favoriteInfo.getProgress());
        //favoriteItemViewHolder.status.setText(favoriteInfo.getStatusEmoji());
        //favoriteItemViewHolder.download.setText(favoriteInfo.getButtonText());
        //favoriteItemViewHolder.info = favoriteInfo;

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
