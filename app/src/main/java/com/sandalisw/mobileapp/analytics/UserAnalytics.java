package com.sandalisw.mobileapp.analytics;

import android.support.v4.media.MediaMetadataCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandalisw.mobileapp.analytics.models.UserHistry;

public class UserAnalytics {

    private static FirebaseFirestore db = DBConnection.dbConnection();

    public static void userHistry(MediaMetadataCompat mediaItem, String user_id){

        final CollectionReference userref = db.collection("UserHistry");
        //int user_id = 1;
        int song_id = Integer.parseInt(mediaItem.getDescription().getMediaId());
        Long timeStamp = System.currentTimeMillis()/1000;

        UserHistry userHistry = new UserHistry(user_id,song_id,timeStamp);

        userref.add(userHistry);

    }
}
