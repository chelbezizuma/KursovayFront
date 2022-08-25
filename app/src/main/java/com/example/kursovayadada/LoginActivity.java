package com.example.kursovayadada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursovayadada.admin.AdminMainActivity;
import com.example.kursovayadada.user.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private List<User> users = new ArrayList<>();
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButton(View view)
    {
        users.add(new User("a","a","adm","Amir"));
        users.add(new User("b","b","usr","SimpleAmir"));
        login = (EditText) findViewById(R.id.editTextLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        int i = 1;
        if (login.getText().toString().equals(users.get(i).getLogin()) && password.getText().toString().equals(users.get(i).getPassword())) {
            if (users.get(i).getRole() != null) {
                openNewActivity(users.get(i).getRole(),users.get(i));
                Toast.makeText(this, "Вход в аккаунт", Toast.LENGTH_SHORT).show();
            }else {
                System.err.println("Нет роли у пользователя");
            }
        }else{
            System.err.println("Пользователь не найден");
        }
        // выводим сообщение
    }

    private void openNewActivity(String role, User user) {
        if (role.equals("adm")) {
            Intent intent = new Intent(this, AdminMainActivity.class);
            intent.putExtra("user", user); //Optional parameters
            startActivity(intent);
        } else {
            System.err.println("Обычный пользователь");
            Intent intent = new Intent(this, UserMainActivity.class);
            startActivity(intent);
        }
    }
}