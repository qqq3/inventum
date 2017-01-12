package org.asdtm.fas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.asdtm.fas.R;
import org.asdtm.fas.fragment.MoviesFragment;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends BaseActivity {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.POPULAR), getString(R.string.popular));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.TOP_RATED), getString(R.string.top_rated));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.UPCOMING), getString(R.string.upcoming));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.NOW_PLAYING), getString(R.string.now_playing));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent search = new Intent(new Intent(MoviesActivity.this, SearchActivity.class));
                startActivity(search);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
