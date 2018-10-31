package com.example.consultants.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InfoDatabase extends SQLiteOpenHelper {
    private static final String TAG = InfoDatabase.class.getSimpleName() + "_tag";
    int i = 0;

    public InfoDatabase(@Nullable Context context) {
        super(context, Contract.NAME, null, Contract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.DELETE_TABLE);
        onCreate(db);
    }

    public long saveInfo(InfoImage infoImage) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.FeedEntry.COL_ID,"1");
        contentValues.put(Contract.FeedEntry.COL_BLOB,infoImage.getBlob());
        contentValues.put(Contract.FeedEntry.COL_DESC,infoImage.getDesc());

        return db.insert(Contract.FeedEntry.TABLE_NAME, null, contentValues);

    }
/*
    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS photo";
        SQLiteStatement dropStmt = db.compileStatement(sql);
        dropStmt.executeInsert();
        db.close();
    }*/

    public int updateRow(InfoImage infoImage) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.FeedEntry.COL_BLOB,infoImage.getBlob());
        contentValues.put(Contract.FeedEntry.COL_DESC,infoImage.getDesc());
        int a = db.update(Contract.FeedEntry.TABLE_NAME, contentValues, Contract.FeedEntry.COL_ID+"=" + infoImage.getId(),  null);
        return a;
    }

    public int deleteRow(String s) {
        SQLiteDatabase db = getWritableDatabase();
        int a = db.delete(Contract.FeedEntry.TABLE_NAME,Contract.FeedEntry.COL_ID+"="+s,null);
        return a;
    }

    public List<InfoImage> showTable (){
        SQLiteDatabase db = getWritableDatabase();
        List<InfoImage> infoImageList = new ArrayList<>();

        Cursor cursor = db.rawQuery(Contract.GET_ALL, null);
        if (cursor.moveToFirst()) {
            do {
                InfoImage infoImage = new InfoImage(cursor.getBlob(cursor.getColumnIndex(Contract.FeedEntry.COL_BLOB)),
                        cursor.getString(cursor.getColumnIndex(Contract.FeedEntry.COL_DESC)),
                        cursor.getString(cursor.getColumnIndex(Contract.FeedEntry.COL_ID)));

                infoImageList.add(infoImage);
            } while (cursor.moveToNext());
        }
        return infoImageList;
    }


}
