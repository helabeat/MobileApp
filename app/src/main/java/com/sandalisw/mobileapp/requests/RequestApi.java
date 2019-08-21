package com.sandalisw.mobileapp.requests;


import com.sandalisw.mobileapp.models.Artist;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.models.User;
import com.sandalisw.mobileapp.requests.responses.TopSongsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestApi {

    @GET("/songs/topsongs")
    Call<TopSongsResponse> getAllSongs();

    @POST("/user")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<User> registerUser(
            @Body User user
    );

    @GET("/artists/artist_preference")
    Call<List<Artist>> getArtists();

    @GET("songs/search")
    Call<List<Song>> searchSongs(
            @Query("q") String query
    );


}
