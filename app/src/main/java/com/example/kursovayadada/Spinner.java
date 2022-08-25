package com.example.kursovayadada;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kursovayadada.admin.AddScheduleActivity;
import com.example.kursovayadada.admin.AddScheduleInformationActivity;

import java.util.List;

public class Spinner {
    Dialog dialog;
    public void showDialogWindow(Object activityName, TextView textview, List<String> list) {
        // Initialize dialog
        dialog=new Dialog((Context) activityName);

        // set custom dialog
        dialog.setContentView(R.layout.dialog_searchable_spinner);

        // set custom height and width
        dialog.getWindow().setLayout(650,800);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        dialog.show();

        // Initialize and assign variable
        EditText editText=dialog.findViewById(R.id.edit_text);
        ListView listView=dialog.findViewById(R.id.list_view);

        // Initialize array adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<>((Context) activityName, android.R.layout.simple_list_item_1, list);

        // set adapter
        listView.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when item selected from list
                // set selected item on textView
                textview.setText(adapter.getItem(position));

                // Dismiss dialog
                dialog.dismiss();
            }
        });
    }
}
