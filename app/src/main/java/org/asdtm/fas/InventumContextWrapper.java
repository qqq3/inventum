package org.asdtm.fas;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;

import org.asdtm.fas.util.Constants;
import org.asdtm.fas.util.StringUtils;

import java.util.Locale;

public class InventumContextWrapper extends ContextWrapper {

    public InventumContextWrapper(Context base) {
        super(base);
    }

    @SuppressWarnings("deprecation")
    public static ContextWrapper wrap(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = prefs.getString(Constants.PREF_APP_LANGUAGE, "");
        Locale locale = StringUtils.getLocale(lang);
        locale = (locale == null) ? Locale.getDefault() : locale;
        Configuration configuration = context.getResources().getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            Locale.setDefault(locale);
            configuration.setLocale(locale);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            context.getResources().updateConfiguration(configuration, null);
        }

        return new InventumContextWrapper(context);
    }
}
