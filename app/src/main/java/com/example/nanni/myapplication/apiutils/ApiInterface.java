package com.example.nanni.myapplication.apiutils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nanni...!!! on 6/3/2017.
 */

public interface ApiInterface {

    @POST("key/login/format/json")
    Call<LoginResponse> getloginResponse(@Body LoginRequest loginRequest);

       // @GET("movie/top_rated")
       // Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

       // @GET("movie/{id}")
       // Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
