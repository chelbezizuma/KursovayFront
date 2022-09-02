package com.example.kursovayadada.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.kursovayadada.R;
import com.example.kursovayadada.user.schedule.MainUserScheduleActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    int position;
    String group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        Button button = (Button) findViewById(R.id.button);
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            group = savedInstanceState.getString("groupUser");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Выбрать дату");
            }
        });

    }

    public void buttonOpenShedule(View view) {
//        int day = Math.abs((position - 2) % 7);
        Intent intent = new Intent(this, MainUserScheduleActivity.class);
        intent.putExtra("groupUser", group);
        intent.putExtra("date", position-2);
        startActivity(intent);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        position = c.get(Calendar.DAY_OF_WEEK);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL, new Locale("ru", "RU")).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentDateString);
    }
}