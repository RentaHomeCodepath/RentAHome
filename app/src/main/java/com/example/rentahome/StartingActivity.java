package com.example.rentahome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.tabs.TabLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class StartingActivity extends AppCompatActivity {
    public static final String TAG = "StartingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);

        TabLayout tabLayout;



        EditText et_Username = findViewById(R.id.etUsername_login);
        EditText et_password = findViewById(R.id.etPassword_login);
        Button btn_login = findViewById(R.id.btnLogin_login);

        Button btnsignup = findViewById(R.id.SignupBtn);
        Button btnGuest = findViewById(R.id.GuestBtn);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(et_Username.getText().toString(), et_password.getText().toString());
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestLogin();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {goSignupActivity();}
        });

    }
    private void goSignupActivity(){
        Intent intent = new Intent(this, signupActivity.class);
        startActivity(intent);
        finish();
    }


    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void guestLogin(){
        Log.i(TAG,"Attempting to login user "+ "Guest");
        ParseUser.logInInBackground("Guest", "Guest", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Issue with login",e);
                    Toast.makeText(StartingActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(StartingActivity.this, "Success!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void login(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(StartingActivity.this, "Login Failed", Toast.LENGTH_SHORT);
                    return;
                }
                goMainActivity();
                Toast.makeText(StartingActivity.this, "Success!", Toast.LENGTH_SHORT);
            }
        });

    }
}
