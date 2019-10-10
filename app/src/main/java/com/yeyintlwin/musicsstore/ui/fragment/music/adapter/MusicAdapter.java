package com.yeyintlwin.musicsstore.ui.fragment.music.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder.MusicItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.adapter.viewholder.ProgressViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;
import com.yeyintlwin.musicsstore.ui.fragment.music.listener.OnDownloadBtnClickListener;
import com.yeyintlwin.musicsstore.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;

    private List<MusicInfo> infos;
    private OnDownloadBtnClickListener mListener;

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
        if (i == VIEW_ITEM)
            return new MusicItemViewHolder(LayoutInflater.from(viewGroup.getContext())
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
            Picasso.get()
                    .load(musicInfo.getCover())
                    .error(R.drawable.cover)
                    .fit()
                    .centerCrop()
                    .into(musicItemViewHolder.cover);
        else
            musicItemViewHolder.cover.setImageResource(R.drawable.cover);

        musicItemViewHolder.perSize.setText(musicInfo.getPerSize());
        musicItemViewHolder.progress.setProgress(musicInfo.getProgress());
        musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
        musicItemViewHolder.download.setText(musicInfo.getButtonText());
        musicItemViewHolder.info = musicInfo;

        switch (musicInfo.getStatus()) {
            case MusicInfo.STATUS_CONNECT_ERROR:
            case MusicInfo.STATUS_DOWNLOAD_ERROR:
            case MusicInfo.STATUS_NOT_DOWNLOAD:
                musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                break;
            case MusicInfo.STATUS_CONNECTING:
                musicItemViewHolder.progressLayout.setVisibility(View.VISIBLE);
                musicItemViewHolder.progress.setIndeterminate(true);
                break;
            case MusicInfo.STATUS_COMPLETE:
                musicItemViewHolder.progressLayout.setVisibility(View.VISIBLE);
                break;
            case MusicInfo.STATUS_PAUSED:
            case MusicInfo.STATUS_DOWNLOADING:
                musicItemViewHolder.progressLayout.setVisibility(View.VISIBLE);
                musicItemViewHolder.progress.setIndeterminate(false);
                break;
        }

        musicItemViewHolder.download.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                musicItemViewHolder.progressLayout.setVisibility(View.VISIBLE);
                Log.w("MusicAdapter", "status: " + musicInfo.getStatus());
                switch (musicInfo.getStatus()) {
                    case MusicInfo.STATUS_NOT_DOWNLOAD:
                        musicItemViewHolder.counter.setText(
                                String.valueOf(Integer.parseInt(musicInfo.getCounter()) + 1));
                        //COUNTER VALUE UP TEMPORARY
                    case MusicInfo.STATUS_CONNECT_ERROR:
                    case MusicInfo.STATUS_PAUSED:
                    case MusicInfo.STATUS_DOWNLOAD_ERROR:
                        //DOWNLOAD_AND_RESUME
                        if (mListener != null) mListener.onDownloadAndResume(musicInfo);
                        break;
                    case MusicInfo.STATUS_CONNECTING:
                    case MusicInfo.STATUS_DOWNLOADING:
                        //PAUSE_DOWNLOAD
                        if (mListener != null) mListener.onPause(musicInfo);
                        break;
                    case MusicInfo.STATUS_COMPLETE: {
                        //PLAY_SONG
                        File file = new File(Utils.getDownloadDir(v.getContext()) + "/" + musicInfo.getTitle() + ".mp3");

                        Log.w("MusicAdapter", file.getPath());
                        if (file.exists()) {
                            playSong(v.getContext(), file);
                        } else {
                            //WHEN FILE NOT FOUND.
                            musicInfo.setStatus(0);
                            musicInfo.setProgress(0);
                            musicInfo.setPerSize("");
                            musicItemViewHolder.download.setText(musicInfo.getButtonText());
                            musicItemViewHolder.status.setText(musicInfo.getStatusEmoji());
                            musicItemViewHolder.progress.setProgress(0);
                            musicItemViewHolder.perSize.setText("0");
                            musicItemViewHolder.progressLayout.setVisibility(View.GONE);
                            Toast.makeText(v.getContext(), "File not found.", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
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

    public void setOnDownloadBtnClickListener(OnDownloadBtnClickListener listener) {
        this.mListener = listener;
    }
}
