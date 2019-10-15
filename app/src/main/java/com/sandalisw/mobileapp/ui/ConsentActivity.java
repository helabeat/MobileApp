package com.sandalisw.mobileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.sandalisw.mobileapp.R;

public class ConsentActivity extends AppCompatActivity {

    private Button button;
    private static final String TAG = "ConsentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        button = (Button) findViewById(R.id.continue_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Agreed");
                Intent nextActivity = new Intent(ConsentActivity.this, SignupActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });

    }

    private void goToHome(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


    public void onAgreement(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        switch (view.getId()) {
            case R.id.agreed:
                if (checked)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
                break;

        }

    }
}
