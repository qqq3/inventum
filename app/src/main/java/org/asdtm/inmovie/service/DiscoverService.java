package org.asdtm.inmovie.service;

import org.asdtm.inmovie.model.MovieResults;
import org.asdtm.inmovie.model.TVResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiscoverService {

    @GET("discover/movie")
    Call<MovieResults> inTheaters(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page,
            @Query("sort_by") String sortBy,
            @Query("primary_release_date.lte") String date_lte,
            @Query("primary_release_date.gte") String date_gte
    );

    @GET("discover/tv")
    Call<TVResults> onTv(
            @Query("air_date.lte") String air_date_lte,
            @Query("air_date.gte") String air_date_gte,
            @Query("sort_by") String sortBy,
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String api_key
    );

    @GET("discover/movie")
    Call<MovieResults> discoverMovie(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page,
            @Query("sort_by") String sortBy,
            @Query("vote_average.gte") String vote_average_gte,
            @Query("with_genres") String genres
    );

    @GET("discover/tv")
    Call<TVResults> discoverTv(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page,
            @Query("sort_by") String sortBy,
            @Query("vote_average.gte") String vote_average_gte,
            @Query("with_genres") String genres
    );
}
