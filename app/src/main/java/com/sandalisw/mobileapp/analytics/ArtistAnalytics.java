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
import com.sandalisw.mobileapp.analytics.models.Artist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArtistAnalytics {

    private static ArrayList<Artist> lst = new ArrayList<>();
    private static FirebaseFirestore db = DBConnection.dbConnection();

    public static void artistAnalytics(MediaMetadataCompat mediaItem){

        //FirebaseFirestore db;
        //FirebaseFirestore db = DBConnection.dbConnection();
        final CollectionReference artistref = db.collection("ArtistAnalytics");

        int artist_id = 2;
        String artist_name = mediaItem.getDescription().getSubtitle().toString();
        int count = getCount(artist_name);//1;
        long timestamp = System.currentTimeMillis()/1000;

        if(count == 0){
            count = count + 1;
            Artist artist = new Artist(artist_name, count, timestamp);
            artistref.add(artist);
        }
        else if(count>=1){
            artistref.whereEqualTo("artist_name", artist_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Artist artistret = document.toObject(Artist.class);
                            Map<Object, Integer> map = new HashMap<>();
                            map.put("count", artistret.getCount()+1);
                            artistref.document(document.getId()).set(map, SetOptions.merge());
                        }
                    }
                }
            });
        }


    }

    public static int getCount(String artist_name){
        final CollectionReference songref = db.collection("ArtistAnalytics");

        Query songQuery = songref
                .whereEqualTo("artist_name",artist_name);

        songQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Artist songret = document.toObject(Artist.class);
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
