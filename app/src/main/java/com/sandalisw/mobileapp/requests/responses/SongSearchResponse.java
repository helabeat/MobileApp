package com.sandalisw.mobileapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandalisw.mobileapp.models.Song;

import java.util.List;

public class SongSearchResponse {
    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("songs")
    @Expose()
    private List<Song> mSongs;

    public int getCount() {
        return count;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", songs =" + mSongs +
                '}';
    }
}
