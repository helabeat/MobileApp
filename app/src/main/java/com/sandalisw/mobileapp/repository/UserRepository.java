package com.sandalisw.mobileapp.repository;

import android.arch.lifecycle.LiveData;

import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.requests.UserRequest;

import java.util.List;

public class UserRepository {

    private UserRequest mApiClient;
    private static UserRepository instance;

    public UserRepository() {
        mApiClient = UserRequest.getInstance();
    }

    public static UserRepository getInstance(){
        if(instance == null){
            instance =  new UserRepository();
        }
        return instance;
    }

    public LiveData<String> registerUser(User user, List<String> mSelectedArtists, List<String> mSelectedGenres) {
        return mApiClient.registerUser(user,mSelectedArtists,mSelectedGenres);
    }

    public void updateHistory(Song song,String userId){
        mApiClient.updateHistory(song,userId);
    }


}
