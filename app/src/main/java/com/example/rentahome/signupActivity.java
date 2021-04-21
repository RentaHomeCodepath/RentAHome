package com.example.rentahome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        EditText et_FirstName = findViewById(R.id.et_firstName_signup);
        EditText et_LastName = findViewById(R.id.et_lastName_signup);
        EditText et_Email = findViewById(R.id.et_Email_signup);
        EditText et_username = findViewById(R.id.et_username_signup);
        EditText et_password = findViewById(R.id.et_password_signup);
        Button btnsignup = findViewById(R.id.btnCreate_signup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ln = et_LastName.getText().toString();
                String fn =  et_FirstName.getText().toString();
                String email = et_Email.getText().toString();
                String password = et_password.getText().toString();
                String username = et_username.getText().toString();
                createuser(username, password,email, fn, ln);
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createuser(String username, String password, String email, String fn, String ln ){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.put("FirstName",fn);
        user.put("LastName",ln);
        // Set custom properties
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(signupActivity.this, "Successful account creation", Toast.LENGTH_SHORT);
                    goMainActivity();
                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(signupActivity.this, "Unsuccessful account creation", Toast.LENGTH_SHORT);
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
