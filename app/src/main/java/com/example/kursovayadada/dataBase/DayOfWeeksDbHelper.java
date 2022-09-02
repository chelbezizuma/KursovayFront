package com.example.kursovayadada.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DayOfWeeksDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kursovaya";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_DAY_OF_WEEK = "dayOfWeeks";


    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    public DayOfWeeksDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DAY_OF_WEEK + "(" + KEY_ID + " integer primary key, " +
                KEY_NAME + " text "
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_DAY_OF_WEEK);
    }
}