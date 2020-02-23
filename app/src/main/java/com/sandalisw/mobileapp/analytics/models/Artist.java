package com.sandalisw.mobileapp.analytics.models;

public class Artist {

    private String artist_name;
    private int count;
    private long timestamp;

    public Artist() {
    }

    public Artist(String artist_name, int count, long timestamp) {
        this.artist_name = artist_name;
        this.count = count;
        this.timestamp = timestamp;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
