package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.dataBase.TimesDbHelper;
import com.example.kursovayadada.models.Shedules;
import com.example.kursovayadada.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddScheduleInformationActivity extends AppCompatActivity {
    // Initialize variable
    TextView textViewGroup;
    TextView dateTextview;
    TextView timeTextview;
    TextView subjectTextview;
    TextView teacherTextview;
    ArrayList<String> timeList;
    ArrayList<String> subjectList;
    ArrayList<String> teacherList;
    ArrayList<String> dayList;
    String group;
    String date;
    long idSchedule;
    private boolean change;

    Spinner spinner;

    TeachersDbHelper teachersDbHelper;
    TimesDbHelper timesDbHelper;
    DayOfWeeksDbHelper dayOfWeeksDbHelper;
    SchedulesDbHelper schedulesDbHelper;

    private String GET_SUBJECT_FIND_BY_TEACHER_URL = "http://10.0.2.2:8080/teacher/subject";
    private String POST_SUBJECT_URL = "http://10.0.2.2:8080/schedules/postSchedules";
    private String GET_SUBJECT_FROM_TEACHER_URL = "http://10.0.2.2:8080/teacher/teacherSubject?subject=";
    private String GET_TITLE_URL = "http://10.0.2.2:8080/times/";
    private String GET_DAY_OF_WEEKS_URL = "http://10.0.2.2:8080/dayOfWeeks/";

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
            if (savedInstanceState.getSerializable("idSchedule") != null) {
                idSchedule = savedInstanceState.getLong("idSchedule");
                changeSchedule();
            }
        }
        // assign variable
        timeTextview = findViewById(R.id.timeTextview);
        subjectTextview = findViewById(R.id.subjectTextview);
        teacherTextview = findViewById(R.id.teacherTextview);

        // initialize array list
        timeList = new ArrayList<>();
        subjectList = new ArrayList<>();
        teacherList = new ArrayList<>();
        dayList = new ArrayList<>();

        fillingInListDays();
        fillingInListTimes();
        fillingInListSubjects();

        dateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this, dateTextview, dayList);
            }
        });

        timeTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AddScheduleInformationActivity.this, timeTextview, timeList);
            }
        });

        subjectTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillingInListTeachers();
                spinner.showDialogWindow(AddScheduleInformationActivity.this, subjectTextview, subjectList);
            }
        });

        teacherTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillingInListTeachers();
                spinner.showDialogWindow(AddScheduleInformationActivity.this, teacherTextview, teacherList);
            }
        });
    }

    private void changeSchedule() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/schedules/" + idSchedule,
                response -> {
                    try {
                        JSONObject object = new JSONObject(Objects.requireNonNull(EncodingToUTF8(response)));
                        ObjectMapper mapper = new ObjectMapper();
                        Shedules shedules = mapper.readValue(object.toString(), new TypeReference<Shedules>() {
                        });
                        textViewGroup.setText(shedules.getGroups());
                        dateTextview.setText(shedules.getDayOfWeek());
                        timeTextview.setText(shedules.getTime());
                        subjectTextview.setText(shedules.getSubject());
                        teacherTextview.setText(shedules.getTeacher());
                        change = true;
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


    public void selectingAddingOrChangingSchedule() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/schedules/",
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        shedulesList = mapper.readValue(object.toString(), new TypeReference<ArrayList<Shedules>>() {
                        });
                        for (int i = 0; i < shedulesList.size(); i++) {
                            System.out.println(shedulesList.get(i).toString());
//                            timeList.add(shedulesList.get(i).toString().replace("\"",""));
                            if (shedulesList.get(i).getDayOfWeek().equals(dateTextview.getText().toString()) &&
                                    shedulesList.get(i).getGroups().equals(textViewGroup.getText().toString()) &&
                                    shedulesList.get(i).getTime().equals(timeTextview.getText().toString()) &&
                                    shedulesList.get(i).getSubject().equals(subjectTextview.getText().toString()) &&
                                    shedulesList.get(i).getTeacher().equals(teacherTextview.getText().toString())) {
                                findDataInTablesSchedule = true;
                                idSchedule = shedulesList.get(i).getId();
                                break;
                            } else {
                                findDataInTablesSchedule = false;
                            }
                        }
                        System.out.println("timeList" + timeList);
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

    public void post(String apiUrl) {
        RequestQueue requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                response -> Toast.makeText(this, "Success", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("groups", textViewGroup.getText().toString());
                params.put("dayOfWeek", dateTextview.getText().toString());
                params.put("time", timeTextview.getText().toString());
                params.put("subject", subjectTextview.getText().toString());
                params.put("teacher", teacherTextview.getText().toString());
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private List<Shedules> shedulesList = new ArrayList<>();
    boolean findDataInTablesSchedule = false;

    public void buttoSaveSchedule(View view) {
//        post(POST_SUBJECT_URL);
//        SQLiteDatabase database1 = schedulesDbHelper.getWritableDatabase();
        if (!dateTextview.getText().toString().equals("") &&
                !textViewGroup.getText().toString().equals("") &&
                !timeTextview.getText().toString().equals("") &&
                !subjectTextview.getText().toString().equals("") &&
                !teacherTextview.getText().toString().equals("")) {
//
//            Cursor cursor = database1.query(SchedulesDbHelper.TABLE_SCHEDULES, null, null, null, null, null, null);
//            int idSchedule = -1;
//            boolean findDataInTablesSchedule;
//            if (cursor.moveToFirst()) {
//                int idIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_ID);
//                int dayOfWeekIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_DAY_OF_WEEK);
//                int groupIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_GROUP);
//                int timeIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TIME);
//                int subjectIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_SUBJECT);
//                int teacherIndex = cursor.getColumnIndex(SchedulesDbHelper.KEY_TEACHER);
//                do {
            if (!change) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:8080/schedules/",
                        response -> {
                            try {
                                JSONArray object = new JSONArray(EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                                ObjectMapper mapper = new ObjectMapper();
                                shedulesList = mapper.readValue(object.toString(), new TypeReference<ArrayList<Shedules>>() {
                                });
                                for (int i = 0; i < shedulesList.size(); i++) {
                                    System.out.println(shedulesList.get(i).toString());
//                            timeList.add(shedulesList.get(i).toString().replace("\"",""));
                                    if (shedulesList.get(i).getDayOfWeek().equals(dateTextview.getText().toString()) &&
                                            shedulesList.get(i).getGroups().equals(textViewGroup.getText().toString()) &&
                                            shedulesList.get(i).getTime().equals(timeTextview.getText().toString()) &&
                                            shedulesList.get(i).getSubject().equals(subjectTextview.getText().toString()) &&
                                            shedulesList.get(i).getTeacher().equals(teacherTextview.getText().toString())) {
                                        findDataInTablesSchedule = true;
                                        idSchedule = shedulesList.get(i).getId();
                                        break;
                                    } else {
                                        findDataInTablesSchedule = false;
                                    }
                                }
                                System.out.println("timeList" + timeList);
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

//                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
//                            ", dayOfWeekIndex" + cursor.getString(dayOfWeekIndex) +
//                            ", groupIndex" + cursor.getString(groupIndex) +
//                            ", timeIndex" + cursor.getString(timeIndex) +
//                            ", subjectIndex" + cursor.getString(subjectIndex) +
//                            ", teacherIndex" + cursor.getString(teacherIndex)
//                    );
//                } while (cursor.moveToNext());
//                ContentValues contentValues1 = new ContentValues();
//                contentValues1.put(SchedulesDbHelper.KEY_DAY_OF_WEEK, dateTextview.getText().toString());
//                contentValues1.put(SchedulesDbHelper.KEY_GROUP, textViewGroup.getText().toString());
//                contentValues1.put(SchedulesDbHelper.KEY_TIME, timeTextview.getText().toString());
//                contentValues1.put(SchedulesDbHelper.KEY_SUBJECT, subjectTextview.getText().toString());
//                contentValues1.put(SchedulesDbHelper.KEY_TEACHER, teacherTextview.getText().toString());

                if (findDataInTablesSchedule) {
//                    database1.update(SchedulesDbHelper.TABLE_SCHEDULES,contentValues1,SchedulesDbHelper.KEY_ID +"="+idSchedule,null);
                    String url = "http://10.0.2.2:8080/schedules/" + idSchedule;
                    System.out.println(url);
                    StringRequest stringRequestPut = new StringRequest(Request.Method.PUT, url,
                            response -> Toast.makeText(this, "Success", Toast.LENGTH_LONG).show(),
                            error -> Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("groups", textViewGroup.getText().toString());
                            params.put("dayOfWeek", dateTextview.getText().toString());
                            params.put("time", timeTextview.getText().toString());
                            params.put("subject", subjectTextview.getText().toString());
                            params.put("teacher", teacherTextview.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue1 = Volley.newRequestQueue(this);
                    requestQueue1.add(stringRequestPut);
//                    RequestQueue requestQueuePut = Volley.newRequestQueue(this);
//                    requestQueuePut.add(stringRequestPut);

                    Toast.makeText(this, "Данные изменены таблице обновлены", Toast.LENGTH_SHORT).show();
                } else {
                    post(POST_SUBJECT_URL);
                    Toast.makeText(this, "Данные добавлены в таблицу", Toast.LENGTH_SHORT).show();
                }
//            }else {
//                Log.d("mLog", "0 rows в таблице с расписанием");
//            }
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

            } else {
                String url = "http://10.0.2.2:8080/schedules/" + idSchedule;
                System.out.println(url);
                StringRequest stringRequestPut = new StringRequest(Request.Method.PUT, url,
                        response -> Toast.makeText(this, "Success", Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("groups", textViewGroup.getText().toString());
                        params.put("dayOfWeek", dateTextview.getText().toString());
                        params.put("time", timeTextview.getText().toString());
                        params.put("subject", subjectTextview.getText().toString());
                        params.put("teacher", teacherTextview.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue1 = Volley.newRequestQueue(this);
                requestQueue1.add(stringRequestPut);
            }
        } else
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
    }


    private void fillingInListTimes() {
        getTimeList(GET_TITLE_URL);
//        SQLiteDatabase database = timesDbHelper.getWritableDatabase();
//        Cursor cursor = database.query(TimesDbHelper.TABLE_TIME,null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(TimesDbHelper.KEY_ID);
//            int nameIndex = cursor.getColumnIndex(TimesDbHelper.KEY_NAME);
//            do {
//                timeList.add(cursor.getString(nameIndex));
//            } while (cursor.moveToNext());
//        }else {
//            Log.d("mLog", "0 rows  в таблице со временем");
//        }
//        System.out.println(timeList);

    }

    private void getTimeList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode[] node = mapper.readValue(object.toString(), ObjectNode[].class);
                        for (int i = 0; i < node.length; i++) {
                            timeList.add(node[i].get("name").toString().replace("\"", ""));
                        }
                        System.out.println("timeList" + timeList);
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


    private void getDayList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode[] node = mapper.readValue(object.toString(), ObjectNode[].class);
                        for (int i = 0; i < node.length; i++) {
                            dayList.add(node[i].get("name").toString().replace("\"", ""));
                        }
                        System.out.println("dayList" + dayList);
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

    private void getTeacherList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
                        ObjectMapper mapper = new ObjectMapper();
//                        ObjectNode[] node = mapper.readValue(object.toString(),  ObjectNode[].class);
                        teacherList = mapper.readValue(object.toString(), new TypeReference<ArrayList<String>>() {
                        });
//                        for (int i = 0; i < node.length; i++) {
//                            teacherList.add(node[i].get("name").toString().replace("\"",""));
//                        }
                        System.out.println("teacherList" + teacherList);
//                        Toast.makeText(getApplicationContext(),"teacherList"+teacherList,Toast.LENGTH_SHORT).show();
                    } catch (JSONException | JsonProcessingException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getSchedulesList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        subjectList = mapper.readValue(object.toString(), new TypeReference<ArrayList<String>>() {
                        });
//                        for (int i = 0; i < ; i++) {
//
//                        }
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

    private void fillingInListDays() {
        getDayList(GET_DAY_OF_WEEKS_URL);
//        SQLiteDatabase database = dayOfWeeksDbHelper.getWritableDatabase();
//        Cursor cursor = database.query(DayOfWeeksDbHelper.TABLE_DAY_OF_WEEK,null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DayOfWeeksDbHelper.KEY_ID);
//            int nameIndex = cursor.getColumnIndex(DayOfWeeksDbHelper.KEY_NAME);
//            do {
//                dayList.add(cursor.getString(nameIndex));
//            } while (cursor.moveToNext());
//        }else {
//            Log.d("mLog", "0 rows  в таблице в днями недели");
//        }

    }

    private void fillingInListSubjects() {
        getSchedulesList(GET_SUBJECT_FIND_BY_TEACHER_URL);
//        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();
//
//        String requestWithFilterTeachers = "SELECT DISTINCT "+ TeachersDbHelper.KEY_SUBJECT +
//                " FROM  " + TeachersDbHelper.TABLE_TEACHERS +";";
//
//        Cursor cursor = database.rawQuery(requestWithFilterTeachers, null);
//        if (cursor.moveToFirst()) {
//            int subjectsIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_SUBJECT);
//            do {
//                subjectList.add(cursor.getString(subjectsIndex));
//            } while (cursor.moveToNext());
//        }else {
//            Log.d("mLog", "0 rows  в таблице с предметами ");
//        }
//        System.out.println(subjectList);
    }

    private void fillingInListTeachers() {
        getTeacherList(GET_SUBJECT_FROM_TEACHER_URL + subjectTextview.getText());
//        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();
//        teacherList = new ArrayList<>();
//        String requestWithFilterTeachers = "SELECT "+ TeachersDbHelper.KEY_SUBJECT+", "+ TeachersDbHelper.KEY_NAME +
//                " FROM  " + TeachersDbHelper.TABLE_TEACHERS +
//                " WHERE "+TeachersDbHelper.KEY_SUBJECT+" = '"+ subjectTextview.getText() +"' ;";
//
//        Cursor cursor = database.rawQuery(requestWithFilterTeachers, null);
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_ID);
//            int teachersIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_NAME);
//            do {
////                System.out.println(cursor.getString(teachersIndex));
//                teacherList.add(cursor.getString(teachersIndex));
//            } while (cursor.moveToNext());
//        }else {
//            Log.d("mLog", "0 rows  в таблице с именами учителей");
//        }
        System.out.println(teacherList);
    }
}