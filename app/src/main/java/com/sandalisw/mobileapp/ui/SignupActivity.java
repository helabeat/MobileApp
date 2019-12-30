package com.sandalisw.mobileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.models.User;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText input_username;
    private EditText input_email;
    private EditText input_age;
    private EditText input_password;
    private RadioGroup input_gender;
    private Button input_signup_button;
    private TextView login_link;

    String gender;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        input_username = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_age = (EditText) findViewById(R.id.input_age);
        input_password = (EditText) findViewById(R.id.input_password);
        input_gender = findViewById(R.id.input_gender);
        input_signup_button = findViewById(R.id.btn_signup);
        login_link = findViewById(R.id.link_login);

        input_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Check which radio button was clicked
                switch(checkedId) {
                    case R.id.radio_male:
                        gender = "male";
                        break;
                    case R.id.radio_female:
                        gender = "female";
                        break;
                }
            }
        });

        login_link.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Login feature is not available.Please sign up", Toast.LENGTH_LONG).show();
            }
        });

        input_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });



    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        input_signup_button.setEnabled(false);
        onSignupSuccess();
    }



    public void onSignupSuccess() {
        input_signup_button.setEnabled(true);
        Log.d(TAG, "onSignupSuccess: "+user.toString());

        Intent nextActivity = new Intent(SignupActivity.this, PreferencesActivity.class);
        nextActivity.putExtra("user",user);
        startActivity(nextActivity);
        finish();





    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        input_signup_button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = input_username.getText().toString();
        String email = input_email.getText().toString();
        String age = input_age.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            input_username.setError("at least 3 characters");
            valid = false;
        } else {
            input_username.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_age.setError(null);
        }

        if (age.isEmpty()) {
            input_age.setError("enter your age");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);

        }
        user = new User(name,email,age,password,gender);
        Log.d(TAG, "validate: "+user.toString()+"validation is"+valid);
        return valid;
    }



}
