package org.asdtm.inmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.asdtm.inmovie.R;
import org.asdtm.inmovie.service.ServiceGenerator;
import org.asdtm.inmovie.adapter.HomeMovieAdapter;
import org.asdtm.inmovie.model.Movie;
import org.asdtm.inmovie.model.MovieResults;
import org.asdtm.inmovie.service.DiscoverService;
import org.asdtm.inmovie.util.PrefUtils;
import org.asdtm.inmovie.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMovieFragment extends Fragment {

    private static final String TAG = HomeMovieFragment.class.getSimpleName();

    @BindView(R.id.home_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) CircularProgressBar progressBar;

    private HomeMovieAdapter mAdapter;
    private List<Movie> mMovies;
    private int mPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_recycler_view, container, false);
        ButterKnife.bind(this, v);

        String lang = PrefUtils.getFormatLocale(getActivity());

        mMovies = new ArrayList<>();
        mAdapter = new HomeMovieAdapter(mMovies);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);

        updateProgressBar(true);
        DiscoverService discover = ServiceGenerator.createService(DiscoverService.class);
        Call<MovieResults> call = discover.inTheaters(ServiceGenerator.API_KEY, lang, mPage, "popularity.desc", StringUtils.inTheatersLte(), StringUtils.inTheatersGte());
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getMovies();
                    mMovies.clear();
                    if (movies != null) {
                        if (movies.size() < 10) {
                            mMovies.addAll(movies);
                        } else {
                            for (int i = 0; i < 10; i++) {
                                Movie movie = movies.get(i);
                                mMovies.add(movie);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    updateProgressBar(false);
                } else {
                    Log.i("TAG", "Res: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.i("TAG", "Error: " + t.getMessage());
                updateProgressBar(false);
            }
        });

        return v;
    }

    private void updateProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
