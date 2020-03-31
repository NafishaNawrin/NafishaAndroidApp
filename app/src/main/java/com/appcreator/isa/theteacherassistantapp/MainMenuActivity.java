package com.appcreator.isa.theteacherassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity
{
    private Button attendanceButton;
    private Button studentOperationsButton;
    private Button notesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        attendanceButton = (Button)findViewById(R.id.button_attendance);
        studentOperationsButton = (Button)findViewById(R.id.button_student_operations);
        notesButton = (Button)findViewById(R.id.button_take_notes);
    }

    public void goToStudentOps (View view)

    {
        studentOperationsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(i);
            }

        });
    }

    public void goToAttendance(View view)
    {
        attendanceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainMenuActivity.this, ViewAllStudents.class);
                startActivity(i);
            }
        });
    }

    public void goToNotes(View view)
    {
        notesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainMenuActivity.this, NoteMainActivity.class);
                startActivity(i);
            }
        });
    }
}
