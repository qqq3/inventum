package org.asdtm.inmovie.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.asdtm.inmovie.InMovieContextWrapper;
import org.asdtm.inmovie.util.Constants;
import org.asdtm.inmovie.R;
import org.asdtm.inmovie.service.ServiceGenerator;
import org.asdtm.inmovie.model.Person;
import org.asdtm.inmovie.provider.MovieContract;
import org.asdtm.inmovie.service.PeopleService;
import org.asdtm.inmovie.util.AppUtils;
import org.asdtm.inmovie.util.ContentValuesUtils;
import org.asdtm.inmovie.util.PrefUtils;
import org.asdtm.inmovie.util.StringUtils;
import org.asdtm.inmovie.view.ExpandableTextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    private static final String TAG = PersonDetailsActivity.class.getSimpleName();

    private static final String PERSON_ID = "org.asdtm.inmovie.details.person_id";

    private Cursor mCursor;
    private ContentResolver mResolver;
    private String mId;

    @BindDrawable(R.drawable.background_reel) Drawable placeholderImage;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.person_details_photo) ImageView photo;
    @BindView(R.id.person_details_name) TextView name;
    @BindView(R.id.person_details_birthday) TextView birthday;
    @BindView(R.id.person_details_place_of_birth) TextView placeOfBirth;
    @BindView(R.id.person_details_deathday) TextView deathday;
    @BindView(R.id.person_details_death_layout) LinearLayout deathLayout;
    @BindView(R.id.person_details_biography) ExpandableTextView biography;
    @BindView(R.id.person_details_imdb_page) TextView imdbPage;
    @BindView(R.id.person_details_homepage) TextView homepage;
    @BindView(R.id.progressBar) SmoothProgressBar progressBar;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(InMovieContextWrapper.wrap(base));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle args = getIntent().getExtras();
        mId = args.getString(PERSON_ID);

        mResolver = getContentResolver();
        mCursor = mResolver.query(MovieContract.Persons.CONTENT_URI,
                null,
                MovieContract.Persons.PERSON_ID + "=?",
                new String[]{String.valueOf(mId)},
                null);
        if (mCursor != null) {
            if (mCursor.getCount() != 0) {
                loadFromDb();
            } else {
                loadFromInternet();
            }
        }

        if (mCursor != null) {
            mCursor.close();
        }
    }

    private void loadFromDb() {
        if (mCursor.moveToNext()) {
            String photoUrl = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_PROFILE_PATH));
            String nameStr = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_NAME));
            String birthdayStr = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_BIRTHDAY));
            String deathdayStr = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_DEATHDAY));
            String placeOfBirthStr = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_PLACE_OF_BIRTH));
            String biographyStr = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_BIOGRAPHY));
            String imdbId = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_BIOGRAPHY));
            String homepageUrl = mCursor.getString(mCursor.getColumnIndex(MovieContract.Persons.PERSON_BIOGRAPHY));

            Picasso.with(PersonDetailsActivity.this)
                    .load(Constants.TMDB_IMAGE_URL + Constants.PROFILE_SIZE_W185 + photoUrl)
                    .placeholder(placeholderImage)
                    .fit().centerCrop()
                    .error(placeholderImage)
                    .into(photo);

            deathLayout.setVisibility(deathdayStr == null || deathdayStr.equals("") ? View.GONE : View.VISIBLE);
            name.setText(nameStr);
            birthday.setText(StringUtils.formatReleaseDate(birthdayStr));
            placeOfBirth.setText(placeOfBirthStr);
            deathday.setText(StringUtils.formatReleaseDate(deathdayStr));
            biography.setText(biographyStr);
            imdbPage.setText(String.format(Constants.IMDB_PERSON_URL, imdbId));
            homepage.setText(homepageUrl);
        }
    }

    private void loadFromInternet() {
        if (!AppUtils.isNetworkAvailableAndConnected(this)) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        }

        updateProgressBar(true);
        String lang = PrefUtils.getFormatLocale(PersonDetailsActivity.this);

        PeopleService service = ServiceGenerator.createService(PeopleService.class);
        Call<Person> call = service.peopleDetails(mId, ServiceGenerator.API_KEY, lang);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    Person person = response.body();
                    addOrUpdate(person);

                    Picasso.with(PersonDetailsActivity.this)
                            .load(Constants.TMDB_IMAGE_URL + Constants.PROFILE_SIZE_W185 + person.getProfilePath())
                            .placeholder(placeholderImage)
                            .fit().centerCrop()
                            .error(placeholderImage)
                            .into(photo);

                    name.setText(person.getName());
                    birthday.setText(StringUtils.formatReleaseDate(person.getBirthday()));
                    deathLayout.setVisibility(person.getDeathday() == null || person.getDeathday().equals("") ? View.GONE : View.VISIBLE);
                    deathday.setText(StringUtils.formatReleaseDate(person.getDeathday()));
                    placeOfBirth.setText(person.getPlaceOfBirth());
                    biography.setText(person.getBiography());
                    imdbPage.setText(String.format(Constants.IMDB_PERSON_URL, person.getImdbId()));
                    homepage.setText(person.getHomepage());
                }

                updateProgressBar(false);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                updateProgressBar(false);
            }
        });
    }

    private void addOrUpdate(Person person) {
        ContentValues values = ContentValuesUtils.setPersonValues(person);
        if (mCursor != null) {
            if (mCursor.getCount() == 0) {
                mResolver.insert(MovieContract.Persons.CONTENT_URI, values);
            } else {
                Uri uri = MovieContract.Persons.buildPersonUri(values.getAsString(MovieContract.Persons.PERSON_ID));
                mResolver.update(uri, values, null, null);
            }
        }
    }

    public void updateProgressBar(boolean visible) {
        if (progressBar != null) {
            progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

            if (visible) {
                progressBar.progressiveStart();
            } else {
                progressBar.progressiveStop();
            }
        }
    }

    public static Intent newIntent(Context context, String id) {
        Intent intent = new Intent(context, PersonDetailsActivity.class);
        Bundle args = new Bundle();
        args.putString(PERSON_ID, id);
        intent.putExtras(args);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_update:
                loadFromInternet();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
