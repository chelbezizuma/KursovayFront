package com.example.kursovayadada.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kursovayadada.R;
import com.example.kursovayadada.user.schedule.MainUserScheduleActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    public void buttonScheduleCertainDay(View view) throws ParseException {
        Intent intent = new Intent(this, MainUserScheduleActivity.class);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String str = "2022-08-29";
//        Date date = ft.parse(str);
        Date date = new Date();
        intent.putExtra("date", Math.abs((getDayNumberOld(date)-2)%7));
        System.out.println("asdasdasdasfas"+Math.abs((getDayNumberOld(date)-2)%7));
        startActivity(intent);
    }

    public void buttonScheduleToday(View view) {
        Intent intent = new Intent(this, MainUserScheduleActivity.class);
        startActivity(intent);
    }
    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println("day" +Calendar.DAY_OF_WEEK);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}