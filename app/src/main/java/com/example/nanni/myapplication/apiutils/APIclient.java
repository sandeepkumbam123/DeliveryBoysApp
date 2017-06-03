package com.example.nanni.myapplication.apiutils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nanni...!!! on 6/3/2017.
 */

public class APIclient {



    public static final String BASE_URL = "http://www.nutrimeat.in/api/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

}
