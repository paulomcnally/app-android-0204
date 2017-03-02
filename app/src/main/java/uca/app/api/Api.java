package uca.app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by polin on 02-23-17.
 */

public class Api {
    private final static String URL = "https://api-demo-uca.herokuapp.com/api";

    public static String getBase() {
        return URL + "/";
    }

    public static ApiInterface instance() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Api.getBase())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }
}
