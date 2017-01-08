package org.asdtm.inmovie.service;

import org.asdtm.inmovie.model.MultiSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("search/multi")
    Call<MultiSearch> multiSearch(
            @Query("query") String query,
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") String page
    );
}
