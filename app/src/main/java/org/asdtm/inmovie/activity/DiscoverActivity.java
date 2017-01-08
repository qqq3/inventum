package org.asdtm.inmovie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.asdtm.inmovie.model.FilterData;
import org.asdtm.inmovie.R;
import org.asdtm.inmovie.fragment.DiscoverFragment;
import org.asdtm.inmovie.fragment.DiscoverResultFragment;

public class DiscoverActivity extends BaseActivity implements DiscoverFragment.OnDiscoverClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DiscoverFragment().newInstance();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onDiscoverClick(FilterData data) {
        DiscoverResultFragment fragment = new DiscoverResultFragment().newInstance(data);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
