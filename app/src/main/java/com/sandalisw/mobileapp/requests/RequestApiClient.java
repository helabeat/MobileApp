package com.sandalisw.mobileapp.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;


import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.requests.responses.TopSongsResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestApiClient {

    private static final String TAG = "RequestApiClient";

    private static RequestApiClient instance;
    private MutableLiveData<TopSongsResponse> mSongs;
    private MutableLiveData<List<Artist>> mArtists;
    private MutableLiveData<List<Song>> mSearchResults;
    private MutableLiveData<Boolean> isRegistered;

    private RequestApiClient() {
        this.mSongs =new MutableLiveData<>();
        this.mArtists = new MutableLiveData<>();
        this.mSearchResults = new MutableLiveData<>();
        this.isRegistered = new MutableLiveData<>();
    }

    public static RequestApiClient getInstance(){
        if(instance == null){
            instance = new RequestApiClient();
        }
        return instance;
    }

    public LiveData<TopSongsResponse> getSongs(){
        getAllSongs();
        return mSongs;
    }

    public LiveData<Boolean> registerUser(final Context context, final User user){
        Log.d(TAG, "registerUser: called");
        registration(context,user);
        Log.d(TAG, "registration: "+isRegistered);
        return isRegistered;
    }

    public LiveData<List<Artist>> getArtists(){
        Log.d(TAG, "getArtists: checked");
        artistPreference();
        return mArtists;
    }

    public LiveData<List<Song>> searchSongs(String query){
        Log.d(TAG, "searchSongs: called");
        getSearchedSongs(query);
        return mSearchResults;
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
        Call<TopSongsResponse> call= ServiceGenerator.getRequestApi().getAllSongs();

        call.enqueue(new Callback<TopSongsResponse>() {
            @Override
            public void onResponse(Call<TopSongsResponse> call, Response<TopSongsResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().getRecent_songs().size());

                mSongs.setValue(response.body());
                Log.d(TAG, "onResponse: "+response.body().toString());

            }

            @Override
            public void onFailure(Call<TopSongsResponse> call, Throwable t) {
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
                isRegistered.setValue(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                isRegistered.setValue(false);
            }
        });
//        return isRegistered;
    }

    private void getSearchedSongs(String query){
        Log.d(TAG, "searchSongs: called");
        Call<List<Song>> call = ServiceGenerator.getRequestApi().searchSongs(query);

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d(TAG, "run: "+response.body().size());
                mSearchResults.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });


    }

}
