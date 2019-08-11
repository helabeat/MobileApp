package com.sandalisw.mobileapp.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestApiClient {

    private static final String TAG = "RequestApiClient";

    private static RequestApiClient instance;
    private MutableLiveData<List<Song>> mSongs;
    private MutableLiveData<List<Artist>> mArtists;
    private boolean isRegistered;

    private RequestApiClient() {
        this.mSongs =new MutableLiveData<>();
        this.mArtists = new MutableLiveData<>();
    }

    public static RequestApiClient getInstance(){
        if(instance == null){
            instance = new RequestApiClient();
        }
        return instance;
    }

    public LiveData<List<Song>> getSongs(){
        getAllSongs();
        return mSongs;
    }

    public boolean registerUser(final Context context, final User user){
        Log.d(TAG, "registerUser: called");
        registration(context,user);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                registration(context,user);
            }
        }, 5000);
        Log.d(TAG, "registration: "+isRegistered);
        return isRegistered;
    }

    public LiveData<List<Artist>> getArtists(){
        Log.d(TAG, "getArtists: checked");
        artistPreference();
        return mArtists;
    }

    private void artistPreference() {
        Log.d(TAG, "artistPreference: checked");
        Call<List<Artist>> call = ServiceGenerator.getRequestApi().getArtists();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                Log.d(TAG, "onResponse: "+response.code());

                mArtists.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private void getAllSongs(){
        Call<List<Song>> call= ServiceGenerator.getRequestApi().getAllSongs();

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d(TAG, "onResponse: "+response.code());

                mSongs.setValue(response.body());
                Log.d(TAG, "onResponse: "+response.body().isEmpty());

            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private void registration(Context context, final User user) {

        Call<User> call = ServiceGenerator.getRequestApi().registerUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: "+response.code());
                isRegistered = true;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                isRegistered = false;
            }
        });

    }

}
