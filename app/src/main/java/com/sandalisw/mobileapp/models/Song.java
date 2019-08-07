package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("artist")
    private String artist;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    @SerializedName("url")
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

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }
}
