package com.sandalisw.mobileapp.room;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import android.widget.Toast;

import com.sandalisw.mobileapp.models.SongR;
import com.sandalisw.mobileapp.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SaveData {

    public static void saveSongs(MediaMetadataCompat mediaItem){
        String song_name = mediaItem.getDescription().getTitle().toString();
        String artist_name = mediaItem.getDescription().getSubtitle().toString();
        String song_url = mediaItem.getDescription().getMediaUri().toString();
        String thumbnail_url = mediaItem.getDescription().getIconUri().toString();

        long tsLong = System.currentTimeMillis()/1000;

        SongR songR = new SongR(song_name, artist_name,thumbnail_url,song_url,tsLong);

        List existingsong = new ArrayList();

        existingsong = MainActivity.songAppDatabase.songDao().getSongs(song_name);

        int size = existingsong.size();

        if(size==0){
            MainActivity.songAppDatabase.songDao().Add(songR);
        }
        else{
            MainActivity.songAppDatabase.songDao().UpdateSong(tsLong, song_name);
        }
    }
}
