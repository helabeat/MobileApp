package com.sandalisw.mobileapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandalisw.mobileapp.models.Song;

import java.util.List;

public class TopSongsResponse {
    @SerializedName("recent")
    @Expose()
    private List<Song> recent_songs;

    @SerializedName("classic")
    @Expose()
    private List<Song> old_songs;


    @SerializedName("playlist")
    @Expose()
    private List<Song> playlist_songs;

    @SerializedName("suggestion")
    @Expose()
    private List<Song> suggested_songs;

    public List<Song> getPlaylist_songs() {
        return playlist_songs;
    }

    public void setPlaylist_songs(List<Song> playlist_songs) {
        this.playlist_songs = playlist_songs;
    }

    public List<Song> getRecent_songs() {
        return recent_songs;
    }

    public void setRecent_songs(List<Song> recent_songs) {
        this.recent_songs = recent_songs;
    }

    public List<Song> getOld_songs() {
        return old_songs;
    }

    public void setOld_songs(List<Song> old_songs) {
        this.old_songs = old_songs;
    }


    public List<Song> getSuggested_songs() {
        return suggested_songs;
    }

    public void setSuggested_songs(List<Song> suggested_songs) {
        this.suggested_songs = suggested_songs;
    }
}
