package com.example.kursovayadada.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.kursovayadada.R;
import com.example.kursovayadada.user.schedule.MainUserScheduleActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    public void buttonScheduleCertainDay(View view) throws ParseException {
//        String userInputDateString = showCalendarDialog();
//        System.err.println("userInputDateString"+userInputDateString);
        Intent intent = new Intent(this, DateActivity.class);
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        Date date;
//        date = ft.parse(userInputDateString);
//        intent.putExtra("date", Math.abs((getDayNumberOld(date) - 2) % 7));
        startActivity(intent);
    }

    Date date;
    public void buttonScheduleToday(View view) {
        Intent intent = new Intent(this, MainUserScheduleActivity.class);
        date = new Date();
//        intent.putExtra("date", Math.abs((getDayNumberOld(date) - 2) % 7));
        intent.putExtra("date", getDayNumberOld(date)-2);
        startActivity(intent);
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}