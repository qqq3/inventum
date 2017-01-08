package org.asdtm.inmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.asdtm.inmovie.R;
import org.asdtm.inmovie.service.ServiceGenerator;
import org.asdtm.inmovie.adapter.TvAdapter;
import org.asdtm.inmovie.model.TV;
import org.asdtm.inmovie.model.TVResults;
import org.asdtm.inmovie.service.DiscoverService;
import org.asdtm.inmovie.service.TvService;
import org.asdtm.inmovie.util.PrefUtils;
import org.asdtm.inmovie.util.StringUtils;
import org.asdtm.inmovie.view.CustomErrorView;
import org.asdtm.inmovie.view.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragment extends Fragment {

    private int mType;
    private static final String GET_TV_TYPE = "org.asdtm.inmovie.movie.get_tv_type";
    public static final int AIRING_TODAY = 0;
    public static final int ON_TV = 1;
    public static final int POPULAR = 2;
    public static final int TOP_RATED = 3;

    private TvAdapter mAdapter;
    private List<TV> mTVs;
    private int mPage = 1;
    private String mLang;

    private Unbinder unbinder;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progressBar) CircularProgressBar progressBar;
    @BindView(R.id.error) CustomErrorView mCustomErrorView;

    public TvFragment newInstance(int type) {
        TvFragment fragment = new TvFragment();
        Bundle args = new Bundle();
        args.putInt(GET_TV_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(GET_TV_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, v);

        mLang = PrefUtils.getFormatLocale(getActivity());
        mTVs = new ArrayList<>();
        mAdapter = new TvAdapter(mTVs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                mPage = page;
                loadTv();
            }
        });
        updateProgressBar(true);
        loadTv();

        return v;
    }

    private void loadTv() {
        switch (mType) {
            case AIRING_TODAY:
                loadAiringToday();
                break;
            case ON_TV:
                loadOnTv();
                break;
            case POPULAR:
                loadPopular();
                break;
            case TOP_RATED:
                loadTopRated();
                break;
        }
    }

    private void loadAiringToday() {
        TvService airingTodayService = ServiceGenerator.createService(TvService.class);
        Call<TVResults> airingTodayCall = airingTodayService.airingToday(ServiceGenerator.API_KEY, mLang, mPage);
        airingTodayCall.enqueue(new Callback<TVResults>() {
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

    private void loadOnTv() {
        DiscoverService onTvService = ServiceGenerator.createService(DiscoverService.class);
        Call<TVResults> onTvCall = onTvService.onTv(StringUtils.getDateOnTheAir(), StringUtils.getDateToday(), "popularity.desc", mLang, mPage, ServiceGenerator.API_KEY);
        onTvCall.enqueue(new Callback<TVResults>() {
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

    private void loadPopular() {
        TvService popularService = ServiceGenerator.createService(TvService.class);
        Call<TVResults> popularCall = popularService.popular(ServiceGenerator.API_KEY, mLang, mPage);
        popularCall.enqueue(new Callback<TVResults>() {
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

    private void loadTopRated() {
        TvService topRatedService = ServiceGenerator.createService(TvService.class);
        Call<TVResults> topRatedCall = topRatedService.topRated(ServiceGenerator.API_KEY, mLang, mPage);
        topRatedCall.enqueue(new Callback<TVResults>() {
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

    private void addTv(List<TV> tvs) {
        if (tvs != null) {
            mTVs.addAll(tvs);
            mAdapter.notifyDataSetChanged();
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