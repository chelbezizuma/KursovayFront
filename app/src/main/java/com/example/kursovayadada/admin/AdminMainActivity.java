package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursovayadada.R;
import com.example.kursovayadada.models.User;

public class AdminMainActivity extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        TextView FIOtextView = (TextView)findViewById(R.id.textViewAdminFIO);
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            user = (User) savedInstanceState.getSerializable("user");
            System.err.println(user.getName());
            FIOtextView.setText(user.getName());
        }
    }

    public void addSubject(View view) {
        Intent intent = new Intent(this, AddSubjectActivity.class);
        startActivity(intent);
    }

    public void addSchedule(View view) {
        Intent intent = new Intent(this, AddScheduleActivity.class);
        startActivity(intent);
    }

//    public void addTeacher(View view) {
////        Intent intent = new Intent(this, AddScheduleActivity.class);
////        startActivity(intent);
//        Toast.makeText(this, "Нет активити для добавления преподавателя", Toast.LENGTH_SHORT).show();
//    }

    public void changeSchedule(View view) {
        Intent intent = new Intent(this, AdminChangeScheduleActivity.class);
        intent.putExtra("user", user); //Optional parameters
        startActivity(intent);

//        Toast.makeText(this, "Нет активити для добавления преподавателя", Toast.LENGTH_SHORT).show();
    }
}