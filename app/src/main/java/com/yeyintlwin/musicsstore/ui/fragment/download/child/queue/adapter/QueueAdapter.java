package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.viewholder.QueueItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;

import java.util.ArrayList;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<QueueInfo> infos;

    public QueueAdapter() {
        infos = new ArrayList<>();
    }

    public void setData(List<QueueInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new QueueItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_queue, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final QueueItemViewHolder itemViewHolder = (QueueItemViewHolder) viewHolder;
        final QueueInfo musicInfo = infos.get(i);
        itemViewHolder.textViewTitle.setText(musicInfo.getTitle());
//        itemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
//        itemViewHolder.textViewPersize.setText(musicInfo.getDownloadPerSize());
//        itemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
//        itemViewHolder.progressBar.setProgress(musicInfo.getProgress());

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
