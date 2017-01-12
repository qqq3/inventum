package org.asdtm.fas.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.asdtm.fas.model.FilterData;
import org.asdtm.fas.R;
import org.asdtm.fas.service.ServiceGenerator;
import org.asdtm.fas.adapter.MoviesAdapter;
import org.asdtm.fas.adapter.TvAdapter;
import org.asdtm.fas.model.Movie;
import org.asdtm.fas.model.MovieResults;
import org.asdtm.fas.model.TV;
import org.asdtm.fas.model.TVResults;
import org.asdtm.fas.service.DiscoverService;
import org.asdtm.fas.util.PrefUtils;
import org.asdtm.fas.view.CustomErrorView;
import org.asdtm.fas.view.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverResultFragment extends Fragment {

    private static final String TAG = DiscoverResultFragment.class.getSimpleName();

    private static final String ARG_FILTER_DATA = "org.asdtm.fas.discover.filter_date";

    public static final int TYPE_MOVIES = 0;
    public static final int TYPE_TV = 1;

    private FilterData mFilterData;
    private String mGenres;
    private String mSortBy;
    private String mMinRating;
    private String mLang;

    private MoviesAdapter mMoviesAdapter;
    private List<Movie> mMovies;
    private TvAdapter mTvAdapter;
    private List<TV> mTVs;
    private int mPage = 1;

    private Unbinder unbinder;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progressBar) CircularProgressBar progressBar;
    @BindView(R.id.error) CustomErrorView mCustomErrorView;

    public DiscoverResultFragment newInstance(FilterData data) {
        DiscoverResultFragment fragment = new DiscoverResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER_DATA, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterData = (FilterData) getArguments().getSerializable(ARG_FILTER_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, v);

        mLang = PrefUtils.getFormatLocale(getActivity());
        final int type = mFilterData.getType();
        mGenres = mFilterData.getGenres();
        mGenres = (mGenres == null) ? "" : mGenres;
        mSortBy = mFilterData.getSortType();
        mMinRating = mFilterData.getMinRating();

        if (type == TYPE_MOVIES) {
            mMovies = new ArrayList<>();
            mMoviesAdapter = new MoviesAdapter(mMovies);
        } else {
            mTVs = new ArrayList<>();
            mTvAdapter = new TvAdapter(mTVs);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(type == TYPE_MOVIES ? mMoviesAdapter : mTvAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                mPage = page;

                if (type == TYPE_MOVIES) {
                    discoverMovies();
                } else {
                    discoverTv();
                }
            }
        });

        updateProgressBar(true);
        if (type == TYPE_MOVIES) {
            discoverMovies();
        } else {
            discoverTv();
        }

        return v;
    }

    private void discoverMovies() {
        DiscoverService discoverMoviesService = ServiceGenerator.createService(DiscoverService.class);
        Call<MovieResults> discoverMoviesCall = discoverMoviesService.discoverMovie(ServiceGenerator.API_KEY, mLang, mPage, mSortBy, mMinRating, mGenres);
        discoverMoviesCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getMovies();
                    addMovies(movies);
                } else {
                    Log.i("TAG", "Res: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                onLoadFailed(t);
            }
        });
    }

    private void discoverTv() {
        DiscoverService discoverTvService = ServiceGenerator.createService(DiscoverService.class);
        Call<TVResults> discoverTvCall = discoverTvService.discoverTv(ServiceGenerator.API_KEY, mLang, mPage, mSortBy, mMinRating, mGenres);
        discoverTvCall.enqueue(new Callback<TVResults>() {
            @Override
            public void onResponse(Call<TVResults> call, Response<TVResults> response) {
                if (response.isSuccessful()) {
                    List<TV> tvs = response.body().getTVs();
                    addTv(tvs);
                } else {
                    Log.i("TAG", "Res: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TVResults> call, Throwable t) {
                onLoadFailed(t);
            }
        });

    }

    private void addMovies(List<Movie> movies) {
        if (movies != null) {
            mMovies.addAll(movies);
            mMoviesAdapter.notifyDataSetChanged();
        }
        updateProgressBar(false);
    }

    private void addTv(List<TV> tvs) {
        if (tvs != null) {
            mTVs.addAll(tvs);
            mTvAdapter.notifyDataSetChanged();
        }
        updateProgressBar(false);
    }

    private void updateProgressBar(boolean visibility) {
        if (progressBar != null) {
            progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    private void onLoadFailed(Throwable t) {
        mCustomErrorView.setError(t);
        mCustomErrorView.setVisibility(View.VISIBLE);
        updateProgressBar(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
