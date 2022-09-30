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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kursovayadada.admin.AdminMainActivity;
import com.example.kursovayadada.dataBase.DbHelperUsers;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.FacultysAndSpecialitysDbHelper;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.dataBase.TimesDbHelper;
import com.example.kursovayadada.models.User;
import com.example.kursovayadada.user.UserMainActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private List<User> users = new ArrayList<>();
    private EditText login;
    private EditText password;
    private static final String GET_USER_URL = "http://10.0.2.2:8080/user/";

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
        getUserList(GET_USER_URL);

//        contentValues4.put(DbHelperUsers.KEY_GROUP_ID,"-1");
//        contentValues4.put(DbHelperUsers.KEY_ROLE,"teacher");
//        contentValues4.put(DbHelperUsers.KEY_NAME,"Васильев Даниил Константинович");
//        contentValues4.put(DbHelperUsers.KEY_LOGIN,"d");
//        contentValues4.put(DbHelperUsers.KEY_PASSWORD,"d");
//        database.insert(DbHelperUsers.TABLE_USER, null, contentValues4);

//        Cursor cursor = database.query(DbHelperUsers.TABLE_USER, null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DbHelperUsers.KEY_ID);
//            int groupIdIndex = cursor.getColumnIndex(DbHelperUsers.KEY_GROUP_ID);
//            int nameIndex = cursor.getColumnIndex(DbHelperUsers.KEY_NAME);
//            int roleIndex = cursor.getColumnIndex(DbHelperUsers.KEY_ROLE);
//            int loginIndex = cursor.getColumnIndex(DbHelperUsers.KEY_LOGIN);
//            int passwordIndex = cursor.getColumnIndex(DbHelperUsers.KEY_PASSWORD);
//            do {
//                Log.d("mLog", "ID" + cursor.getInt(idIndex) +
//                        ", group_id" + cursor.getString(groupIdIndex) +
//                        ", name" + cursor.getString(nameIndex) +
//                        ", role" + cursor.getString(roleIndex) +
//                        ", login" + cursor.getString(loginIndex) +
//                        ", password" + cursor.getString(passwordIndex)
//                );
//                users.add(new User(cursor.getInt(idIndex),cursor.getString(groupIdIndex),cursor.getString(nameIndex),
//                        cursor.getString(roleIndex),cursor.getString(loginIndex),cursor.getString(passwordIndex)));
//            } while (cursor.moveToNext());
//        }else {
//            Log.d("mLog", "0 rows");
//        }
//        cursor.close();

//        users.add(new User(1,"a", "a", "adm", "Amir"));
//        users.add(new User(2,"b", "b", "usr", "SimpleAmir"));
        login = (EditText) findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        for (int i = 0; i < users.size(); i++) {
            if (login.getText().toString().equals(users.get(i).getLogin()) && password.getText().toString().equals(users.get(i).getPassword())) {
                if (users.get(i).getRole() != null) {
                    openNewActivity(users.get(i).getRole(), users.get(i));
                    System.out.println(users.get(i).getGroup_id());
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

    private void  getUserList(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray (EncodingToUTF8(response));
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode[] node = mapper.readValue(object.toString(),  ObjectNode[].class);
                        for (int i = 0; i < node.length; i++) {
                            System.out.println(node[i].get("id") );
                        }
//                        System.out.println(node.get("id"));
                        users  = mapper.readValue(object.toString(), new TypeReference<List<User>>(){});
                    }catch (JSONException | JsonProcessingException e){
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public  static  String EncodingToUTF8(String response){
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        return response;
    }
}