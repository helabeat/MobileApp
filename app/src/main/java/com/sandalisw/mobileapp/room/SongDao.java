package com.sandalisw.mobileapp.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.Toast;

import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.SongR;

import java.util.List;

@Dao
public interface SongDao {

    //@Insert
    //public void addSong(SongT songt);

    @Insert
    public void Add(SongR song);

    @Query("select * from (select * from songs order by lastdate DESC) limit 10")
    public List<SongR> getSongs();

    @Query("select * from songs where song_name == :songname")
    public List<SongR> getSongs(String songname);

    //@Update
    //public void UpdateSong(SongR songR);

    @Query("update songs set lastdate = :lastdate where song_name == :songname")
    public void UpdateSong(long lastdate,String songname);

}
