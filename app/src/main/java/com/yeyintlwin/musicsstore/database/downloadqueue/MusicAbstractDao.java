package com.yeyintlwin.musicsstore.database.downloadqueue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MusicAbstractDao<T> {


    private DatabaseOpenHelper openHelper;

    MusicAbstractDao(Context context) {
        openHelper = new DatabaseOpenHelper(context, MusicInfoDao.DATABASE_NAME, null, MusicInfoDao.DATABASE_VERSION);
    }

    SQLiteDatabase getWritableDatabase() {
        return openHelper.getWritableDatabase();
    }

    SQLiteDatabase getReadableDatabase() {
        return openHelper.getReadableDatabase();
    }

    public void close() {
        openHelper.close();
    }
}
