package org.asdtm.fas.service;

import org.asdtm.fas.model.Person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PeopleService {

    @GET("person/{person_id}")
    Call<Person> peopleDetails(
            @Path("person_id") String person_id,
            @Query("api_key") String api_key,
            @Query("language") String lang
    );
}
