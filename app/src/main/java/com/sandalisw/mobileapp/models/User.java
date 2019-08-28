package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("age")
    private String age;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private String gender;


    @SerializedName("artist_preference")
    private List<String> artist_preference;

    @SerializedName("genre_preference")
    private  List<String> genre_preference;

    public User(String mUsername,String mEmail, String mAge, String mPassword, String mGender){
        username = mUsername;
        email = mEmail;
        age = mAge;
        password = mPassword;
        gender = mGender;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public List<String> getArtist_preference() {
        return artist_preference;
    }

    public void setArtist_preference(List<String> artist_preference) {
        this.artist_preference = artist_preference;
    }

    public List<String> getGenre_preference() {
        return genre_preference;
    }

    public void setGenre_preference(List<String> genre_preference) {
        this.genre_preference = genre_preference;
    }

    @Override
    public String toString() {
        return "Post{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password=" + password +
                '}';
    }
}
