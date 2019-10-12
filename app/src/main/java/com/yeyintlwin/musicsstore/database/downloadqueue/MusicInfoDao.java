package com.yeyintlwin.musicsstore.database.downloadqueue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yeyintlwin.musicsstore.database.downloadqueue.entity.DownloadMusicInfo;

import java.util.ArrayList;
import java.util.List;

public class MusicInfoDao extends MusicAbstractDao<DownloadMusicInfo> {
    static final String DATABASE_NAME = "queue_download";
    static final int DATABASE_VERSION = 1;

    @SuppressLint("StaticFieldLeak")
    private static MusicInfoDao musicInfoDao;
    private final String TABLE_NAME = "musics";

    private MusicInfoDao(Context context) {
        super(context);
    }

    public static MusicInfoDao getInstance(Context context) {
        if (musicInfoDao == null) musicInfoDao = new MusicInfoDao(context);
        return musicInfoDao;
    }

    void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (_id integer primary key autoincrement, id text, title text, artist text, genre text, album text, cover text, link text, created_date datetime default current_timestamp, modified_date datetime default current_timestamp)");
    }

    void insertMusic(DownloadMusicInfo downloadMusicInfo) {
        if (isExists(downloadMusicInfo.getId())) return;
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("insert into " + TABLE_NAME + " (id, title, artist, genre, album, cover, link) values (?, ?, ?, ?, ?, ?, ?)",
                new Object[]{
                        downloadMusicInfo.getId(),
                        downloadMusicInfo.getTitle(),
                        downloadMusicInfo.getArtist(),
                        downloadMusicInfo.getGenre(),
                        downloadMusicInfo.getAlbum(),
                        downloadMusicInfo.getCover(),
                        downloadMusicInfo.getLink()});
    }

    void deleteMusic(String id) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME + " where id = ?", new Object[]{id});
    }

    void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public boolean isExists(String id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[]{id});
        boolean isExists = cursor.moveToNext();
        cursor.close();
        return isExists;
    }

    List<DownloadMusicInfo> getAllMusics() {
        List<DownloadMusicInfo> musicInfoList = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " order by created_date desc", null);
        while (cursor.moveToNext()) {
            DownloadMusicInfo info = new DownloadMusicInfo();
            info.setId(cursor.getString(cursor.getColumnIndex("id")));
            info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            info.setArtist(cursor.getString(cursor.getColumnIndex("artist")));
            info.setGenre(cursor.getString(cursor.getColumnIndex("genre")));
            info.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
            info.setCover(cursor.getString(cursor.getColumnIndex("cover")));
            info.setLink(cursor.getString(cursor.getColumnIndex("link")));
            musicInfoList.add(info);
        }
        cursor.close();
        return musicInfoList;
    }

    @Override
    public void close() {
        super.close();
    }
}
