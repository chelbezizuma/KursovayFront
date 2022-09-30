package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kursovayadada.AdminChangingInformationInScheduleActivity;
import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.models.Shedules;
import com.example.kursovayadada.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminChangeScheduleActivity extends AppCompatActivity {

    TextView groupTextview2;
    TextView timeTextview2;
    TextView dayTextview2;
    TextView spinnerChetnost;
    TextView chetnostTextview2;


    User user;
    ArrayList<String> groupList;
    ArrayList<String> dayList;
    ArrayList<String> timeList;
    ArrayList<String> chetnostList;
    private List<Shedules> shedulesList = new ArrayList<>();

    private String GET_TITLE_URL = "http://10.0.2.2:8080/times/";
    private String GET_DAY_OF_WEEKS_URL = "http://10.0.2.2:8080/dayOfWeeks/";
    private String GET_GROUP_URL = "http://10.0.2.2:8080/user/getGroups";

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_schedule);

        spinner = new Spinner();
        groupList =new ArrayList<>();
        dayList =new ArrayList<>();
        timeList =new ArrayList<>();
        chetnostList =new ArrayList<>();
        chetnostList.add("Четный");
        chetnostList.add("Нечетный");

        groupTextview2 = findViewById(R.id.groupTextview2);
        timeTextview2 = findViewById(R.id.timeTextview2);
        dayTextview2 = findViewById(R.id.dayTextview2);
        spinnerChetnost = findViewById(R.id.chetnostTextview2);

        getTimeList(GET_TITLE_URL);
        getGroupList(GET_GROUP_URL);
        getDayList(GET_DAY_OF_WEEKS_URL);


        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            user = (User) savedInstanceState.getSerializable("user");
        }

        groupTextview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AdminChangeScheduleActivity.this, groupTextview2,groupList);
            }
        });

        dayTextview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AdminChangeScheduleActivity.this,dayTextview2,dayList);
            }
        });

        timeTextview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AdminChangeScheduleActivity.this,timeTextview2,timeList);
            }
        });

        spinnerChetnost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(AdminChangeScheduleActivity.this,spinnerChetnost,chetnostList);
            }
        });
    }

    public void buttonExitMenu(View view) {
        finish();
    }

    boolean findDataInTablesSchedule;
    Long idSchedule;
    public void checkingForAvailability() {
        System.out.println(dayTextview2.getText().toString());
        System.out.println(groupTextview2.getText().toString());
        System.out.println(timeTextview2.getText().toString());
        System.out.println(spinnerChetnost.getText().toString());
        if(
                !dayTextview2.getText().toString().equals("")&&
                !groupTextview2.getText().toString().equals("")&&
                !timeTextview2.getText().toString().equals("")&&
                !spinnerChetnost.getText().toString().equals("")
        ){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://10.0.2.2:8080/schedules/" ,
                    response -> {
                        try {
                            JSONArray object = new JSONArray (EncodingToUTF8(response));
                            ObjectMapper mapper = new ObjectMapper();
                            shedulesList = mapper.readValue(object.toString(),  new TypeReference<ArrayList<Shedules>>(){});
                            for (int i = 0; i < shedulesList.size(); i++) {
                                System.out.println(shedulesList.get(i).toString());
                                if (shedulesList.get(i).getDayOfWeek().equals(dayTextview2.getText().toString())&&
                                        shedulesList.get(i).getGroups().equals(groupTextview2.getText().toString()) &&
                                        shedulesList.get(i).getTime().equals(timeTextview2.getText().toString())
//                                        && shedulesList.get(i).getDayOfWeek().equals(spinnerChetnost.getText().toString())
                                ) {
                                    findDataInTablesSchedule = true;
                                    idSchedule = shedulesList.get(i).getId();
                                    break;
                                }else {
                                    findDataInTablesSchedule = false;
                                }
                                    System.out.println(shedulesList.get(i).toString());
                            }
                            System.out.println("timeList"+timeList);
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

            if (findDataInTablesSchedule) {
                Intent intent = new Intent(this, AdminChangingInformationInScheduleActivity.class);
                intent.putExtra("idSchedule", idSchedule); //Optional parameters
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Нет расписания по этим данным!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля!",Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonChange(View view) {
        checkingForAvailability();
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

    private void  getDayList(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray (EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode[] node = mapper.readValue(object.toString(),  ObjectNode[].class);
                        for (int i = 0; i < node.length; i++) {
                            dayList.add(node[i].get("name").toString().replace("\"",""));
                        }
                        System.out.println("dayList"+dayList);
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

    private void  getTimeList(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray (EncodingToUTF8(response));
//                        object.getString(1);
//                        System.out.println(object.getString(1));
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode[] node = mapper.readValue(object.toString(),  ObjectNode[].class);
                        for (int i = 0; i < node.length; i++) {
                            timeList.add(node[i].get("name").toString().replace("\"",""));
                        }
                        System.out.println("timeList"+timeList);
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
}