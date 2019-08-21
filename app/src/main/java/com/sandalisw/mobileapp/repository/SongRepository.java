package com.sandalisw.mobileapp.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.requests.RequestApiClient;
import com.sandalisw.mobileapp.requests.responses.TopSongsResponse;

import java.io.IOException;
import java.util.List;

public class SongRepository {
    private RequestApiClient mApiClient;
    private static SongRepository instance;

    private SongRepository(){
        mApiClient = RequestApiClient.getInstance();
    }

    public static SongRepository getInstance(){
        if(instance == null){
            instance =  new SongRepository();
        }
        return instance;
    }

    public LiveData<TopSongsResponse> getSongs(){
        return mApiClient.getSongs();
    }

    public LiveData<Boolean> registerUser(Context context, User user)  {
        return  mApiClient.registerUser(context,user);
    }

    public LiveData<List<Artist>> getArtists() {
        return mApiClient.getArtists();
    }

    public LiveData<List<Song>> searchSongs(String s) {
        return mApiClient.searchSongs(s);
    }
}
