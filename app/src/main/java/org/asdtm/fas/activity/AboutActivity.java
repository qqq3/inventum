package org.asdtm.fas.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.asdtm.fas.InventumContextWrapper;
import org.asdtm.fas.R;
import org.asdtm.fas.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = AboutActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.about_version_text) TextView versionTextView;
    @BindView(R.id.about_app_info_text) TextView appInfoTextView;
    @BindView(R.id.about_libraries_text) TextView librariesTextView;
    @BindView(R.id.about_license_text) TextView licenseTextView;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(InventumContextWrapper.wrap(base));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setupToolbar();

        MovementMethod movementMethod = LinkMovementMethod.getInstance();

        versionTextView.setText(getVersionName());
        appInfoTextView.setText(StringUtils.fromHtml(getString(R.string.about_app_info)));
        appInfoTextView.setMovementMethod(movementMethod);
        librariesTextView.setText(StringUtils.fromHtml(getString(R.string.about_libraries_text)));
        librariesTextView.setMovementMethod(movementMethod);
        licenseTextView.setText(StringUtils.fromHtml(getString(R.string.about_license_text)));
        licenseTextView.setMovementMethod(movementMethod);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private String getVersionName() {
        String versionName;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Get version name error: ", e);
            versionName = "666";
        }
        return versionName;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
