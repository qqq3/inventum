package org.asdtm.fas.util;

import android.content.ContentValues;

import org.asdtm.fas.model.Movie;
import org.asdtm.fas.model.Person;
import org.asdtm.fas.model.TV;
import org.asdtm.fas.provider.MovieContract;

public class ContentValuesUtils {
    public static ContentValues setPersonValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.Persons.PERSON_NAME, person.getName());
        values.put(MovieContract.Persons.PERSON_ID, person.getId());
        values.put(MovieContract.Persons.PERSON_IMDB_ID, person.getImdbId());
        values.put(MovieContract.Persons.PERSON_ADULT, person.isAdult());
        values.put(MovieContract.Persons.PERSON_BIOGRAPHY, person.getBiography());
        values.put(MovieContract.Persons.PERSON_BIRTHDAY, person.getBirthday());
        values.put(MovieContract.Persons.PERSON_DEATHDAY, person.getDeathday());
        values.put(MovieContract.Persons.PERSON_HOMEPAGE, person.getHomepage());
        values.put(MovieContract.Persons.PERSON_PLACE_OF_BIRTH, person.getPlaceOfBirth());
        values.put(MovieContract.Persons.PERSON_POPULARITY, person.getPopularity());
        values.put(MovieContract.Persons.PERSON_PROFILE_PATH, person.getProfilePath());

        return values;
    }

    public static ContentValues setMovieValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.Movies.MOVIE_ADULT, movie.isAdult());
        values.put(MovieContract.Movies.MOVIE_ID, movie.getId());
        values.put(MovieContract.Movies.MOVIE_IMDB_ID, movie.getImdbId());
        values.put(MovieContract.Movies.MOVIE_TITLE, movie.getTitle());
        values.put(MovieContract.Movies.MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.Movies.MOVIE_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        values.put(MovieContract.Movies.MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieContract.Movies.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.Movies.MOVIE_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.Movies.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.Movies.MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.Movies.MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieContract.Movies.MOVIE_BUDGET, movie.getBudget());
        values.put(MovieContract.Movies.MOVIE_HOMEPAGE, movie.getHomepage());
        values.put(MovieContract.Movies.MOVIE_POPULARITY, movie.getPopularity());
        values.put(MovieContract.Movies.MOVIE_REVENUE, movie.getRevenue());
        values.put(MovieContract.Movies.MOVIE_RUNTIME, movie.getRuntime());
        values.put(MovieContract.Movies.MOVIE_STATUS, movie.getStatus());
        values.put(MovieContract.Movies.MOVIE_TAGLINE, movie.getTagline());
        values.put(MovieContract.Movies.MOVIE_GENRES, StringUtils.getGenres(movie.getGenres()));
        values.put(MovieContract.Movies.MOVIE_PRODUCTION_COUNTRIES, StringUtils.getProductCountries(movie.getProductionCountries()));

        return values;
    }

    public static ContentValues setTvValues(TV tv) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.TVs.TV_ID, tv.getId());
        values.put(MovieContract.TVs.TV_NAME, tv.getName());
        values.put(MovieContract.TVs.TV_OVERVIEW, tv.getOverview());
        values.put(MovieContract.TVs.TV_ORIGINAL_NAME, tv.getOriginalName());
        values.put(MovieContract.TVs.TV_ORIGINAL_LANGUAGE, tv.getOriginalLanguage());
        String country = (!tv.getOriginCountry().isEmpty()) ? tv.getOriginCountry().get(0) : "-";
        values.put(MovieContract.TVs.TV_ORIGINAL_COUNTRY, country);
        values.put(MovieContract.TVs.TV_BACKDROP_PATH, tv.getBackdropPath());
        values.put(MovieContract.TVs.TV_POSTER_PATH, tv.getPosterPath());
        int runtime = (!tv.getEpisodeRunTime().isEmpty()) ? tv.getEpisodeRunTime().get(0) : 0;
        values.put(MovieContract.TVs.TV_EPISODE_RUNTIME, runtime);
        values.put(MovieContract.TVs.TV_FIRST_AIR_DATE, tv.getFirstAirDate());
        values.put(MovieContract.TVs.TV_LAST_AIR_DATE, tv.getLastAirDate());
        values.put(MovieContract.TVs.TV_GENRES, StringUtils.getGenres(tv.getGenres()));
        values.put(MovieContract.TVs.TV_HOMEPAGE, tv.getHomepage());
        values.put(MovieContract.TVs.TV_IN_PRODUCTION, tv.isInProduction());
        values.put(MovieContract.TVs.TV_NETWORKS, StringUtils.getNetworks(tv.getNetworks()));
        values.put(MovieContract.TVs.TV_NUMBER_OF_SEASONS, tv.getNumberOfSeasons());
        values.put(MovieContract.TVs.TV_NUMBER_OF_EPISODES, tv.getNumberOfEpisodes());
        values.put(MovieContract.TVs.TV_STATUS, tv.getStatus());
        values.put(MovieContract.TVs.TV_POPULARITY, tv.getPopularity());
        values.put(MovieContract.TVs.TV_VOTE_COUNT, tv.getVoteCount());
        values.put(MovieContract.TVs.TV_VOTE_AVERAGE, tv.getVoteAverage());
        return values;
    }
}
