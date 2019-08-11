package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.repository.SongRepository;

import static android.support.constraint.Constraints.TAG;


public class UserViewModel extends ViewModel {
    private SongRepository mRepository;

    public UserViewModel() {
        mRepository = SongRepository.getInstance();
    }

    public boolean registerUser(Context context,User user){
        Log.d(TAG, "registerUser: "+user.toString());
        return mRepository.registerUser(context,user);
    }


}
