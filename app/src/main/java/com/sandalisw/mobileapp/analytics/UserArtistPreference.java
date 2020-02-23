package com.sandalisw.mobileapp.analytics;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandalisw.mobileapp.analytics.models.Song;
import com.sandalisw.mobileapp.analytics.models.UserArtistPreferenceModel;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserArtistPreference {

    private static ArrayList<UserArtistPreferenceModel> lstnew = new ArrayList<>();

    private static FirebaseFirestore db = DBConnection.dbConnection();

    public static void userArtistPreferences(MediaMetadataCompat mediaItem){

        final CollectionReference artistpref = db.collection("UserArtistPreference");
        //SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        //String userId = sp.getString("userId","0");

        int user_id = 1;
        String artist_name = (String) mediaItem.getDescription().getSubtitle();
        int count = getCount(artist_name);

        if(count == 0){
            count=count+1;
            UserArtistPreferenceModel userArtistPreferenceModel = new UserArtistPreferenceModel(user_id, artist_name, count);
            artistpref.add(userArtistPreferenceModel);
        }

    }

    public static int getCount(String artist_name){
        final CollectionReference songref = db.collection("UserArtistPreference");

        Query songQuery = songref
                .whereEqualTo("artist_Name", artist_name);

        songQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()){
                        UserArtistPreferenceModel songret = document.toObject(UserArtistPreferenceModel.class);
                        lstnew.add(songret);
                        //Log.i("hghg",songret.getArtist_Name());

                    }
                }

            }

        });

        if(lstnew.isEmpty()){
            return 0;
        }
        else{
            return lstnew.get(0).getCount();
        }


    }
}
