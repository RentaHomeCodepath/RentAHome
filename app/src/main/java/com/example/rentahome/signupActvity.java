package com.example.rentahome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class signupActvity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        EditText et_FirstName = findViewById(R.id.et_firstName_signup);
        EditText et_LastName = findViewById(R.id.et_lastName_signup);
        EditText et_Email = findViewById(R.id.et_Email_signup);
        EditText et_Password = findViewById(R.id.et_password_signup);
        Button btnsignup = findViewById(R.id.btnCreate_signup);
    }

    private void createuser(){

    }
}
