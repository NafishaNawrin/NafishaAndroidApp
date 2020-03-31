package com.appcreator.isa.theteacherassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherLogin extends AppCompatActivity
{
    public EditText usernameEditText;
    public EditText passwordEditText;

    public Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        usernameEditText = (EditText)findViewById(R.id.edit_text_username);
        passwordEditText = (EditText)findViewById(R.id.edit_text_password);

        login = (Button)findViewById(R.id.button_loginAdmin);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (usernameEditText.getText().toString().equals("Teacher") && passwordEditText.getText().toString().equals("Teacher123"))
                {
                    Intent i = new Intent(TeacherLogin.this , MainMenuActivity.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(TeacherLogin.this, "Incorrect Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
