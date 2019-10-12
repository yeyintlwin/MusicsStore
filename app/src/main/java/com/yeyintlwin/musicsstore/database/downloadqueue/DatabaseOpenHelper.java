package com.yeyintlwin.musicsstore.database.downloadqueue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    DatabaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        MusicInfoDao.getInstance(mContext).createTable(db);
    }

    private void dropTable(SQLiteDatabase db) {
        MusicInfoDao.getInstance(mContext).dropTable(db);
    }

}
