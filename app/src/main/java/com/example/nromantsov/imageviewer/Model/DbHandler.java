package com.example.nromantsov.imageviewer.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nromantsov.imageviewer.View.ApplicationBase;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "URL_DB";
    private static final String TABLE_URLS = "urls";
    private static final String KEY_TAG = "tag";
    private static final String KEY_URL = "url";

    public DbHandler(Context context) {
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

    public void addUrl(UrlBase urlBase) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TAG, urlBase.getTag());
        cv.put(KEY_URL, urlBase.getUrl());

        db.insert(TABLE_URLS, null, cv);
        db.close();

        ApplicationBase.obs.getObserverChange().setUrl(urlBase.getUrl());
    }

    public List<String> getUrls(String tag) {
        List<String> urlsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_URL + " FROM " + TABLE_URLS + " WHERE " + KEY_TAG + "='" + tag + "'", null);

        while (cursor.moveToNext()) {
            urlsList.add(cursor.getString(0));
        }
        cursor.close();
        return urlsList;
    }

    public void deleteUrlFavorite(String url) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_URLS, "url = '" + url + "'", null);

        ApplicationBase.obs.getObserverChange().setUrl("delete");
    }

    public void deleteUrls(String tag) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_URLS, "tag = '" + tag + "'", null);

        ApplicationBase.obs.getObserverChange().setUrl("delete");
    }
}
