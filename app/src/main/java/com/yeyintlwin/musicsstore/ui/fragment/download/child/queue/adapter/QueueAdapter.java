package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.adapter.viewholder.QueueItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.listener.OnDownloadBtnClickListener;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<QueueInfo> infos;
    private OnDownloadBtnClickListener mListener;

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
        itemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
        itemViewHolder.textViewPersize.setText(musicInfo.getPerSize());
        itemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
        itemViewHolder.progressBar.setProgress(musicInfo.getProgress());
        itemViewHolder.queueInfo = musicInfo;

        switch (musicInfo.getStatus()) {
            case QueueInfo.STATUS_CONNECT_ERROR:
            case QueueInfo.STATUS_DOWNLOAD_ERROR:
                itemViewHolder.progressBar.setVisibility(View.INVISIBLE);
                itemViewHolder.textViewStatus.setTextColor(Color.RED);
                break;
            case QueueInfo.STATUS_NOT_DOWNLOAD:
                itemViewHolder.progressBar.setVisibility(View.INVISIBLE);
                itemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                break;
            case QueueInfo.STATUS_CONNECTING:
                itemViewHolder.progressBar.setVisibility(View.VISIBLE);
                itemViewHolder.progressBar.setIndeterminate(true);
                itemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                break;
            case QueueInfo.STATUS_DOWNLOADING:
                itemViewHolder.progressBar.setVisibility(View.VISIBLE);
                itemViewHolder.progressBar.setIndeterminate(false);
                itemViewHolder.textViewStatus.setTextColor(Color.GREEN);
                break;
            case QueueInfo.STATUS_COMPLETE:
                itemViewHolder.progressBar.setVisibility(View.VISIBLE);
                itemViewHolder.textViewStatus.setTextColor(Color.GREEN);
                break;
            case QueueInfo.STATUS_PAUSED:
                itemViewHolder.progressBar.setVisibility(View.VISIBLE);
                itemViewHolder.progressBar.setIndeterminate(false);
                itemViewHolder.textViewStatus.setTextColor(Color.GRAY);
                break;
        }

        itemViewHolder.buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (musicInfo.getStatus()) {
                    case QueueInfo.STATUS_NOT_DOWNLOAD:
                    case QueueInfo.STATUS_CONNECT_ERROR:
                    case QueueInfo.STATUS_PAUSED:
                    case QueueInfo.STATUS_DOWNLOAD_ERROR:
                        if (mListener != null) mListener.onDownloadAndResume(musicInfo);
                        break;
                    case QueueInfo.STATUS_CONNECTING:
                    case QueueInfo.STATUS_DOWNLOADING:
                        if (mListener != null) mListener.onPause(musicInfo);
                        break;
                    case QueueInfo.STATUS_COMPLETE:

                        File file = new File(Utils.getDownloadDir(v.getContext()), musicInfo.getTitle() + ".mp3");
                        if (file.exists()) {
                            playSong(v.getContext(), file);
                            return;
                        } else {
                            musicInfo.setStatus(0);
                            musicInfo.setProgress(0);
                            musicInfo.setPerSize("");
                            itemViewHolder.buttonDownload.setText(musicInfo.getButtonText());
                            itemViewHolder.textViewStatus.setText(musicInfo.getStatusText());
                            itemViewHolder.progressBar.setProgress(0);
                            itemViewHolder.textViewPersize.setText("0");
                            Toast.makeText(v.getContext(), "File not found.", Toast.LENGTH_LONG).show();
                        }
                        break;
                }

            }
        });

    }

    public void setOnDownloadBtnClickListener(com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.listener.OnDownloadBtnClickListener listener) {
        this.mListener = listener;
    }

    private void playSong(Context context, File file) {
        if (file.exists()) Log.w("fuck", "file is exist.");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "audio/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.w("MusicAdapter", e);
        }

    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
