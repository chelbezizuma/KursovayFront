package com.example.kursovayadada;

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
import com.example.kursovayadada.admin.AddScheduleActivity;
import com.example.kursovayadada.admin.AdminChangeScheduleActivity;
import com.example.kursovayadada.user.schedule.MainUserScheduleActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserGroupSelectionActivity extends AppCompatActivity {

    TextView groupTextview;
    TextView chetnostTextview4;

    ArrayList<String> groupList;
    ArrayList<String> chetnostList;

    Spinner spinner;

    private String GET_GROUP_URL = "http://10.0.2.2:8080/user/getGroups";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group_selection);

        spinner = new Spinner();
        groupTextview =findViewById(R.id.groupTextview3);
        chetnostTextview4=findViewById(R.id.chetnostTextview4);

        groupList =new ArrayList<>();
        chetnostList =new ArrayList<>();

        chetnostList.add("Четный");
        chetnostList.add("Нечетный");

        getGroupList(GET_GROUP_URL);

        System.out.println("asdasdasd"+groupList);
        groupTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(UserGroupSelectionActivity.this,groupTextview,groupList);
            }
        });

        chetnostTextview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDialogWindow(UserGroupSelectionActivity.this,chetnostTextview4,chetnostList);
            }
        });
    }

    public void buttonOpenSchedule(View view) {
        if (!chetnostTextview4.getText().toString().equals("") && !groupTextview.getText().toString().equals("")) {
            Intent intent = new Intent(this, MainUserScheduleActivity.class);
            intent.putExtra("groupUser", groupTextview.getText().toString());
            intent.putExtra("chetnost", chetnostTextview4.getText().toString());
            startActivity(intent);
        }else
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
    }
    
    
    public void cancel(View view) {
        finish();
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