package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.dataBase.TimesDbHelper;

import java.util.ArrayList;

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

    TeachersDbHelper teachersDbHelper;
    TimesDbHelper timesDbHelper;
    DayOfWeeksDbHelper dayOfWeeksDbHelper;
    SchedulesDbHelper schedulesDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_information);
        textViewGroup = findViewById(R.id.textViewGroup);
        dateTextview = findViewById(R.id.dayTextview);

        teachersDbHelper = new TeachersDbHelper(this);
        timesDbHelper = new TimesDbHelper(this);
        dayOfWeeksDbHelper = new DayOfWeeksDbHelper(this);
        schedulesDbHelper = new SchedulesDbHelper(this);

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

        fillingInListDays();
        fillingInListTimes();
        fillingInListSubjects();

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
                fillingInListTeachers();
                spinner.showDialogWindow(AddScheduleInformationActivity.this,teacherTextview,teacherList);
            }
        });
    }

    public void buttoSaveSchedule(View view) {
        SQLiteDatabase database1 = schedulesDbHelper.getWritableDatabase();
        if ( !dateTextview.getText().toString().equals("") &&
         !textViewGroup.getText().toString().equals("") &&
         !timeTextview.getText().toString().equals("") &&
         !subjectTextview.getText().toString().equals("") &&
         !teacherTextview.getText().toString().equals("")) {

            Cursor cursor = database1.query(SchedulesDbHelper.TABLE_SCHEDULES, null, null, null, null, null, null);
            int idSchedule = -1;
            boolean findDataInTablesSchedule;
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
                do {
                    if (cursor.getString(dayOfWeekIndex).equals(dateTextview.getText().toString())&&
                    cursor.getString(groupIndex).equals(textViewGroup.getText().toString()) &&
                    cursor.getString(timeIndex).equals(timeTextview.getText().toString()) &&
                    cursor.getString(subjectIndex).equals(subjectTextview.getText().toString()) &&
                    cursor.getString(teacherIndex).equals(teacherTextview.getText().toString())) {
                        findDataInTablesSchedule = true;
                        idSchedule = cursor.getInt(idIndex);
                        break;
                    }else {
                        findDataInTablesSchedule = false;
                    }
                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
                            ", groupIndex" + cursor.getString(groupIndex) +
                            ", timeIndex" + cursor.getString(timeIndex) +
                            ", subjectIndex" + cursor.getString(subjectIndex) +
                            ", teacherIndex" + cursor.getString(teacherIndex)
                    );
                } while (cursor.moveToNext());
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(SchedulesDbHelper.KEY_DAY_OF_WEEK, dateTextview.getText().toString());
                contentValues1.put(SchedulesDbHelper.KEY_GROUP, textViewGroup.getText().toString());
                contentValues1.put(SchedulesDbHelper.KEY_TIME, timeTextview.getText().toString());
                contentValues1.put(SchedulesDbHelper.KEY_SUBJECT, subjectTextview.getText().toString());
                contentValues1.put(SchedulesDbHelper.KEY_TEACHER, teacherTextview.getText().toString());

                if (findDataInTablesSchedule){
                    database1.update(SchedulesDbHelper.TABLE_SCHEDULES,contentValues1,SchedulesDbHelper.KEY_ID +"="+idSchedule,null);
                    Toast.makeText(this, "Данные в  таблице обновлены", Toast.LENGTH_SHORT).show();
                }else{
                    database1.insert(SchedulesDbHelper.TABLE_SCHEDULES, null, contentValues1);
                    Toast.makeText(this, "Данные добавлены в таблицу", Toast.LENGTH_SHORT).show();
                }
            }else {
                Log.d("mLog", "0 rows в таблице с расписанием");
            }
//            database1.delete(SchedulesDbHelper.TABLE_SCHEDULES,SchedulesDbHelper.KEY_ID +"="+6,null);
//            database1.delete(SchedulesDbHelper.TABLE_SCHEDULES,SchedulesDbHelper.KEY_ID +"="+8,null);

//            ContentValues contentValues1 = new ContentValues();
//            contentValues1.put(SchedulesDbHelper.KEY_DAY_OF_WEEK, dateTextview.getText().toString());
//            contentValues1.put(SchedulesDbHelper.KEY_GROUP, textViewGroup.getText().toString());
//            contentValues1.put(SchedulesDbHelper.KEY_TIME, timeTextview.getText().toString());
//            contentValues1.put(SchedulesDbHelper.KEY_SUBJECT, subjectTextview.getText().toString());
//            contentValues1.put(SchedulesDbHelper.KEY_TEACHER, teacherTextview.getText().toString());
//
//            database1.insert(SchedulesDbHelper.TABLE_SCHEDULES, null, contentValues1);

        }else
        Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
    }


    private void fillingInListTimes() {
        SQLiteDatabase database = timesDbHelper.getWritableDatabase();
        Cursor cursor = database.query(TimesDbHelper.TABLE_TIME,null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(TimesDbHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(TimesDbHelper.KEY_NAME);
            do {
                timeList.add(cursor.getString(nameIndex));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows  в таблице со временем");
        }
        System.out.println(timeList);

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

    private void fillingInListSubjects() {
        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();

        String requestWithFilterTeachers = "SELECT DISTINCT "+ TeachersDbHelper.KEY_SUBJECT +
                " FROM  " + TeachersDbHelper.TABLE_TEACHERS +";";

        Cursor cursor = database.rawQuery(requestWithFilterTeachers, null);
        if (cursor.moveToFirst()) {
            int subjectsIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_SUBJECT);
            do {
                subjectList.add(cursor.getString(subjectsIndex));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows  в таблице с предметами ");
        }
        System.out.println(subjectList);
    }

    private void fillingInListTeachers() {
        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();
        teacherList = new ArrayList<>();
        String requestWithFilterTeachers = "SELECT "+ TeachersDbHelper.KEY_SUBJECT+", "+ TeachersDbHelper.KEY_NAME +
                " FROM  " + TeachersDbHelper.TABLE_TEACHERS +
                " WHERE "+TeachersDbHelper.KEY_SUBJECT+" = '"+ subjectTextview.getText() +"' ;";

        Cursor cursor = database.rawQuery(requestWithFilterTeachers, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_ID);
            int teachersIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_NAME);
            do {
//                System.out.println(cursor.getString(teachersIndex));
                teacherList.add(cursor.getString(teachersIndex));
            } while (cursor.moveToNext());
        }else {
            Log.d("mLog", "0 rows  в таблице с именами учителей");
        }
        System.out.println(teacherList);
    }
}