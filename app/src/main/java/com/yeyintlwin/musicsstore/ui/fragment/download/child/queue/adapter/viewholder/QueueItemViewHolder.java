package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;

public class QueueItemViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewStatus;
    public TextView textViewPersize;
    public Button buttonDownload;
    public ProgressBar progressBar;

    public QueueInfo queueInfo;

    public QueueItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.item_queueTextViewTitle);
        textViewStatus = itemView.findViewById(R.id.item_queueTextViewStatus);
        textViewPersize = itemView.findViewById(R.id.item_queueTextViewPersize);
        buttonDownload = itemView.findViewById(R.id.item_queueButton);
        progressBar = itemView.findViewById(R.id.item_queueProgressBar);
    }
}
