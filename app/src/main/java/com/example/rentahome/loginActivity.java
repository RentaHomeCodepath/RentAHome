package com.example.rentahome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        EditText et_Username_Email = findViewById(R.id.etUsername_login);
        EditText et_password = findViewById(R.id.etPassword_login);
        Button btn_login = findViewById(R.id.btnLogin_login);
    }
    private void login(){

    }

}
