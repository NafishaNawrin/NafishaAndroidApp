package com.appcreator.isa.theteacherassistantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appcreator.isa.theteacherassistantapp.Model.Student;
import com.appcreator.isa.theteacherassistantapp.Database.StudentOperations;


public class AddUpdateStudent extends AppCompatActivity
{
    private static final String EXTRA_STUDENT_ID = "com.appcreator.SId";
    private static final String EXTRA_ADD_UPDATE = "com.appcreator.add_update";
    private RadioGroup radioGroup;
    private RadioButton trueRadioButton,falseRadioButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText studyEditText;
    private EditText enrolmentEditText;
    private Button addUpdateButton;
    private Student newStudent;
    private Student oldStudent;
    private String mode;
    private long studentID;
    private StudentOperations studentData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_student);
        newStudent = new Student();
        oldStudent = new Student();
        firstNameEditText = (EditText)findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText)findViewById(R.id.edit_text_last_name);
        enrolmentEditText = (EditText)findViewById(R.id.edit_text_enrolmentID);
        radioGroup = (RadioGroup) findViewById(R.id.radio_attendance);
        trueRadioButton = (RadioButton) findViewById(R.id.radio_true);
        falseRadioButton = (RadioButton) findViewById(R.id.radio_false);
        studyEditText = (EditText)findViewById(R.id.edit_text_study);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_employee);
        studentData = new StudentOperations(this);
        studentData.open();
        newStudent.setAttendance("True");


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update"))
        {
            addUpdateButton.setText("Update Student");
            studentID = getIntent().getLongExtra(EXTRA_STUDENT_ID,0);

            initializeStudent(studentID);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // find which radio button is selected
                if (checkedId == R.id.radio_true)
                {
                    newStudent.setAttendance("True");
                    if(mode.equals("Update"))
                    {
                        oldStudent.setAttendance("True");
                    }
                }

                else if (checkedId == R.id.radio_false)
                {
                    newStudent.setAttendance("False");
                    if(mode.equals("Update"))
                    {
                        oldStudent.setAttendance("False");
                    }
                }
            }

        });


        addUpdateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(mode.equals("Add"))
                {
                    newStudent.setFirstname(firstNameEditText.getText().toString());
                    newStudent.setLastname(lastNameEditText.getText().toString());
                    newStudent.setEnrlomentID(enrolmentEditText.getText().toString());
                    newStudent.setStudy(studyEditText.getText().toString());

                    if(firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || enrolmentEditText.getText().toString().isEmpty() || studyEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddUpdateStudent.this, "Field CANNOT be empty !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        studentData.addStudent(newStudent);
                        Toast.makeText(AddUpdateStudent.this, "Student "+ newStudent.getFirstname() + "has been added successfully !", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddUpdateStudent.this,MainActivity.class);
                        startActivity(i);
                    }

                }

                else
                {
                    oldStudent.setFirstname(firstNameEditText.getText().toString());
                    oldStudent.setLastname(lastNameEditText.getText().toString());
                    oldStudent.setEnrlomentID(enrolmentEditText.getText().toString());
                    oldStudent.setStudy(studyEditText.getText().toString());

                    if(firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || enrolmentEditText.getText().toString().isEmpty() || studyEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddUpdateStudent.this, "Field CANNOT be empty !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        studentData.updateStudent(oldStudent);
                        Toast.makeText(AddUpdateStudent.this, "Student "+ oldStudent.getFirstname() + " has been updated successfully !", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddUpdateStudent.this,MainActivity.class);
                        startActivity(i);
                    }
                }

            }
        });


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



    private void initializeStudent(long studentID)
    {
        oldStudent = studentData.getStudent(studentID);
        firstNameEditText.setText(oldStudent.getFirstname());
        lastNameEditText.setText(oldStudent.getLastname());
        enrolmentEditText.setText(oldStudent.getEnrlomentID());
        radioGroup.check(oldStudent.getAttendance().equals("True") ? R.id.radio_true : R.id.radio_false);
        studyEditText.setText(oldStudent.getStudy());
    }

}
