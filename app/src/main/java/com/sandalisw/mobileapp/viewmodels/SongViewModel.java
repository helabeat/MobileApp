package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.repository.SongRepository;

import java.util.List;

public class SongViewModel extends ViewModel {

    private SongRepository mRepository;

    public SongViewModel() {
        mRepository = SongRepository.getInstance();
    }

    public LiveData<List<Song>> getSongs(){
        return mRepository.getSongs();
    }

    public LiveData<List<Artist>> getArtists(){
        return mRepository.getArtists();
    }


}
