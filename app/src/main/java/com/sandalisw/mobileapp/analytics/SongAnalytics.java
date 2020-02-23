package com.sandalisw.mobileapp.analytics;


import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.sandalisw.mobileapp.analytics.models.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongAnalytics {

    private static ArrayList<Song> lst = new ArrayList<>();

    private static FirebaseFirestore db = DBConnection.dbConnection();

    public static void songAnalytics(MediaMetadataCompat mediaItem){

        //DatabaseReference reff;
        final CollectionReference songref = db.collection("SongAnalytics");
        //String user_id = SessionManagement.getSession();
        int song_id = Integer.parseInt(mediaItem.getDescription().getMediaId());
        String song_name = mediaItem.getDescription().getTitle().toString();
        int count = getCount(song_id);
        Long timeStamp = System.currentTimeMillis()/1000;

        if(count==0){
            count = 1;
            Song song = new Song(song_id, song_name, count, timeStamp);
            songref.add(song);
        }
        else if(count>=1){
            songref.whereEqualTo("songid", song_id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Song songret = document.toObject(Song.class);
                            Map<Object, Integer> map = new HashMap<>();
                            map.put("count", songret.getCount()+1);
                            songref.document(document.getId()).set(map, SetOptions.merge());
                        }
                    }
                }
            });
        }
        //reff = FirebaseDatabase.getInstance().getReference().child("songs");
        //reff.push().setValue(song);


        //songref.add(song);
    }

    public  static int getCount(int song_id){
        final CollectionReference songref = db.collection("SongAnalytics");

        Query songQuery = songref
                .whereEqualTo("songid",song_id);

        songQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Song songret = document.toObject(Song.class);
                        lst.add(songret);
                    }
                }

            }

        });

        if(lst.isEmpty()){
            return 0;
        }
        else{
            return lst.get(0).getCount();
        }
    }
}
