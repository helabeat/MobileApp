package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.util.Log;

import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.repository.UserRepository;

import java.util.List;


public class UserViewModel extends ViewModel {
     private UserRepository mRepository;
     private static final String TAG = "UserViewModel";

    public UserViewModel() {
        mRepository = UserRepository.getInstance();

    }

    public LiveData<String> registerUser(User mUser) {
        Log.d(TAG, "registerUser: "+mUser.toString());
        return mRepository.registerUser(mUser,mUser.getArtist_preference(),mUser.getGenre_preference());

    }


}
