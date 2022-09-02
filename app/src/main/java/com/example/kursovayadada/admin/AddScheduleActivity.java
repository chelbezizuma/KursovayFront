package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.DbHelperUsers;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.models.SimpleTables;
import com.example.kursovayadada.models.User;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity {
    // Initialize variable
    TextView groupTextview;
    TextView dayTextDate;
    ArrayList<String> groupList;
    ArrayList<String> dayList;
//    ArrayList<SimpleTables> dayList;
    Spinner spinner;

    String requestWithFilterGroup = "SELECT DISTINCT "+DbHelperUsers.KEY_GROUP_ID +
            " FROM  " + DbHelperUsers.TABLE_USER +";";
    List<String> infoGroupDate = new ArrayList();

    private DbHelperUsers dbHelperUsers;
    private DayOfWeeksDbHelper dayOfWeeksDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_schedule);
        spinner = new Spinner();

        dbHelperUsers = new DbHelperUsers(this);
        dayOfWeeksDbHelper = new DayOfWeeksDbHelper(this);

        // assign variable
        groupTextview =findViewById(R.id.groupTextview);
        dayTextDate = findViewById(R.id.dayTextview);
        // initialize array list
        groupList =new ArrayList<>();
        dayList =new ArrayList<>();

        // set value in array list
//        groupList.add("09-951");
//        groupList.add("09-952");
//        groupList.add("09-953");

//        dayList.add("Пн");
//        dayList.add("Вт");
//        dayList.add("Ср");
        fillingInListDays();
        fillingInListGroup();

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

    private void fillingInListDays() {
        SQLiteDatabase database = dayOfWeeksDbHelper.getWritableDatabase();
        Cursor cursor = database.query(DayOfWeeksDbHelper.TABLE_DAY_OF_WEEK,null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DayOfWeeksDbHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DayOfWeeksDbHelper.KEY_NAME);
            do {
                dayList.add(cursor.getString(nameIndex));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows  в таблице в днями недели");
        }
            System.out.println(dayList);

    }

    private void fillingInListGroup() {
        SQLiteDatabase database = dbHelperUsers.getWritableDatabase();

        Cursor cursor = database.rawQuery(requestWithFilterGroup, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbHelperUsers.KEY_ID);
            int groupIndex = cursor.getColumnIndex(DbHelperUsers.KEY_GROUP_ID);
            do {
                if (!cursor.getString(groupIndex).equals("0") && !cursor.getString(groupIndex).equals("-1"))
                groupList.add(cursor.getString(groupIndex));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows  в таблице в днями недели");
        }
            System.out.println(groupList);

    }

    public void addScheduleButton(View view) {
        System.out.println(infoGroupDate);
        Intent intent = new Intent(this, AddScheduleInformationActivity.class);
        intent.putExtra("group",groupTextview.getText().toString()); //Optional parameters
        intent.putExtra("date",dayTextDate.getText().toString()); //Optional parameters
        startActivity(intent);
    }
}