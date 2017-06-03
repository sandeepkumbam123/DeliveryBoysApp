package com.example.nanni.myapplication.apiutils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nanni...!!! on 6/3/2017.
 */

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

}
