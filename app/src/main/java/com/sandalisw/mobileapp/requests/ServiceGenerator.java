package com.sandalisw.mobileapp.requests;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Retrofit Client Instance
public class ServiceGenerator {

    private static final String URL = "http://10.0.2.2:3000";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RequestApi requestApi = retrofit.create(RequestApi.class);

    public static RequestApi getRequestApi(){
        return requestApi;
    }

//    public static Retrofit getRetrofitInstance(){
//        Log.d("ServiceGenerator","init requets");
//        if(retrofit == null){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }

    //Volley Instance

}
