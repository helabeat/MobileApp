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
                    Intent nextActivity = new Intent(GenresActivity.this, MainActivity.class);
                    startActivity(nextActivity);
                    finish();
                    sp = getSharedPreferences("User_Data",MODE_PRIVATE);
                    sp.edit().putString("userId",s).apply();
                    sp.edit().putBoolean("loggedIn",true).apply();
                }else{
                    sp.edit().putBoolean("loggedIn",false).apply();
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
            case R.id.a:
                if (checked)
                    mSelectedGenres.add("Genre 1");
                else
                    mSelectedGenres.remove("Genre 1");
                break;
            case R.id.aa:
                if (checked)
                    mSelectedGenres.add("Genre 12");
                else
                    mSelectedGenres.remove("Genre 1");
                break;
            case R.id.aaa:
                if (checked)
                    mSelectedGenres.add("Genre 1");
                else
                    mSelectedGenres.remove("Genre 1");
                break;
            case R.id.aaaaa:
                if (checked)
                    mSelectedGenres.add("Genre 1");
                else
                    mSelectedGenres.remove("Genre 1");
                break;
            case R.id.aaaaaa:
                if (checked)
                    mSelectedGenres.add("Genre 1");
                else
                    mSelectedGenres.remove("Genre 1");
                break;
            case R.id.aaaaaaa:
                if (checked)
                    mSelectedGenres.add("Genre 1");
                else
                    mSelectedGenres.remove("Genre 1");
                break;

        }
    }

}
