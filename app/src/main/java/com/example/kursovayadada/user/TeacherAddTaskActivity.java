package com.example.kursovayadada.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.admin.AddScheduleActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class TeacherAddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView textViewStartDeadLine;
    TextView textViewEndDeadLine;
    boolean start = false, end = false;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date firstDate,secondDate,nowDate;

    ArrayList<String> groupList;
    TextView groupTextview;
    private String GET_GROUP_URL = "http://10.0.2.2:8080/user/getGroups";

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_task);

        spinner = new Spinner();
        groupList =new ArrayList<>();

        textViewStartDeadLine = findViewById(R.id.textViewStartDeadLine);
        textViewEndDeadLine = findViewById(R.id.textViewEndDeadLine);
        groupTextview = findViewById(R.id.groupTextview4);

        getGroupList(GET_GROUP_URL);

        groupTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(TeacherAddTaskActivity.this,groupTextview,groupList);
            }
        });
    }


    public void startDeadLine(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Выбрать начало срока");
        start = true;
    }

    public void endDeadLine(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Выбрать конец срока");
        end = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SneakyThrows
    public void addTakSubject(View view) {
//        firstDate = sdf.parse("06/24/2017");
//        mmm/dd/yyyy
        secondDate = sdf.parse("10/01/2022");
        nowDate = new Date();
        System.out.println(secondDate);
        System.out.println(nowDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        long diffInMillies = Math.abs(secondDate.getTime() - nowDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.err.println(diff);
    }

    public void exit(View view) {
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        int day = c.get(Calendar.DAY_OF_WEEK);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL, new Locale("ru", "RU")).format(c.getTime());

        if (start) {
            textViewStartDeadLine.setText(currentDateString);
            start = false;
        }

        if (end) {
            textViewEndDeadLine.setText(currentDateString);
            end = false;
        }
    }

    private void  getGroupList(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray (EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        groupList = mapper.readValue(object.toString(),  new TypeReference<ArrayList<String>>(){});
                        groupList.removeAll(Arrays.asList("-1", "0"));
                    }catch (JSONException | JsonProcessingException e){
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public  static  String EncodingToUTF8(String response){
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        return response;
    }
}