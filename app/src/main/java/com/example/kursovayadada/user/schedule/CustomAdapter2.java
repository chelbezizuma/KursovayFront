package com.example.kursovayadada.user.schedule;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kursovayadada.R;

import java.util.List;

public class CustomAdapter2 implements ListAdapter {
    List<DescriptionOfItemInList> arrayList;
    Context context;

    public CustomAdapter2(Context context, List<DescriptionOfItemInList> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        DescriptionOfItemInList descriptionOfItemInList =arrayList.get(position);
        if(view==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.list_item, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView subject, time,teacher;

            subject = view.findViewById(R.id.subject);
            time = view.findViewById(R.id.time);
            teacher = view.findViewById(R.id.teacher);

            subject.setText(descriptionOfItemInList.getSubject());
            time.setText(descriptionOfItemInList.getTime());
            teacher.setText(descriptionOfItemInList.getTeacher());
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
