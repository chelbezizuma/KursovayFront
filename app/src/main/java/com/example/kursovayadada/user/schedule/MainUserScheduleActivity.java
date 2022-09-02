package com.example.kursovayadada.user.schedule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kursovayadada.dataBase.DbHelperUsers;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.databinding.ActivityUserScheduleMainBinding;
import com.example.kursovayadada.user.DateActivity;
import com.example.kursovayadada.user.schedule.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainUserScheduleActivity extends AppCompatActivity {

    private ActivityUserScheduleMainBinding binding;
    private int position;
    String group;
    String nameTeacher = null;
    SchedulesDbHelper schedulesDbHelper;
    ArrayList<DescriptionOfItemInList> descriptionOfItemInLists;

    int idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserScheduleMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("date");
            group = savedInstanceState.getString("groupUser");
            System.out.println("asddddddddddddddddddddddd"+ savedInstanceState.getString("teacherName"));
            if (savedInstanceState.getString("teacherName")!=null)
                nameTeacher = savedInstanceState.getString("teacherName");

            if (position == -1)
                position = 0;

            if (position == -2)
                position = 1;
            schedulesDbHelper = new SchedulesDbHelper(this);
            setInitialData();
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),descriptionOfItemInLists);
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            TabLayout.Tab tab = tabs.getTabAt(position);
            assert tab != null;
            tab.select();
        }

    }

    void setInitialData() {
        descriptionOfItemInLists = new ArrayList<>();
        SQLiteDatabase database = schedulesDbHelper.getWritableDatabase();

        String requestGetTeachersSchedules = "SELECT * FROM "+SchedulesDbHelper.TABLE_SCHEDULES+ " WHERE " +
                SchedulesDbHelper.KEY_TEACHER + " = '"+ nameTeacher +"' ;";
        Cursor cursor;
        if (nameTeacher == null) {
            cursor = database.query(SchedulesDbHelper.TABLE_SCHEDULES, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
                do {
                    if (cursor.getString(groupIndex).equals(group)) {
                        descriptionOfItemInLists.add(new DescriptionOfItemInList(cursor.getString(teacherIndex),
                                cursor.getString(subjectIndex),cursor.getString(timeIndex),cursor.getString(dayOfWeekIndex)));
                    }
                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
                            ", groupIndex" + cursor.getString(groupIndex) +
                            ", timeIndex" + cursor.getString(timeIndex) +
                            ", subjectIndex" + cursor.getString(subjectIndex) +
                            ", teacherIndex" + cursor.getString(teacherIndex)
                    );
                } while (cursor.moveToNext());
            }else {
                Log.d("mLog", "0 rows в таблице с расписанием");
            }
        }else{
            cursor = database.rawQuery(requestGetTeachersSchedules, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
                do {
                    if (cursor.getString(teacherIndex).equals(nameTeacher)) {
                        descriptionOfItemInLists.add(new DescriptionOfItemInList(cursor.getString(groupIndex),
                                cursor.getString(subjectIndex),cursor.getString(timeIndex),cursor.getString(dayOfWeekIndex)));
                    }
                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
                            ", groupIndex" + cursor.getString(groupIndex) +
                            ", timeIndex" + cursor.getString(timeIndex) +
                            ", subjectIndex" + cursor.getString(subjectIndex) +
                            ", teacherIndex" + cursor.getString(teacherIndex)
                    );
                } while (cursor.moveToNext());
            }else {
                Log.d("mLog", "0 rows в таблице с расписанием");
            }
        }
    }
}