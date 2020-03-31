package com.appcreator.isa.theteacherassistantapp;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.appcreator.isa.theteacherassistantapp.Model.Student;
import com.appcreator.isa.theteacherassistantapp.Database.StudentOperations;

import java.util.List;

public class ViewAllStudents extends ListActivity
{
    private StudentOperations studentOps;
    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_students);

        studentOps = new StudentOperations(this);
        studentOps.open();
        students = studentOps.getAllStudents();
        studentOps.close();
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(this,R.layout.simple_list_item_1, students);
        setListAdapter(adapter);
        //getListView().setCacheColorHint(Color.rgb(255, 255, 255));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_return, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_cancel:
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        actionCancel();
    }

    private void actionCancel()
    {
        finish();
    }

}
