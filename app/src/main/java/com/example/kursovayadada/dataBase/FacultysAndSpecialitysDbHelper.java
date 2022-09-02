package com.example.kursovayadada.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FacultysAndSpecialitysDbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "kursovaya";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FACULTYS_AND_SPECIALITYS = "facultysAndSpecialitys";


    public static final String KEY_ID = "_id";
    public static final String KEY_FACULTYS = "facultys";
    public static final String KEY_SPECIALITY = "specialitys";

    public FacultysAndSpecialitysDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FACULTYS_AND_SPECIALITYS + "(" + KEY_ID + " integer primary key, " +
                KEY_FACULTYS + " text, "+
                KEY_SPECIALITY + " text "
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_FACULTYS_AND_SPECIALITYS);
    }
}
