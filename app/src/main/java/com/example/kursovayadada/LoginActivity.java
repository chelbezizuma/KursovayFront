package com.example.kursovayadada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursovayadada.admin.AdminMainActivity;
import com.example.kursovayadada.dataBase.DbHelperUsers;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.FacultysAndSpecialitysDbHelper;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.dataBase.TimesDbHelper;
import com.example.kursovayadada.models.User;
import com.example.kursovayadada.user.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private List<User> users = new ArrayList<>();
    private EditText login;
    private EditText password;

    private DbHelperUsers dbHelperUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelperUsers = new DbHelperUsers(this);
    }
//    Васильев Даниил Константинович
    public void onLoginButton(View view) {
        SQLiteDatabase database = dbHelperUsers.getWritableDatabase();
//        ContentValues contentValues4 = new ContentValues();


//        contentValues4.put(DbHelperUsers.KEY_GROUP_ID,"-1");
//        contentValues4.put(DbHelperUsers.KEY_ROLE,"teacher");
//        contentValues4.put(DbHelperUsers.KEY_NAME,"Васильев Даниил Константинович");
//        contentValues4.put(DbHelperUsers.KEY_LOGIN,"d");
//        contentValues4.put(DbHelperUsers.KEY_PASSWORD,"d");
//        database.insert(DbHelperUsers.TABLE_USER, null, contentValues4);

        Cursor cursor = database.query(DbHelperUsers.TABLE_USER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbHelperUsers.KEY_ID);
            int groupIdIndex = cursor.getColumnIndex(DbHelperUsers.KEY_GROUP_ID);
            int nameIndex = cursor.getColumnIndex(DbHelperUsers.KEY_NAME);
            int roleIndex = cursor.getColumnIndex(DbHelperUsers.KEY_ROLE);
            int loginIndex = cursor.getColumnIndex(DbHelperUsers.KEY_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DbHelperUsers.KEY_PASSWORD);
            do {
                Log.d("mLog", "ID" + cursor.getInt(idIndex) +
                        ", group_id" + cursor.getString(groupIdIndex) +
                        ", name" + cursor.getString(nameIndex) +
                        ", role" + cursor.getString(roleIndex) +
                        ", login" + cursor.getString(loginIndex) +
                        ", password" + cursor.getString(passwordIndex)
                );
                users.add(new User(cursor.getInt(idIndex),cursor.getString(groupIdIndex),cursor.getString(nameIndex),
                        cursor.getString(roleIndex),cursor.getString(loginIndex),cursor.getString(passwordIndex)));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
//        users.add(new User(1,"a", "a", "adm", "Amir"));
//        users.add(new User(2,"b", "b", "usr", "SimpleAmir"));
        login = (EditText) findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        for (int i = 0; i < users.size(); i++) {
            if (login.getText().toString().equals(users.get(i).getLogin()) && password.getText().toString().equals(users.get(i).getPassword())) {
                if (users.get(i).getRole() != null) {
                    openNewActivity(users.get(i).getRole(), users.get(i));
                    Toast.makeText(this, "Вход в аккаунт", Toast.LENGTH_SHORT).show();
                } else {
                    System.err.println("Нет роли у пользователя");
                }
            } else {
                System.err.println("Пользователь не найден");
            }
        }
        // выводим сообщение
    }

    private void openNewActivity(String role, User user) {
        if (role.equals("adm")) {
            Intent intent = new Intent(this, AdminMainActivity.class);
            intent.putExtra("user", user); //Optional parameters
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, UserMainActivity.class);
            intent.putExtra("user", user); //Optional parameters
            startActivity(intent);
        }
    }
}