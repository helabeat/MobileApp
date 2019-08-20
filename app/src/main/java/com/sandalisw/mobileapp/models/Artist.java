package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("artist_id")
    @Expose
    private Integer id;

    @SerializedName("artist_name")
    @Expose
    private String artistName;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnail;

    public Artist(Integer id,String artistName, String thumbnail) {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
