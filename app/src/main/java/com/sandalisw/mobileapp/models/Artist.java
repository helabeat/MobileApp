package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("artist_name")
    @Expose
    private String artistName;

    @SerializedName("thumbnail")
    @Expose
    private int thumbnail;

    public Artist(Integer id,String artistName, int thumbnail) {
        this.id = id;
        this.artistName = artistName;
        this.thumbnail = thumbnail;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
