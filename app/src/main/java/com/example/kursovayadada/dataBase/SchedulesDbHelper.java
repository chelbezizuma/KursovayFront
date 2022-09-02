package com.example.kursovayadada.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SchedulesDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "kursovaya";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_SCHEDULES = "schedules";


    public static final String KEY_ID = "_id";
    public static final String KEY_GROUP = "groups";
    public static final String KEY_DAY_OF_WEEK = "dayOfWeek";
    public static final String KEY_TIME = "time";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_TEACHER = "teacher";

    public SchedulesDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SCHEDULES + "(" + KEY_ID + " integer primary key, " +
                KEY_DAY_OF_WEEK + " text, " +
                KEY_GROUP + " text, " +
                KEY_TIME + " text, " +
                KEY_SUBJECT + " text, " +
                KEY_TEACHER + " text "
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_SCHEDULES);
    }
}