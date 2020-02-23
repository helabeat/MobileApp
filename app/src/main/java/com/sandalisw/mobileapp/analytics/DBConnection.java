package com.sandalisw.mobileapp.analytics;

import com.google.firebase.firestore.FirebaseFirestore;

public class DBConnection{

    public static FirebaseFirestore dbConnection(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        return db;
    }
}
