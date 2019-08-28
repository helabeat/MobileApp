package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class GenresActivity extends AppCompatActivity {

    private static final String TAG = "";
    private List<String> mSelectedGenres = new ArrayList<>();
    private UserViewModel mViewModel;
    private SharedPreferences sp;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        final Button button = findViewById(R.id.button_next2);
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        Log.d(TAG, "onCreate: "+mSelectedGenres.isEmpty());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mSelectedGenres.isEmpty()){
                    Toast.makeText(getBaseContext(), "Select at least one!", Toast.LENGTH_LONG).show();
                    return;
                }
                LinearLayout layout1 = findViewById(R.id.selection);
                LinearLayout layout2 = findViewById(R.id.preference_bar);
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                ProgressBar pb = findViewById(R.id.progresbar);
                pb.setVisibility(View.VISIBLE);
                onNext();
                registerUser();
            }
        });
    }

    private void registerUser(){
        mViewModel.registerUser(user).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(!s.equals("0")){
                    sp = getSharedPreferences("User_Data",MODE_PRIVATE);
                    sp.edit().putString("userId",s).apply();
                    sp.edit().putBoolean("loggedIn",true).apply();
                    Log.d(TAG, "onChanged: loggedin"+sp.getBoolean("loggedIn",false));
                    Intent nextActivity = new Intent(GenresActivity.this, MainActivity.class);
                    startActivity(nextActivity);
                    finish();
                }else{
                    sp.edit().putBoolean("loggedIn",false).apply();
                    Log.d(TAG, "onChanged: "+"signup fail");
                    Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onNext(){
        Intent i =getIntent();
        user = (User)i.getSerializableExtra("user");
        user.setGenre_preference(mSelectedGenres);
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.classical:
                if (checked)
                    mSelectedGenres.add("Classical");
                else
                    mSelectedGenres.remove("Classical");
                break;
            case R.id.baila:
                if (checked)
                    mSelectedGenres.add("Baila");
                else
                    mSelectedGenres.remove("Baila");
                break;
            case R.id.cultural:
                if (checked)
                    mSelectedGenres.add("Cultural");
                else
                    mSelectedGenres.remove("Cultural");
                break;
            case R.id.rock:
                if (checked)
                    mSelectedGenres.add("Rock");
                else
                    mSelectedGenres.remove("Rock");
                break;
            case R.id.pop:
                if (checked)
                    mSelectedGenres.add("Pop");
                else
                    mSelectedGenres.remove("Pop");
                break;
            case R.id.hiphop:
                if (checked)
                    mSelectedGenres.add("Hiphop");
                else
                    mSelectedGenres.remove("Hiphop");
                break;
            case R.id.rap:
                if (checked)
                    mSelectedGenres.add("Rap");
                else
                    mSelectedGenres.remove("Rap");
                break;
            case R.id.symphony:
                if (checked)
                    mSelectedGenres.add("Symphony");
                else
                    mSelectedGenres.remove("Symphony");
                break;
            case R.id.alternative:
                if (checked)
                    mSelectedGenres.add("Alternative");
                else
                    mSelectedGenres.remove("Alternative");
                break;

        }
    }

}
