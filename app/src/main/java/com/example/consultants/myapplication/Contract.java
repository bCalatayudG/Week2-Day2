package com.example.consultants.myapplication;

import android.provider.BaseColumns;

public class Contract {
    public static final String NAME = "database2.db";
    public static final int VERSION = 1;
    public static int n_id;
    public static String n_info;

    public static final String CREATE_TABLE =
            "CREATE TABLE " +
                    FeedEntry.TABLE_NAME + "(" +
                    FeedEntry.COL_ID + " Text, " +
                    FeedEntry.COL_BLOB + " Text, " +
                    FeedEntry.COL_DESC + " Text)";


    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "photo";
        public static final String COL_ID = "id";
        public static final String COL_BLOB = "blob";
        public static final String COL_DESC = "desc";
    }

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final String GET_ALL = "SELECT * FROM " + FeedEntry.TABLE_NAME;

}
