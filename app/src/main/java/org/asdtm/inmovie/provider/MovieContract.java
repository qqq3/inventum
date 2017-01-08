package org.asdtm.inmovie.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_TYPE_APP_BASE = "movies.";
    public static final String CONTENT_TYPE_BASE =
            "vnd.android.cursor.dir/vnd." + CONTENT_TYPE_APP_BASE;
    public static final String CONTENT_ITEM_TYPE_BASE =
            "vnd.android.cursor.item/vnd." + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_AUTHORITY = "org.asdtm.inmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_MOVIES = "movies";
    private static final String PATH_TVS = "tvs";
    private static final String PATH_PERSONS = "persons";

    private MovieContract() {
    }

    interface MoviesColumns {
        String MOVIE_ADULT = "movie_adult";
        String MOVIE_ID = "movie_id";
        String MOVIE_IMDB_ID = "movie_imdb_id";
        String MOVIE_TITLE = "movie_title";
        String MOVIE_ORIGINAL_TITLE = "movie_original_title";
        String MOVIE_ORIGINAL_LANGUAGE = "movie_original_language";
        String MOVIE_OVERVIEW = "movie_overview";
        String MOVIE_RELEASE_DATE = "movie_release_date";
        String MOVIE_POSTER_PATH = "movie_poster_path";
        String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        String MOVIE_BUDGET = "movie_budget";
        String MOVIE_HOMEPAGE = "movie_homepage";
        String MOVIE_POPULARITY = "movie_popularity";
        String MOVIE_REVENUE = "movie_revenue";
        String MOVIE_RUNTIME = "movie_runtime";
        String MOVIE_STATUS = "movie_status";
        String MOVIE_TAGLINE = "movie_tagline";
        String MOVIE_VOTE_COUNT = "movie_vote_count";
        String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        String MOVIE_GENRES = "movie_genres";
        String MOVIE_PRODUCTION_COUNTRIES = "movie_production_countries";
    }

    interface TVsColumns {
        String TV_ID = "tv_id";
        String TV_NAME = "tv_name";
        String TV_OVERVIEW = "tv_overview";
        String TV_ORIGINAL_NAME = "tv_original_name";
        String TV_ORIGINAL_LANGUAGE = "tv_original_language";
        String TV_ORIGINAL_COUNTRY = "tv_original_country";
        String TV_POSTER_PATH = "tv_poster_path";
        String TV_BACKDROP_PATH = "tv_backdrop_path";
        String TV_EPISODE_RUNTIME = "tv_episode_runtime";
        String TV_FIRST_AIR_DATE = "tv_first_air_date";
        String TV_LAST_AIR_DATE = "tv_last_air_date";
        String TV_GENRES = "tv_genres";
        String TV_HOMEPAGE = "tv_homepage";
        String TV_IN_PRODUCTION = "tv_in_production";
        String TV_NETWORKS = "tv_networks";
        String TV_NUMBER_OF_EPISODES = "tv_number_of_episodes";
        String TV_NUMBER_OF_SEASONS = "tv_number_of_seasons";
        String TV_POPULARITY = "tv_popularity";
        String TV_STATUS = "tv_status";
        String TV_VOTE_COUNT = "tv_vote_count";
        String TV_VOTE_AVERAGE = "tv_vote_average";
    }

    interface PersonsColumns {
        String PERSON_NAME = "person_name";
        String PERSON_ID = "person_id";
        String PERSON_IMDB_ID = "person_imdb_id";
        String PERSON_ADULT = "person_adult";
        String PERSON_BIOGRAPHY = "person_biography";
        String PERSON_BIRTHDAY = "person_birthday";
        String PERSON_DEATHDAY = "person_deathday";
        String PERSON_HOMEPAGE = "person_homepage";
        String PERSON_PLACE_OF_BIRTH = "person_place_of_birth";
        String PERSON_POPULARITY = "person_popularity";
        String PERSON_PROFILE_PATH = "person_profile_path";
    }

    public static class Movies implements MoviesColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE_ID = "movies";

        public static Uri buildMovieUri(String movieId) {
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }

        public static String getMovieId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class TVs implements TVsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TVS).build();

        public static final String CONTENT_TYPE_ID = "tvs";

        public static Uri buildTVUri(String tvId) {
            return CONTENT_URI.buildUpon().appendPath(tvId).build();
        }

        public static String getTVId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Persons implements PersonsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSONS).build();

        public static final String CONTENT_TYPE_ID = "persons";

        public static Uri buildPersonUri(String personId) {
            return CONTENT_URI.buildUpon().appendPath(personId).build();
        }

        public static String getPersonId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }
}
