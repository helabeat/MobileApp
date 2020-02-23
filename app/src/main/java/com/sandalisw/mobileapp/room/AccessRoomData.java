package com.sandalisw.mobileapp.room;

import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.SongR;
import com.sandalisw.mobileapp.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AccessRoomData {

    public static List<Song> getRecentlyPlayedSongs(){
        List<Song> lstSong = new ArrayList<>();

        List<SongR> songs = MainActivity.songAppDatabase.songDao().getSongs();

        for(SongR song : songs)
        {
            int id = song.getId();
            String songname = song.getSongname();
            String artistname = song.getArtistname();
            String songurl = song.getSongurl();
            String thumbnailurl = song.getThumbnailurl();

            Song newSong = new Song(id, songname, artistname, thumbnailurl, songurl);

            lstSong.add(newSong);

        }

        return lstSong;

    }
}
