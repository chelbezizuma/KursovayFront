package com.example.kursovayadada.user.schedule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kursovayadada.dataBase.DbHelperUsers;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.databinding.ActivityUserScheduleMainBinding;
import com.example.kursovayadada.user.schedule.main.SectionsPagerAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainUserScheduleActivity extends AppCompatActivity {

    private ActivityUserScheduleMainBinding binding;
    private int position;
    String group;
    String chetnost;
    String nameTeacher = null;
    SchedulesDbHelper schedulesDbHelper;
    ArrayList<DescriptionOfItemInList> descriptionOfItemInLists;
    private static final String GET_SCHEDULES_FIND_BY_TEACHER_URL_NAME = "http://10.0.2.2:8080/schedules/teacherSchedules?nameTeacher=";
    private String GET_SCHEDULES_FIND_BY_TEACHER_URL = "http://10.0.2.2:8080/schedules/";

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

            if (savedInstanceState.getString("teacherName") != null) {
                nameTeacher = savedInstanceState.getString("teacherName");
            }

            if (savedInstanceState.getString("chetnost") != null)
                chetnost = savedInstanceState.getString("chetnost");

//            System.out.println("asddddddddddddddddddddddd" + group + chetnost);
            if (position == -1)
                position = 0;

            if (position == -2)
                position = 1;
            schedulesDbHelper = new SchedulesDbHelper(this);
            setInitialData();

            for (int i = 0; i < descriptionOfItemInLists.size(); i++) {
                System.out.println(descriptionOfItemInLists.get(i));
            }
            System.out.println("aaaaaaaaaaaaaa");
        }

    }

    void setInitialData() {
        descriptionOfItemInLists = new ArrayList<>();
        SQLiteDatabase database = schedulesDbHelper.getWritableDatabase();

        String requestGetTeachersSchedules = "SELECT * FROM " + SchedulesDbHelper.TABLE_SCHEDULES + " WHERE " +
                SchedulesDbHelper.KEY_TEACHER + " = '" + nameTeacher + "' ;";
//        GET_SCHEDULES_FIND_BY_TEACHER_URL = GET_SCHEDULES_FIND_BY_TEACHER_URL + nameTeacher;
//        Cursor cursor;
        if (nameTeacher == null) {
//            cursor = database.query(SchedulesDbHelper.TABLE_SCHEDULES, null, null, null, null, null, null);
//            if (cursor.moveToFirst()) {
//                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
//                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
//                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
//                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
//                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
//                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
//                do {
//                    if (cursor.getString(groupIndex).equals(group)) {
//                        descriptionOfItemInLists.add(new DescriptionOfItemInList(cursor.getString(teacherIndex),
//                                cursor.getString(subjectIndex),cursor.getString(timeIndex),cursor.getString(dayOfWeekIndex)));
//                    }
//                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
//                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
//                            ", groupIndex" + cursor.getString(groupIndex) +
//                            ", timeIndex" + cursor.getString(timeIndex) +
//                            ", subjectIndex" + cursor.getString(subjectIndex) +
//                            ", teacherIndex" + cursor.getString(teacherIndex)
//                    );
//                } while (cursor.moveToNext());
//            }else {
//                Log.d("mLog", "0 rows в таблице с расписанием");
//            }
            getSchedulesList(GET_SCHEDULES_FIND_BY_TEACHER_URL);
        } else {
            String GET_SCHEDULES_FIND_BY_TEACHER_URL_WITH_NAME = GET_SCHEDULES_FIND_BY_TEACHER_URL_NAME + nameTeacher;

            getSchedulesList(GET_SCHEDULES_FIND_BY_TEACHER_URL_WITH_NAME);
//            cursor = database.rawQuery(requestGetTeachersSchedules, null);
//
//            if (cursor.moveToFirst()) {
//                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
//                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
//                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
//                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
//                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
//                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
//                do {
//                    if (cursor.getString(teacherIndex).equals(nameTeacher)) {
//                        descriptionOfItemInLists.add(new DescriptionOfItemInList(cursor.getString(groupIndex),
//                                cursor.getString(subjectIndex),cursor.getString(timeIndex),cursor.getString(dayOfWeekIndex)));
//                    }
//                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
//                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
//                            ", groupIndex" + cursor.getString(groupIndex) +
//                            ", timeIndex" + cursor.getString(timeIndex) +
//                            ", subjectIndex" + cursor.getString(subjectIndex) +
//                            ", teacherIndex" + cursor.getString(teacherIndex)
//                    );
//                } while (cursor.moveToNext());
//            }else {
//                Log.d("mLog", "0 rows в таблице с расписанием");
//            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getSchedulesList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
                        ObjectMapper mapper = new ObjectMapper();


                        String parityOfWeek;
                        if (chetnost == null) {
                            Calendar cal = Calendar.getInstance();
                            Date currentdate = new Date();
                            cal.setTime(currentdate);
                            int week = cal.get(Calendar.WEEK_OF_YEAR);
                            if (week % 2 == 0) {
                                parityOfWeek = "Ч";
                            } else
                                parityOfWeek = "НЧ";
                        }else {
                            if(chetnost.equals("Четный"))
                                parityOfWeek = "Ч";
                            else
                                parityOfWeek = "НЧ";
                        }
                                System.out.println("asddddddddddddddddddddddd" + parityOfWeek);

                        descriptionOfItemInLists = (ArrayList<DescriptionOfItemInList>) mapper.readValue(object.toString(), new TypeReference<ArrayList<DescriptionOfItemInList>>() {
                                })
                                .stream()
                                .filter(description -> description.getParityOfWeek().equals(parityOfWeek))
                                .collect(Collectors.toList());
                        System.out.println(descriptionOfItemInLists);
                        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), descriptionOfItemInLists);
                        ViewPager viewPager = binding.viewPager;
                        viewPager.setAdapter(sectionsPagerAdapter);
                        TabLayout tabs = binding.tabs;
                        tabs.setupWithViewPager(viewPager);
                        TabLayout.Tab tab = tabs.getTabAt(position);
                        assert tab != null;
                        tab.select();

//                        System.out.println(descriptionOfItemInLists);
                    } catch (JSONException | JsonProcessingException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static String EncodingToUTF8(String response) {
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
}