package com.example.kursovayadada.user.schedule.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.kursovayadada.R;
import com.example.kursovayadada.user.schedule.CustomAdapter2;
import com.example.kursovayadada.user.schedule.DescriptionOfItemInList;

import java.util.ArrayList;

public class FragmentRaspisanie6 extends Fragment {

    static ArrayList<DescriptionOfItemInList> descriptionOfItemInLists;

    public static FragmentRaspisanie6 newInstance(ArrayList<DescriptionOfItemInList> descriptionOfItem) {
        descriptionOfItemInLists = descriptionOfItem;
        Bundle args = new Bundle();
        FragmentRaspisanie6 fragment = new FragmentRaspisanie6();
        fragment.setArguments(args);
        return fragment;
    }


    public FragmentRaspisanie6() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (descriptionOfItemInLists.size()!=0) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        // получаем экземпляр элемента ListView
        ListView listView = (ListView) view.findViewById(R.id.listRaspisanie);
// используем адаптер данных
        listView.setAdapter(new CustomAdapter2(view.getContext(), descriptionOfItemInLists));
        }else{
            view = inflater.inflate(R.layout.fragment_main_null, container, false);
        }
        return view;
    }




}
