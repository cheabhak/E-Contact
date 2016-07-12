package com.lycheabhak.e_contact;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by LY CHEABHAK on 6/28/2016.
 */
public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}