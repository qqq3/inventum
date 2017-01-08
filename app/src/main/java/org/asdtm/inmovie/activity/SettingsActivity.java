package org.asdtm.inmovie.activity;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.asdtm.inmovie.InMovieContextWrapper;
import org.asdtm.inmovie.util.Constants;
import org.asdtm.inmovie.R;
import org.asdtm.inmovie.util.AppUtils;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(InMovieContextWrapper.wrap(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        getFragmentManager().beginTransaction().replace(android.R.id.content, new GeneralPreferenceFragment()).commit();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private static final String TAG = GeneralPreferenceFragment.class.getSimpleName();

        private static final String[] SUMMARIES_TO_UPDATE = {
                Constants.PREF_APP_LANGUAGE,
                Constants.PREF_MOVIES_LANGUAGE,
                Constants.PREF_IMAGES_CACHE
        };

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getActivity().finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updateSummary(key, true);
        }

        private void updateSummary(String key, boolean changing) {
            switch (key) {
                case Constants.PREF_APP_LANGUAGE:
                    entrySummary(key);
                    if (changing) {
                        DialogFragment dialogFragment = new SettingsAlertDialog().newInstance(R.string.restart_dialog_message);
                        dialogFragment.show(getActivity().getFragmentManager(), "restartApp");
                    }
                    break;
                case Constants.PREF_MOVIES_LANGUAGE:
                    entrySummary(key);
                    break;
                case Constants.PREF_IMAGES_CACHE:
                    File cacheDir = getActivity().getCacheDir();
                    File picassoCacheDir = new File(cacheDir, "picasso-cache");
                    double size = AppUtils.getDirSize(picassoCacheDir);
                    preferenceSummary(key, getString(R.string.pref_summary_cache_size, size));
                    break;
            }
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if (preference.equals(findPreference(Constants.PREF_IMAGES_CACHE))) {
                File cacheDir = getActivity().getCacheDir();
                File picassoCacheDir = new File(cacheDir, "picasso-cache");
                int messageId = AppUtils.deleteDir(picassoCacheDir) ? R.string.clear_cache_message_ok : R.string.clear_cache_message_fail;
                DialogFragment dialogFragment = new SettingsAlertDialog().newInstance(messageId);
                dialogFragment.show(getActivity().getFragmentManager(), "clearCache");
                updateSummary(Constants.PREF_IMAGES_CACHE, false);
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        private void entrySummary(String key) {
            ListPreference pref = (ListPreference) findPreference(key);
            pref.setSummary(pref.getEntry());
        }

        private void preferenceSummary(String key, String summary) {
            Preference pref = findPreference(key);
            pref.setSummary(summary);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            for (String key : SUMMARIES_TO_UPDATE)
                updateSummary(key, false);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    public static class SettingsAlertDialog extends DialogFragment {

        private static final String ARG_RES_ID = "org.asdtm.inmovie.settingsalertdialog.res_id";

        public SettingsAlertDialog newInstance(int messageResId) {
            SettingsAlertDialog fragment = new SettingsAlertDialog();
            Bundle args = new Bundle();
            args.putInt(ARG_RES_ID, messageResId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int messageResId = getArguments().getInt(ARG_RES_ID);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(messageResId));
            builder.setPositiveButton(android.R.string.ok, null);

            return builder.create();
        }
    }
}
