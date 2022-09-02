package com.example.kursovayadada.dataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelperUsers extends SQLiteOpenHelper {

    public static String DATABASE_PATH;
    public static String DATABASE_NAME = "kursovaya";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USER = "users";


    public static final String KEY_ID = "_id";
    public static final String KEY_GROUP_ID = "group_id";
    public static final String KEY_ROLE = "role";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    private Context myContext;

    public DbHelperUsers(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + "("+ KEY_ID+ " integer primary key, " +
                KEY_GROUP_ID +" text, "+
                KEY_ROLE + " text, "+
                KEY_NAME + " text, "+
                KEY_LOGIN +" text, "+
                KEY_PASSWORD + " text "
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_USER);
    }
    public SQLiteDatabase open()throws SQLException {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void onCreateDb() {
        File file = new File(DATABASE_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DATABASE_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
}
