package com.example.atulgupta.testing.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by atulgupta on 11-11-2017 and 11 at 12:18 AM for Testing .
 */

public class ApiClient {

    public static final String BASE_URL = "https://us-central1-firebase-triker.cloudfunctions.net/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder ()
                    .baseUrl (BASE_URL)
                    .addConverterFactory (GsonConverterFactory.create ())
                    .build ();
        }
        return retrofit;
    }
}
