package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeyintlwin.musicsstore.R;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.adapter.viewholder.FinishItemViewHolder;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity.FinishInfo;
import com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.listener.OnDeleteSongListener;

import java.util.ArrayList;
import java.util.List;

public class FinishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FinishInfo> infos;

    private MediaMetadataRetriever metadataRetriever;
    private OnDeleteSongListener mListener;

    public FinishAdapter() {
        this.infos = new ArrayList<>();
        metadataRetriever = new MediaMetadataRetriever();
    }

    public void setData(List<FinishInfo> data) {
        infos.clear();
        infos.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FinishItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_finish, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FinishItemViewHolder itemViewHolder = (FinishItemViewHolder) viewHolder;
        final FinishInfo downloadedInfo = infos.get(i);
        itemViewHolder.title.setText(downloadedInfo.getTitle());
        itemViewHolder.artist.setText(downloadedInfo.getArtist());
        itemViewHolder.genre.setText(downloadedInfo.getGenre());
        itemViewHolder.album.setText(downloadedInfo.getAlbum());
        itemViewHolder.info = downloadedInfo;

        metadataRetriever.setDataSource(downloadedInfo.getPath());
        byte[] bytes = metadataRetriever.getEmbeddedPicture();
        if (bytes != null) {
            Bitmap bitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length),
                    144, 144, true);
            itemViewHolder.cover.setImageBitmap(bitmap);
        } else {
            itemViewHolder.cover.setImageResource(R.drawable.ic_launcher_foreground);
        }

        if (itemViewHolder.view != null)
            itemViewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Do you want to delete this song?");
                    builder.setMessage("Title: " + downloadedInfo.getTitle() + "\nPath: "
                            + downloadedInfo.getPath());
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mListener != null) {
                                mListener.onDeleteSong(downloadedInfo.getPath());
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                    return false;
                }
            });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public void setOnDeleteSongListener(OnDeleteSongListener listener) {
        mListener = listener;
    }
}
