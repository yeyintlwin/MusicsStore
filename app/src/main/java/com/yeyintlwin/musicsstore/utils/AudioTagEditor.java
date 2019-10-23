package com.yeyintlwin.musicsstore.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.ArtworkFactory;

import java.io.File;

public class AudioTagEditor {
    @SuppressLint("StaticFieldLeak")
    private static AudioTagEditor tagEditor;
    private MusicInfo mMusicInfo;
    private Context mContext;

    private AudioTagEditor(Context context) {
        this.mContext = context;
    }

    public static AudioTagEditor getInstance(Context context) {
        if (tagEditor == null) tagEditor = new AudioTagEditor(context);
        return tagEditor;
    }

    public AudioTagEditor setTag(MusicInfo info) {
        this.mMusicInfo = info;
        return tagEditor;
    }

    public void target(File musicFile) throws Exception {
        AudioFile audioFile = AudioFileIO.read(musicFile);
        Tag tag = audioFile.getTag();
        tag.setField(FieldKey.TITLE, mMusicInfo.getTitle());
        tag.setField(FieldKey.ARTIST, mMusicInfo.getArtist());
        tag.setField(FieldKey.GENRE, mMusicInfo.getGenre());
        tag.setField(FieldKey.ALBUM, mMusicInfo.getAlbum());
        tag.deleteArtworkField();
        File artWork = PicassoCacheRecycle.with(mContext).getFile(mMusicInfo.getCover());
        if (artWork.exists())
            tag.setField(ArtworkFactory.createArtworkFromFile(artWork));
        audioFile.commit();
    }

}
