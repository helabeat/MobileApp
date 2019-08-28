package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.repository.UserRepository;


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

    public void updateHistory(Song song,String userId){ mRepository.updateHistory(song,userId);
    }


}
