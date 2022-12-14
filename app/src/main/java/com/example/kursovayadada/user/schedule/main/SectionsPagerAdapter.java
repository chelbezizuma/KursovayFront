package com.example.kursovayadada.user.schedule.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kursovayadada.dataBase.SchedulesDbHelper;
import com.example.kursovayadada.user.schedule.DescriptionOfItemInList;
import com.example.kursovayadada.R;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie1;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie2;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie3;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie4;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie5;
import com.example.kursovayadada.user.schedule.main.fragment.FragmentRaspisanie6;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    SchedulesDbHelper schedulesDbHelper;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{
              R.string.tab_text_1
            , R.string.tab_text_2
            , R.string.tab_text_3
            , R.string.tab_text_4
            , R.string.tab_text_5
            , R.string.tab_text_6
    };
    private final Context mContext;

    ArrayList<DescriptionOfItemInList> descriptionOfItemInLists;

    public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<DescriptionOfItemInList> descriptionOfItemInLists) {
        super(fm);
        mContext = context;
        System.out.println(descriptionOfItemInLists);
        this.descriptionOfItemInLists = descriptionOfItemInLists;
    }
//    private void setInitialData(){
//        descriptionOfItemInLists = new ArrayList<>();
//        descriptionOfItemInLists.add(new DescriptionOfItemInList("??????????????????", "??????????????", "10:00-11:40","????"));
//        descriptionOfItemInLists.add(new DescriptionOfItemInList("??????????????", "??????????", "11:50-13:20","????"));
//        descriptionOfItemInLists.add(new DescriptionOfItemInList("??????????????", "??????????????????????????????","19:30-21:00","????")); ;
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        setInitialData();
        for (int i = 0; i < descriptionOfItemInLists.size(); i++) {
            System.out.println("aaaaaaaaaa"+descriptionOfItemInLists.get(i).getGroups());
            System.out.println("aaaaaaaaaa"+descriptionOfItemInLists.get(i).getDayOfWeek());
            System.out.println("aaaaaaaaaa"+descriptionOfItemInLists.get(i).getTeacher());
            System.out.println("aaaaaaaaaa"+descriptionOfItemInLists.get(i).getDayOfWeek());
        }
        switch(position){
            case 0:
                return FragmentRaspisanie1.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????????????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));

            case 1:
                return FragmentRaspisanie2.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case 2:
                return FragmentRaspisanie3.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case 3:
                return FragmentRaspisanie4.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case 4:
                return FragmentRaspisanie5.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case 5:
                return FragmentRaspisanie6.newInstance(descriptionOfItemInLists
                        .stream()
                        .filter(subjectList -> "??????????????".equals(subjectList.getDayOfWeek()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}