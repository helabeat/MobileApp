package com.sandalisw.mobileapp.utils;

import com.sandalisw.mobileapp.models.Song;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Song> datalist = new ArrayList();

    public void setData() {

        for(int i=0;i<10;i++){
            datalist.add(new Song(
                        i,
                    "Song "+i,
                    "artist "+i,
                    "https://tse2.mm.bing.net/th?id=OIP.Mc780f0d5e596912333bf8bb949206103o0&pid=Api&w=180&h=181",
                    "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3"
            ));
        }
    }

    public List<Song> getData(){
        return datalist;
    }
}
