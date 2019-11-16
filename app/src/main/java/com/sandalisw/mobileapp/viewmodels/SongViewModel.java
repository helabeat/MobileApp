package com.sandalisw.mobileapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.repository.SongRepository;
import com.sandalisw.mobileapp.requests.responses.TopSongsResponse;

import java.util.List;

public class SongViewModel extends ViewModel {
    private static final String TAG = "SongViewModel";
    private MutableLiveData<MediaMetadataCompat> currentMedia = new MutableLiveData<MediaMetadataCompat>();
    private MutableLiveData<List<MediaMetadataCompat>> mplaylist = new MutableLiveData<>();

    private SongRepository mRepository;

    public SongViewModel() {
        mRepository = SongRepository.getInstance();
    }

    public LiveData<TopSongsResponse> getSongs(){
        return mRepository.getSongs();
    }

    public LiveData<List<Song>> searchSong(String s){
        return mRepository.searchSongs(s);
    }

    public  LiveData<List<MediaMetadataCompat>> getPlaylist(){
        Log.d(TAG, "getPlaylist: ");
        return mplaylist;
    }

    public void setPlaylist(List<MediaMetadataCompat> data){
        Log.d(TAG, "setPlaylist: ");
        mplaylist.setValue(data);
    }

    public LiveData<List<Artist>> getArtists(){
        return mRepository.getArtists();
    }

    public void setCurrentMedia(MediaMetadataCompat mData){
        currentMedia.setValue(mData);

    }

    public MutableLiveData<MediaMetadataCompat> getCurrentMedia(){
        return  currentMedia;
    }

}
