package com.example.dictionary;

import com.example.dictionary.ModelClasses.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiSet {

    @GET("entries/en/{word}")
    Call<List<ApiResponse>> getResponse(@Path("word") String word);


}
