package org.asdtm.fas.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    public static String getFormatLocale(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return StringUtils.getFormatLocale(prefs.getString(Constants.PREF_MOVIES_LANGUAGE, ""));
    }
}
