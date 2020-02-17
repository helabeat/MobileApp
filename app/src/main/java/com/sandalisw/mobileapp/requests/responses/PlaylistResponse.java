package com.sandalisw.mobileapp.requests.responses;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandalisw.mobileapp.models.Song;

import java.util.List;

public class PlaylistResponse {
    private static final String TAG = "PlaylistResponse";
//    @SerializedName("count")
//    @Expose()
//    private int count;

    @SerializedName("suggestions")
    @Expose()
    private List<Song> mSongs;

//    public int getCount() {
//        return count;
//    }

    public List<Song> getSongs() {
        Log.d(TAG, "ResponseSongs: "+mSongs.size());
        return mSongs;
    }

    @Override
    public String toString() {
        return "PlaylistResponse{" +
                ", songs =" + mSongs +
                '}';
    }
}
