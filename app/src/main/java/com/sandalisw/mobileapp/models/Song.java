package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("song_id")
    private int id;
    @SerializedName("song_name")
    private String title;
    @SerializedName("artist_id")
    private String artist;
    @SerializedName("thumbnail_url")
    private String thumbnailUrl;
    @SerializedName("song_url")
    private String song_url;

    public Song(int id, String title, String artist, String thumbnailUrl, String song_url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.thumbnailUrl = thumbnailUrl;
        this.song_url = song_url;
    }

    public String  getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public String getSong_url() {
        return song_url;
    }

}
