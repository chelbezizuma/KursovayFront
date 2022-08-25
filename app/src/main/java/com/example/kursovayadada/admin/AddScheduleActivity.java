package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity {
    // Initialize variable
    TextView groupTextview;
    TextView dayTextDate;
    ArrayList<String> groupList;
    ArrayList<String> dayList;
    Spinner spinner;
    List<String> infoGroupDate = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_schedule);
        spinner = new Spinner();
        // assign variable
        groupTextview =findViewById(R.id.groupTextview);
        dayTextDate = findViewById(R.id.dayTextview);
        // initialize array list
        groupList =new ArrayList<>();
        dayList =new ArrayList<>();

        // set value in array list
        groupList.add("09-951");
        groupList.add("09-952");
        groupList.add("09-953");

        dayList.add("Пн");
        dayList.add("Вт");
        dayList.add("Ср");

        groupTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleActivity.this,groupTextview,groupList);
            }
        });

        dayTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleActivity.this,dayTextDate,dayList);
            }
        });
    }

    public void addScheduleButton(View view) {
        System.out.println(infoGroupDate);
        Intent intent = new Intent(this, AddScheduleInformationActivity.class);
        intent.putExtra("group",groupTextview.getText().toString()); //Optional parameters
        intent.putExtra("date",dayTextDate.getText().toString()); //Optional parameters
        startActivity(intent);
    }


}