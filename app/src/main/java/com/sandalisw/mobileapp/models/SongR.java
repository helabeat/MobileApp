package com.sandalisw.mobileapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "songs")
public class SongR {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "song_name")
    private String songname;

    @ColumnInfo(name = "artist_name")
    private String artistname;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailurl;

    @ColumnInfo
    private String songurl;

    @ColumnInfo(name = "lastdate")
    private long lastdate;

    public SongR(String songname, String artistname, String thumbnailurl, String songurl, long lastdate) {
        this.songname = songname;
        this.artistname = artistname;
        this.thumbnailurl = thumbnailurl;
        this.songurl = songurl;
        this.lastdate = lastdate;
    }

    public int getId() {
        return id;
    }

    public String getSongname() {
        return songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public String getSongurl() {
        return songurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    public void setSongurl(String songurl) {
        this.songurl = songurl;
    }

    public long getLastdate() {
        return lastdate;
    }

    public void setLastdate(long lastdate) {
        this.lastdate = lastdate;
    }
}
