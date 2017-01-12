package org.asdtm.fas.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;

import org.asdtm.fas.R;
import org.asdtm.fas.model.Genre;
import org.asdtm.fas.model.Network;
import org.asdtm.fas.model.ProductionCountry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StringUtils {

    public static String getYear(String dateStr) {
        dateStr = dateStr != null ? dateStr : "1970-01-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public static String getProductCountries(List<ProductionCountry> countries) {
        String result = "";
        for (int i = 0; i < countries.size(); i++) {
            if (i == countries.size() - 1) {
                result += countries.get(i).getName();
            } else {
                result += countries.get(i).getName() + ", ";
            }
        }
        return result;
    }

    public static String getNetworks(List<Network> networks) {
        String result = "-";
        if (!networks.isEmpty()) {
            result = "";
            for (int i = 0; i < networks.size(); i++) {
                if (i == networks.size() - 1) {
                    result += networks.get(i).getName();
                } else {
                    result += networks.get(i).getName() + ", ";
                }
            }
        }

        return result;
    }

    public static String getGenres(List<Genre> genres) {
        String result = "-";
        if (!genres.isEmpty()) {
            result = "";
            for (int i = 0; i < genres.size(); i++) {
                if (genres.get(i) == genres.get(genres.size() - 1)) {
                    result += genres.get(i).getName();
                } else {
                    result += genres.get(i).getName() + ", ";
                }
            }
        }

        return result;
    }

    public static String getEpisodeRuntime(Context context, int episodeRuntime) {
        String minutes = String.valueOf(episodeRuntime % 60);
        return context.getString(R.string.details_episode_runtime, minutes);
    }

    public static String formatRuntime(Context context, int time) {
        String hours = String.valueOf(time / 60);
        String minutes = String.valueOf(time % 60);
        return context.getString(R.string.details_runtime, hours, minutes);
    }

    public static String getDateToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar today = Calendar.getInstance();
        return format.format(today.getTime());
    }

    public static String getDateOnTheAir() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar week = Calendar.getInstance();
        week.set(Calendar.DAY_OF_YEAR, week.get(Calendar.DAY_OF_YEAR) + 7);
        return format.format(week.getTime());
    }

    public static String inTheatersLte() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar lte = Calendar.getInstance();
        lte.set(Calendar.DAY_OF_YEAR, lte.get(Calendar.DAY_OF_YEAR) + 2);
        return format.format(lte.getTime());
    }

    public static String inTheatersGte() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar gte = Calendar.getInstance();
        gte.set(Calendar.DAY_OF_YEAR, gte.get(Calendar.DAY_OF_YEAR) - 25);
        return format.format(gte.getTime());
    }

    public static String formatReleaseDate(String dateStr) {
        dateStr = dateStr != null ? dateStr : "1970-01-01";
        SimpleDateFormat inPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outPattern = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = inPattern.parse(dateStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(outPattern.format(calendar.getTime()));
    }

    public static Locale getLocale(String lang) {
        if (TextUtils.isEmpty(lang)) {
            return null;
        }
        final String[] parts = lang.split("_");
        if (parts.length == 1) {
            return new Locale(parts[0]);
        }
        if (parts.length == 2) {
            String country = parts[1];
            if (country.charAt(0) == 'r' && country.length() == 3) {
                country = country.substring(1);
            }
            return new Locale(parts[0], country);
        }
        return new Locale(lang);
    }

    public static String getFormatLocale(String locale) {
        if (TextUtils.isEmpty(locale)) {
            locale = Locale.getDefault().toString();
        }

        return locale.replace("_", "-");
    }

    @NonNull
    public static CharSequence fromHtml(@NonNull String htmlText) {
        CharSequence spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned =  Html.fromHtml(htmlText);
        }

        return trim(spanned);
    }

    private static CharSequence trim(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        int end = charSequence.length() - 1;
        while (Character.isWhitespace(charSequence.charAt(end))) {
            end--;
        }
        return charSequence.subSequence(0, end + 1);
    }
}