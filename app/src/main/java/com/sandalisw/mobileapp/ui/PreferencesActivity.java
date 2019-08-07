package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.ArtistAdapter;
import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;

import java.util.List;


public class PreferencesActivity extends AppCompatActivity implements ArtistAdapter.ArtistSelection {

    private static final String TAG = "PreferencesActivity";

    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private SongViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Check");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        mViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        initRecyclerView();
        subscribeObservers();

        final Button button = findViewById(R.id.button_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Next set of preferences
                //call subscribe again and change the dataset
            }
        });

    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.preferences_recyclerview);
        artistAdapter = new ArtistAdapter(this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(artistAdapter);
    }

    private void subscribeObservers(){
        mViewModel.getArtists().observe(this,new Observer<List<Artist>>(){

            @Override
            public void onChanged(@Nullable List<Artist> mData) {
                //this logic should be changed
                artistAdapter.setDataList(mData);
            }
        });
    }

    @Override
    public void onCardClick(int position) {
        Log.d(TAG, "onCardClick: Called");
    }
}
