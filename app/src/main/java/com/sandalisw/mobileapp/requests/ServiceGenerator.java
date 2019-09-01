package com.sandalisw.mobileapp.requests;


import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Retrofit Client Instance
public class ServiceGenerator {

    private static final String URL = "http://ec2-18-139-140-235.ap-southeast-1.compute.amazonaws.com:3000";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RequestApi requestApi = retrofit.create(RequestApi.class);

    public static RequestApi getRequestApi(){
        return requestApi;
    }

    public static FirebaseFirestore getFirebaseApi(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db;
    }



}
