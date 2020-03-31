package com.appcreator.isa.theteacherassistantapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.appcreator.isa.theteacherassistantapp.Database.StudentDatabaseHandler;
import com.appcreator.isa.theteacherassistantapp.Database.StudentOperations;
import com.appcreator.isa.theteacherassistantapp.Model.Student;

public class MainActivity extends AppCompatActivity
{

    private Button addStudentButton;
    private Button editStudentButton;
    private Button deleteStudentButton;
    //private Button viewAllStudentButton;
    private StudentOperations studentOps;
    private static final String EXTRA_STUDENT_ID = "com.appcreator.SId";
    private static final String EXTRA_ADD_UPDATE = "com.appcreator.add_update";

    private static final String TAG = "Student Exits";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addStudentButton = (Button) findViewById(R.id.button_add_student);
        editStudentButton = (Button) findViewById(R.id.button_edit_student);
        deleteStudentButton = (Button) findViewById(R.id.button_delete_student);
        //viewAllStudentButton = (Button)findViewById(R.id.button_view_student);



        addStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,AddUpdateStudent.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getStudentIDAndUpdateStudent();
            }
        });
        deleteStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getStudentIDAndRemoveStudent();
            }
        });
 /*           viewAllStudentButton.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllStudents.class);
                startActivity(i);
            }
        }); */

    }

    public boolean check_existence(String stud_id)
    {
        SQLiteOpenHelper db = new StudentDatabaseHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();

        String select = "SELECT * FROM students WHERE studentID =" + stud_id;

        Cursor c = database.rawQuery(select, null);

        if (c.moveToFirst())
        {
            Log.d(TAG,"Student Exists");
            return true;
        }

        if(c!=null)
        {
            c.close();
        }
        database.close();
        return false;
    }

    public void getStudentIDAndUpdateStudent()
    {

        LayoutInflater li = LayoutInflater.from(this);
        final View getStudentIdView = li.inflate(R.layout.dialog_get_student_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_student_id.xml to alertdialog builder
        alertDialogBuilder.setView(getStudentIdView);

        final EditText userInput = (EditText) getStudentIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        if (userInput.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                                // get user input and set it to result
                                // edit text
                                if (check_existence(userInput.getText().toString()) == true)
                                {
                                    Intent i = new Intent(MainActivity.this,AddUpdateStudent.class);
                                    i.putExtra(EXTRA_ADD_UPDATE, "Update");
                                    i.putExtra(EXTRA_STUDENT_ID, Long.parseLong(userInput.getText().toString()));
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
                }).create()
                .show();
    }


    public void getStudentIDAndRemoveStudent()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View getStudentIdView = li.inflate(R.layout.dialog_get_student_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_student_id.xml to alertdialog builder
        alertDialogBuilder.setView(getStudentIdView);

        final EditText userInput = (EditText) getStudentIdView.findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        if (userInput.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(check_existence(userInput.getText().toString()) == true)
                            {
                                // get user input and set it to result
                                // edit text
                                //studentOps = new StudentOperations(MainActivity.this); disabled because placing it here causes error
                                studentOps.removeStudent(studentOps.getStudent(Long.parseLong(userInput.getText().toString())));
                                Toast.makeText(MainActivity.this, "Student has been removed successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Invalid Input" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).create()
                .show();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        studentOps = new StudentOperations(MainActivity.this);
        studentOps.open();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        studentOps.close();
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


















