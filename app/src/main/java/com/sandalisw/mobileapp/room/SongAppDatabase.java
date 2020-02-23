package com.sandalisw.mobileapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.SongR;

//import com.sandalisw.mobileapp.models.SongR;

@Database(entities = {SongR.class}, version = 1, exportSchema = false)
public abstract class SongAppDatabase extends RoomDatabase {

    public abstract SongDao songDao();
}
