package com.sandalisw.mobileapp.analytics.models;


public class Song {

    //@Exclude
    //private String id;

    private String username;
    private int songid;
    private String songname;
    private int count;
    private long timestamp;

    public Song() {
    }

    public Song(int songid, String songname, int count, long timestamp) {
        this.songid = songid;
        this.songname = songname;
        this.count = count;
        this.timestamp = timestamp;
    }

    //public String getId() {
    //    return id;
    //}

    //public void setId(String id) {
    //    this.id = id;
    //}

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
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
