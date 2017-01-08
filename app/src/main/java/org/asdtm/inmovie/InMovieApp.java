package org.asdtm.inmovie;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import org.asdtm.inmovie.util.Constants;

public class InMovieApp extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(base);
        String lang = prefs.getString(Constants.PREF_APP_LANGUAGE, "");
        super.attachBaseContext(InMovieContextWrapper.wrap(base));
    }
}
