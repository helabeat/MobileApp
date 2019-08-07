package com.sandalisw.mobileapp.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("age")
    private String age;

    @SerializedName("password")
    private String password;

    public User(String mUsername,String mEmail, String mAge, String mPassword){
        username = mUsername;
        email = mEmail;
        age = mAge;
        password = mPassword;
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
