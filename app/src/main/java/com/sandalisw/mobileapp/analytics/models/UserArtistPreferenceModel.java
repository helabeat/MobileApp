
package com.sandalisw.mobileapp.analytics.models;

public class UserArtistPreferenceModel {

    private int user_id;
    private String artist_name;
    private int count;

    public UserArtistPreferenceModel() {
    }

    public UserArtistPreferenceModel(int user_id, String artist_name, int count) {
        this.user_id = user_id;
        this.artist_name = artist_name;
        this.count = count;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}

