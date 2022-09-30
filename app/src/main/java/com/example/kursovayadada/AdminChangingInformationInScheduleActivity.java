package com.example.kursovayadada;

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
import com.example.kursovayadada.admin.AddScheduleInformationActivity;
import com.example.kursovayadada.dataBase.DayOfWeeksDbHelper;
import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.dataBase.TeachersDbHelper;
import com.example.kursovayadada.dataBase.TimesDbHelper;
import com.example.kursovayadada.models.Shedules;
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

public class AdminChangingInformationInScheduleActivity extends AppCompatActivity {

    // Initialize variable
    TextView textViewGroup;
    TextView dateTextview;
    TextView timeTextview;
    TextView subjectTextview;
    TextView teacherTextview;
    ArrayList<String> subjectList;
    ArrayList<String> teacherList;
    String group;
    String date;
    long idSchedule;

    Spinner spinner;

    TeachersDbHelper teachersDbHelper;
    TimesDbHelper timesDbHelper;
    DayOfWeeksDbHelper dayOfWeeksDbHelper;
    SchedulesDbHelper schedulesDbHelper;

    private String GET_SUBJECT_FIND_BY_TEACHER_URL = "http://10.0.2.2:8080/teacher/subject";
    private String GET_SUBJECT_FROM_TEACHER_URL = "http://10.0.2.2:8080/teacher/teacherSubject?subject=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_changing_information_in_schedule);

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
        subjectTextview = findViewById(R.id.subjectTextview2);
        teacherTextview = findViewById(R.id.teacherTextview3);

        // initialize array list
        subjectList = new ArrayList<>();
        teacherList = new ArrayList<>();

        fillingInListSubjects();
//        fillingInListTeachers();


        subjectTextview.setOnClickListener(v -> {
            spinner.showDialogWindow(AdminChangingInformationInScheduleActivity.this, subjectTextview, subjectList);
            fillingInListTeachers();
        });

        teacherTextview.setOnClickListener(v -> {
//            fillingInListTeachers();
            spinner.showDialogWindow(AdminChangingInformationInScheduleActivity.this, teacherTextview, teacherList);
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

    public void buttonSaveSchedule(View view) {
        if (!dateTextview.getText().toString().equals("") &&
                !textViewGroup.getText().toString().equals("") &&
                !timeTextview.getText().toString().equals("") &&
                !subjectTextview.getText().toString().equals("") &&
                !teacherTextview.getText().toString().equals("")) {
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
        } else
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
    }

    private void getTeacherList(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(EncodingToUTF8(response));
                        ObjectMapper mapper = new ObjectMapper();
                        teacherList = mapper.readValue(object.toString(), new TypeReference<ArrayList<String>>() {
                        });
                        System.out.println("teacherList" + teacherList);
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

                        ObjectMapper mapper = new ObjectMapper();
                        subjectList = mapper.readValue(object.toString(), new TypeReference<ArrayList<String>>() {
                        });
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

    private void fillingInListSubjects() {
        getSchedulesList(GET_SUBJECT_FIND_BY_TEACHER_URL);
    }

    private void fillingInListTeachers() {
        getTeacherList(GET_SUBJECT_FROM_TEACHER_URL + subjectTextview.getText());
        System.out.println(teacherList);
    }
}