package org.asdtm.inmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import org.asdtm.inmovie.R;
import org.asdtm.inmovie.fragment.HomeMovieFragment;
import org.asdtm.inmovie.fragment.HomeTVFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.rotate_reel) ImageView rotateReel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMovie = fm.findFragmentById(R.id.fragment_movie_container);

        if (fragmentMovie == null) {
            fragmentMovie = new HomeMovieFragment();
            fm.beginTransaction().add(R.id.fragment_movie_container, fragmentMovie).commit();
        }

        Fragment fragmentTv = fm.findFragmentById(R.id.fragment_tv_container);
        if (fragmentTv == null)
        {
            fragmentTv = new HomeTVFragment();
            fm.beginTransaction().add(R.id.fragment_tv_container, fragmentTv).commit();
        }
    }

    @OnClick(R.id.home_in_theaters)
    void startMoviesActivity() {
        startActivity(new Intent(this, MoviesActivity.class));
    }

    @OnClick(R.id.home_on_tv)
    void startTvActivity() {
        startActivity(new Intent(this, TvActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
