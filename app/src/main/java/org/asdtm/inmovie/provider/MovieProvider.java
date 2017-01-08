package org.asdtm.inmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.asdtm.inmovie.provider.MovieContract.Movies;
import org.asdtm.inmovie.provider.MovieContract.MoviesColumns;
import org.asdtm.inmovie.provider.MovieContract.Persons;
import org.asdtm.inmovie.provider.MovieContract.PersonsColumns;
import org.asdtm.inmovie.provider.MovieContract.TVs;
import org.asdtm.inmovie.provider.MovieContract.TVsColumns;

public class MovieProvider extends ContentProvider {

    private static final String TAG = MovieProvider.class.getSimpleName();

    private MovieDatabase mOpenHelper;

    private MovieProviderUriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDatabase(getContext());
        mUriMatcher = new MovieProviderUriMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        MovieUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(matchingUriEnum.table);
        switch (matchingUriEnum) {
            case MOVIES_ID:
                qb.appendWhere(MoviesColumns.MOVIE_ID + "=" + Movies.getMovieId(uri));
                break;
            case TVS_ID:
                qb.appendWhere(TVsColumns.TV_ID + "=" + TVs.getTVId(uri));
                break;
            case PERSONS_ID:
                qb.appendWhere(PersonsColumns.PERSON_ID + "=" + Persons.getPersonId(uri));
                break;
        }

        Cursor cursor = db.query(matchingUriEnum.table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        MovieUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        return matchingUriEnum.contentType;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        MovieUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        Log.i(TAG, "insert; Table: " + matchingUriEnum.table);

        if (matchingUriEnum.table != null) {
            try {
                db.insertOrThrow(matchingUriEnum.table, null, values);
            } catch (SQLiteConstraintException e) {
                throw e;
            }
        }

        switch (matchingUriEnum) {
            case MOVIES:
                return Movies.buildMovieUri(values.getAsString(Movies.MOVIE_ID));
            case TVS:
                return TVs.buildTVUri(values.getAsString(TVs.TV_ID));
            case PERSONS:
                return Persons.buildPersonUri(values.getAsString(Persons.PERSON_ID));
            default:
                throw new UnsupportedOperationException("Unknown insert URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        MovieUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        int count = 0;
        switch (matchingUriEnum) {
            case MOVIES_ID:
                String movieId = Movies.getMovieId(uri);
                if (TextUtils.isEmpty(selection)) {
                    selection = MoviesColumns.MOVIE_ID + " = " + movieId;
                } else {
                    selection = selection + " AND " + MoviesColumns.MOVIE_ID + " = " + movieId;
                }
                break;
            case TVS_ID:
                String tvID = TVs.getTVId(uri);
                if (TextUtils.isEmpty(selection)) {
                    selection = TVsColumns.TV_ID + " = " + tvID;
                } else {
                    selection = selection + " AND " + TVsColumns.TV_ID + " = " + tvID;
                }
                break;
            case PERSONS_ID:
                String personID = Persons.getPersonId(uri);
                if (TextUtils.isEmpty(selection)) {
                    selection = PersonsColumns.PERSON_ID + "=?";
                    selectionArgs = new String[]{personID};
                } else {
                    selection = selection + " AND " + PersonsColumns.PERSON_ID + "=" + personID;
                    selectionArgs = new String[]{personID};
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        count = db.delete(matchingUriEnum.table, selection, selectionArgs);
        notifyChanged(uri);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        MovieUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        Log.i(TAG, "update; Table: " + matchingUriEnum.table);

        int count = 0;
        switch (matchingUriEnum) {
            case MOVIES_ID:
                String movieId = Movies.getMovieId(uri);
                if (TextUtils.isEmpty(selection)) {
//                    selection = MoviesColumns.MOVIE_ID + " = " + movieId;
                    selection = MoviesColumns.MOVIE_ID + "=?";
                    selectionArgs = new String[]{movieId};
                } else {
//                    selection = selection + " AND " + MoviesColumns.MOVIE_ID + " = " + movieId;
                    selection = selection + " AND " + MoviesColumns.MOVIE_ID + "=?";
                    selectionArgs = new String[]{movieId};
                }
                break;
            case TVS_ID:
                String tvId = TVs.getTVId(uri);
                if (TextUtils.isEmpty(selection)) {
                    selection = TVsColumns.TV_ID + " = " + tvId;
                } else {
                    selection = selection + " AND " + TVsColumns.TV_ID + " = " + tvId;
                }
                break;
            case PERSONS_ID:
                String person_id = Persons.getPersonId(uri);
                if (TextUtils.isEmpty(selection)) {
                    selection = PersonsColumns.PERSON_ID + "=?";
                    selectionArgs = new String[]{person_id};
                } else {
                    selection = selection + " AND " + PersonsColumns.PERSON_ID + "=?";
                    selectionArgs = new String[]{person_id};
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        count = db.update(matchingUriEnum.table, values, selection, selectionArgs);
        notifyChanged(uri);
        return count;
    }

    private void notifyChanged(Uri uri) {
        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
    }
}
