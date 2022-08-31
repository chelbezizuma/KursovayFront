package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;

import java.util.ArrayList;

public class AddSubjectActivity extends AppCompatActivity {
    TextView editTextTextNameSubject;
    TextView facultyTextview;
    TextView specialityTextview;
    TextView textViewTeacher;
    ArrayList<String> facultyList;
    ArrayList<String> specialityList;
    ArrayList<String> teacherList;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_subject);

        editTextTextNameSubject = (TextView)findViewById(R.id.editTextTextNameSubject);
        facultyTextview = (TextView)findViewById(R.id.facultyTextview);
        specialityTextview = (TextView)findViewById(R.id.specialityTextview);
        textViewTeacher = (TextView)findViewById(R.id.TextviewTeacher);

        facultyList =new ArrayList<>();
        specialityList =new ArrayList<>();
        teacherList =new ArrayList<>();

        spinner = new Spinner();

        // set value in array list
        facultyList.add("ИВМиИТ");
        facultyList.add("ИТИС");
        facultyList.add("Чето еще, хз.");

        // set value in array list
        specialityList.add("Прикладная информатика");
        specialityList.add("Прикладная математика");
        specialityList.add("Бизнес информатика");

        // set value in array list
        teacherList.add("Русичка");
        teacherList.add("Асхатов");
        teacherList.add("Матренина");

        facultyTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddSubjectActivity.this,facultyTextview,facultyList);
            }
        });

        specialityTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddSubjectActivity.this,specialityTextview,specialityList);
            }
        });

        textViewTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddSubjectActivity.this,textViewTeacher,teacherList);
            }
        });
    }

    public void buttonSaveSubject(View view) {
        Toast.makeText(this, "типо данные сохранены Нет бд потом добавлю", Toast.LENGTH_SHORT).show();
    }
}