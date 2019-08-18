package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.repository.SongRepository;

import java.util.List;

public class SongViewModel extends ViewModel {
    private static final String TAG = "SongViewModel";

    private SongRepository mRepository;

    public SongViewModel() {
        mRepository = SongRepository.getInstance();
    }

    public LiveData<List<Song>> getSongs(){
        return mRepository.getSongs();
    }

    public LiveData<List<Song>> searchSong(String s){
        Log.d(TAG, "searchSong: Called in viewmodel");
        return mRepository.searchSongs(s);
    }

    public LiveData<List<Artist>> getArtists(){
        return mRepository.getArtists();
    }


}
