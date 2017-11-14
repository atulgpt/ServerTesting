package com.example.atulgupta.testing.rest;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by atulgupta on 11-11-2017 and 11 at 12:19 AM for Testing .
 */

public interface ApiInterface {
    @POST("/test")
    Call<String> getTiming();
}
