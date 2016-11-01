package com.example.nromantsov.imageviewer.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "URL_DB";
    private static final String TABLE_URLS = "urls";
    private static final String KEY_TAG = "tag";
    private static final String KEY_URL = "url";

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_URLS_TABLE = "CREATE TABLE " + TABLE_URLS + "("
                + KEY_TAG + " TEXT, "
                + KEY_URL + " TEXT)";
        db.execSQL(CREATE_URLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URLS);
        onCreate(db);
    }

    public void addUrl (UrlBase urlBase) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TAG, urlBase.getTag());
        cv.put(KEY_URL, urlBase.getUrl());

        db.insert(TABLE_URLS, null, cv);
        db.close();
    }

    public List<UrlBase> getTagUrls() {
        List<UrlBase> urlsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_URLS, null);

        while (cursor.moveToNext()) {
            UrlBase urlBase = new UrlBase();

            urlBase.setId(cursor.getInt(0));
            urlBase.setTag(cursor.getString(1));
            urlBase.setUrl(cursor.getString(2));

            urlsList.add(urlBase);
        }

        cursor.close();

        return urlsList;
    }
}
