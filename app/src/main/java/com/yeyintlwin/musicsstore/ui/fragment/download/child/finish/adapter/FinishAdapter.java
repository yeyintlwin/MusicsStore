package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.viewholder.FinishItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity.FinishInfo;

import java.util.ArrayList;
import java.util.List;

public class FinishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FinishInfo> infos;

    //private MediaMetadataRetriever mmr;
    public FinishAdapter() {
        this.infos = new ArrayList<>();
        //mmr = new MediaMetadataRetriever();
    }

    public void setData(List<FinishInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FinishItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_finish,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FinishItemViewHolder itemViewHolder = (FinishItemViewHolder) viewHolder;
        FinishInfo downloadedInfo = infos.get(i);
        itemViewHolder.title.setText(downloadedInfo.getTitle());
        itemViewHolder.artist.setText(downloadedInfo.getArtist());
        itemViewHolder.genre.setText(downloadedInfo.getGenre());
        itemViewHolder.album.setText(downloadedInfo.getAlbum());
        itemViewHolder.info = downloadedInfo;
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
