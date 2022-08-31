package com.example.kursovayadada.user.schedule;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kursovayadada.User;
import com.example.kursovayadada.databinding.ActivityMainBinding;
import com.example.kursovayadada.databinding.ActivityUserScheduleMainBinding;
import com.example.kursovayadada.user.schedule.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainUserScheduleActivity extends AppCompatActivity {

    private ActivityUserScheduleMainBinding binding;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserScheduleMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        savedInstanceState = getIntent().getExtras();
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("date");

            if (position == -1)
                position = 0;

            if (position == -2)
                position = 1;

            System.err.println("aaaaaaaaaasdasdasd"+position);
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            TabLayout.Tab tab = tabs.getTabAt(position);
            assert tab != null;
            tab.select();
        }

    }
}