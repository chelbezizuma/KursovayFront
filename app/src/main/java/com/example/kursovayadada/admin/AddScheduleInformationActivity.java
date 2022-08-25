package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.User;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleInformationActivity extends AppCompatActivity {
    // Initialize variable
    TextView textViewGroup ;
    TextView dateTextview ;
    TextView timeTextview;
    TextView subjectTextview;
    TextView teacherTextview;
    ArrayList<String> timeList;
    ArrayList<String> subjectList;
    ArrayList<String> teacherList;
    ArrayList<String> dayList;
    String group;
    String date;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_information);
        textViewGroup = findViewById(R.id.textViewGroup);
        dateTextview = findViewById(R.id.dayTextview);

        spinner = new Spinner();
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            group = String.valueOf(savedInstanceState.getSerializable("group"));
            date = (String) savedInstanceState.getSerializable("date");
            textViewGroup.setText(group);
            dateTextview.setText(date);
        }

        // assign variable
        timeTextview =findViewById(R.id.timeTextview);
        subjectTextview =findViewById(R.id.subjectTextview);
        teacherTextview =findViewById(R.id.teacherTextview);

        // initialize array list
        timeList =new ArrayList<>();
        subjectList =new ArrayList<>();
        teacherList =new ArrayList<>();
        dayList =new ArrayList<>();


        dayList.add("Пн");
        dayList.add("Вт");
        dayList.add("Ср");

        // set value in array list
        timeList.add("8:30-10:00");
        timeList.add("10:10-11:40");
        timeList.add("11:50-12:20");

        // set value in array list
        subjectList.add("Осис");
        subjectList.add("Матан");
        subjectList.add("Русский");

        // set value in array list
        teacherList.add("Русичка");
        teacherList.add("Асхатов");
        teacherList.add("Матренина");

        dateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this,dateTextview,dayList);
            }
        });

        timeTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this,timeTextview,timeList);
            }
        });

        subjectTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this,subjectTextview,subjectList);
            }
        });

        teacherTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this,teacherTextview,teacherList);
            }
        });
    }
}