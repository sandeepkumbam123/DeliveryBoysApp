package com.example.nanni.myapplication.apiutils;

import com.example.nanni.myapplication.models.UpdateOrderStatus;
import com.example.nanni.myapplication.models.UserOrdersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by nanni...!!! on 6/3/2017.
 */

public interface ApiInterface {

    @POST("key/login/format/json")
    Call<LoginResponse> getloginResponse(@Body LoginRequest loginRequest);

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @GET("main/orders_for_delivery/format/json")
    Call<List<UserOrdersModel>> getUserOrders();

    @Headers("X-API-KEY:80w0g4o84wsc4gsc804c08scs00w8co4wscg848c")
    @POST("main/change_order_status/format/json")
    Call<Object> updateOrderStatus(@Body UpdateOrderStatus orderStatusRequest);



       // @GET("movie/top_rated")
       // Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

       // @GET("movie/{id}")
       // Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
