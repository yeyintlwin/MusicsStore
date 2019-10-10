package com.yeyintlwin.musicsstore.ui.fragment.music.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.squareup.picasso.Picasso;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder.MusicItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder.ProgressViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;

    private List<MusicInfo> infos;

    public MusicAdapter() {
        infos = new ArrayList<>();
    }

    public void setData(List<MusicInfo> musicInfoList) {
        infos.clear();
        infos.addAll(musicInfoList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_ITEM) return new MusicItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_music, viewGroup, false));
        return new ProgressViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_progress, viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        return infos.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (!(viewHolder instanceof MusicItemViewHolder)) return;
        final MusicInfo musicInfo = infos.get(i);
        final MusicItemViewHolder musicItemViewHolder = (MusicItemViewHolder) viewHolder;
        musicItemViewHolder.title.setText(musicInfo.getTitle());
        musicItemViewHolder.artist.setText(musicInfo.getArtist());
        musicItemViewHolder.genre.setText(musicInfo.getGenre());
        musicItemViewHolder.album.setText(musicInfo.getAlbum());
        musicItemViewHolder.country.setText(musicInfo.getCountry());
        musicItemViewHolder.counter.setText(musicInfo.getCounter());


        if (URLUtil.isValidUrl(musicInfo.getCover()))
            Picasso.get().load(musicInfo.getCover()).error(R.drawable.cover).fit().centerCrop().into(musicItemViewHolder.cover);
        else
            musicItemViewHolder.cover.setImageResource(R.drawable.cover);

        /*musicItemViewHolder.perSize.setText(musicInfo.getDownloadPerSize());
        musicItemViewHolder.progress.setProgress(musicInfo.getProgress());
        musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
        musicItemViewHolder.download.setText(musicInfo.getButtonText());*/
        musicItemViewHolder.info = musicInfo;
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

}
