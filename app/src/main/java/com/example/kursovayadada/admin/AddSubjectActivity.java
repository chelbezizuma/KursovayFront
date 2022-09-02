package com.example.kursovayadada.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursovayadada.R;
import com.example.kursovayadada.Spinner;
import com.example.kursovayadada.dataBase.TeachersDbHelper;

import java.util.ArrayList;

public class AddSubjectActivity extends AppCompatActivity {
    TextView editTextTextNameSubject;
    TextView facultyTextview;
    TextView textViewTeacher;
    ArrayList<String> facultyList;
    ArrayList<String> specialityList;
    ArrayList<String> teacherList;

    TeachersDbHelper teachersDbHelper;

    String requestWithFilterFaculty = "SELECT DISTINCT " + TeachersDbHelper.KEY_FACULTY +
            " FROM  " + TeachersDbHelper.TABLE_TEACHERS + ";";

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_subject);

        teachersDbHelper = new TeachersDbHelper(this);

        editTextTextNameSubject = (TextView) findViewById(R.id.editTextTextNameSubject);
        facultyTextview = (TextView) findViewById(R.id.facultyTextview);
        textViewTeacher = (TextView) findViewById(R.id.TextviewTeacher);

        facultyList = new ArrayList<>();
        specialityList = new ArrayList<>();
        teacherList = new ArrayList<>();

        spinner = new Spinner();

        // set value in array list
//        facultyList.add("ИВМиИТ");
//        facultyList.add("ИТИС");
//        facultyList.add("Чето еще, хз.");
        fillingInListFacultys();

        // set value in array list
        specialityList.add("Прикладная информатика");
        specialityList.add("Прикладная математика");
        specialityList.add("Бизнес информатика");

        // set value in array list
//        teacherList.add("Русичка");
//        teacherList.add("Асхатов");
//        teacherList.add("Матренина");
        facultyTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTeacher.setText("");
                spinner.showDialogWindow(AddSubjectActivity.this, facultyTextview, facultyList);
            }
        });

        textViewTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillingInListTeachers();
                spinner.showDialogWindow(AddSubjectActivity.this, textViewTeacher, teacherList);
            }
        });
    }

    public void buttonSaveSubject(View view) {
        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();

        if (
                !textViewTeacher.getText().toString().equals("") &&
                        !facultyTextview.getText().toString().equals("") &&
                        !editTextTextNameSubject.getText().toString().equals("")
        ) {
            Cursor cursor = database.query(TeachersDbHelper.TABLE_TEACHERS, null, null, null, null, null, null);
            int idTeacher = -1;
            boolean findDataInTablesSchedule;
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_ID);
                int teacherIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_NAME);
                int facultyIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_FACULTY);
                int subjectIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_SUBJECT);
                do {
                    if (
                            cursor.getString(teacherIndex).equals(textViewTeacher.getText().toString()) &&
                            cursor.getString(subjectIndex).equals(editTextTextNameSubject.getText().toString()) &&
                            cursor.getString(facultyIndex).equals(facultyTextview.getText().toString())
                    ) {
                        findDataInTablesSchedule = true;
                        idTeacher = cursor.getInt(idIndex);
                        break;
                    } else {
                        findDataInTablesSchedule = false;
                    }
                    Log.d("mLog", "ID" + cursor.getInt(idIndex) +
                            ", facultyIndex" + cursor.getString(facultyIndex) +
                            ", subjectIndex" + cursor.getString(subjectIndex) +
                            ", teacherIndex" + cursor.getString(teacherIndex)
                    );
                } while (cursor.moveToNext());

                contentValues1.put(TeachersDbHelper.KEY_NAME, textViewTeacher.getText().toString());
                contentValues1.put(TeachersDbHelper.KEY_FACULTY, facultyTextview.getText().toString());
                contentValues1.put(TeachersDbHelper.KEY_SUBJECT, editTextTextNameSubject.getText().toString());
//                database.insert(TeachersDbHelper.TABLE_TEACHERS, null, contentValues1);
                if (findDataInTablesSchedule) {
                    database.update(TeachersDbHelper.TABLE_TEACHERS, contentValues1, TeachersDbHelper.KEY_ID + "=" + idTeacher, null);
                    Toast.makeText(this, "Данные в  таблице обновлены", Toast.LENGTH_SHORT).show();
                } else {
                    database.insert(TeachersDbHelper.TABLE_TEACHERS, null, contentValues1);
                    Toast.makeText(this, "Данные добавлены в таблицу", Toast.LENGTH_SHORT).show();
                }
//                    database.delete(TeachersDbHelper.TABLE_TEACHERS,TeachersDbHelper.KEY_ID +"="+7,null);
            } else {
                Log.d("mLog", "0 rows в таблице с учителем и перподавателем");
            }
        } else {
            Toast.makeText(this, "Заполните все поля для сохранения!", Toast.LENGTH_SHORT).show();
        }

    }

    private void fillingInListFacultys() {
        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(requestWithFilterFaculty, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_ID);
            int facultysIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_FACULTY);
            do {
                facultyList.add(cursor.getString(facultysIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows  в таблице в днями недели");
        }
        System.out.println(facultyList);
    }

    private void fillingInListTeachers() {
        SQLiteDatabase database = teachersDbHelper.getWritableDatabase();
        teacherList = new ArrayList<>();
        String requestWithFilterTeachers = "SELECT " + TeachersDbHelper.KEY_FACULTY + ", " + TeachersDbHelper.KEY_NAME +
                " FROM  " + TeachersDbHelper.TABLE_TEACHERS +
                " WHERE " + TeachersDbHelper.KEY_FACULTY + " = '" + facultyTextview.getText() + "' ;";

        Cursor cursor = database.rawQuery(requestWithFilterTeachers, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_ID);
            int teachersIndex = cursor.getColumnIndex(TeachersDbHelper.KEY_NAME);
            do {
//                System.out.println(cursor.getString(teachersIndex));
                teacherList.add(cursor.getString(teachersIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows  в таблице с именами учителей");
        }
        System.out.println(teacherList);
    }
}