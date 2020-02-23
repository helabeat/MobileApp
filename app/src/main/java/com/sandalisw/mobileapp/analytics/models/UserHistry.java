package com.sandalisw.mobileapp.analytics.models;

public class UserHistry {

    private String userid;
    private int songid;
    private long timeStamp;

    public UserHistry(String userid, int songid, long timeStamp) {
        this.userid = userid;
        this.songid = songid;
        this.timeStamp = timeStamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
