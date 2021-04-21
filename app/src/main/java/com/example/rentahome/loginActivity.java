package com.example.rentahome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class loginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        EditText et_Username = findViewById(R.id.etUsername_login);
        EditText et_password = findViewById(R.id.etPassword_login);
        Button btn_login = findViewById(R.id.btnLogin_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(et_Username.getText().toString(),et_password.getText().toString());
            }
        });
    }
    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void login(String username, String password){

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Toast.makeText(loginActivity.this, "Login Failed",Toast.LENGTH_SHORT);
                    return;
                }
                goMainActivity();
                Toast.makeText(loginActivity.this, "Success!",Toast.LENGTH_SHORT);
            }
        });
    }

}
