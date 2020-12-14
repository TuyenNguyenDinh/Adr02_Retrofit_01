package com.example.lession06_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CategoryAPI {

    @GET("categories")
    Call<List<Category>> getResponse();

}
