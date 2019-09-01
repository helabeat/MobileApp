package com.sandalisw.mobileapp.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRequest {

    private static final String TAG = "UserRequest";
    private static UserRequest instance;
    private String userID;
    private MutableLiveData<String> isRegistered;

    public UserRequest() {
        this.isRegistered = new MutableLiveData<>();
    }

    private FirebaseFirestore getFIreStore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        return db;
    }

    public static UserRequest getInstance() {
        if(instance == null){
            instance = new UserRequest();
        }
        return instance;
    }

    public LiveData<String> registerUser(User user, List<String> mSelectedArtists, List<String> mSelectedGenres){
        registration(user,mSelectedArtists,mSelectedGenres);
        Log.d(TAG, "registerUser: "+isRegistered);
        return  isRegistered;
    }

    private void registration(User user, List<String> mSelectedArtists, List<String> mSelectedGenres){
        FirebaseFirestore db = getFIreStore();


        Map<String, Object> ob = new HashMap<>();
        ob.put("name", user.getUsername());
        ob.put("email", user.getEmail());
        ob.put("age", user.getAge());
        ob.put("gender",user.getGender());
        ob.put("artist_preference",mSelectedArtists);
        ob.put("genre_preference",mSelectedGenres);
        ob.put("history",new ArrayList<>());

        // Add a new document with a generated ID
        db.collection("users")
                .add(ob)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        userID =documentReference.getId();
                        isRegistered.setValue(userID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        isRegistered.setValue("0");
                    }
                });

    }

    public void updateHistory(Song song, String userId) {
        FirebaseFirestore db = getFIreStore();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());

        Map<String, Object> ob = new HashMap<>();
        ob.put("id",song.getId());
        ob.put("name",song.getTitle());
        ob.put("artist",song.getArtist());
        ob.put("date-time",currentDateandTime);

        db.collection("users")
                .document(userId)
                .collection("history")
                .add(ob)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
