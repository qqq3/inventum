package org.asdtm.fas.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.asdtm.fas.R;
import org.asdtm.fas.adapter.VideoAdapter;
import org.asdtm.fas.model.Video;
import org.asdtm.fas.model.VideoResults;
import org.asdtm.fas.service.MovieService;
import org.asdtm.fas.service.ServiceGenerator;
import org.asdtm.fas.service.TvService;
import org.asdtm.fas.util.PrefUtils;
import org.asdtm.fas.view.CustomErrorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosActivity extends AppCompatActivity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    private static final String ARG_TYPE = "org.asdtm.fas.video_type";
    private static final String ARG_ID = "org.asdtm.fas.id";

    public static final int TYPE_MOVIES = 0;
    public static final int TYPE_TV = 1;

    private VideoAdapter mAdapter;
    private List<Video> mVideos;
    private String mId;
    private String mLang;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progressBar) CircularProgressBar progressBar;
    @BindView(R.id.error) CustomErrorView mCustomErrorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        int type = args.getInt(ARG_TYPE);
        mId = args.getString(ARG_ID);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mVideos = new ArrayList<>();
        mAdapter = new VideoAdapter(mVideos);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLang = PrefUtils.getFormatLocale(this);
        updateProgressBar(true);
        switch (type) {
            case TYPE_MOVIES:
                loadMovieVideos();
                break;
            case TYPE_TV:
                loadTvVideos();
                break;
        }
    }

    private void loadMovieVideos() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        Call<VideoResults> call = service.movieVideos(String.valueOf(mId), ServiceGenerator.API_KEY, mLang);
        call.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body().getVideos();
                    mVideos.clear();
                    if (!videos.isEmpty()) {
                        mVideos.addAll(videos);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                updateProgressBar(false);
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {
                Log.e(TAG, "Error loading video preview", t);
                onLoadFailed(t);
            }
        });
    }

    private void loadTvVideos() {
        TvService service = ServiceGenerator.createService(TvService.class);
        Call<VideoResults> call = service.tvVideos(String.valueOf(mId), ServiceGenerator.API_KEY, mLang);
        call.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body().getVideos();
                    mVideos.clear();
                    if (!videos.isEmpty()) {
                        mVideos.addAll(videos);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                updateProgressBar(false);
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {
                Log.e(TAG, "Error loading video preview", t);
                onLoadFailed(t);
            }
        });
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

    public static Intent newIntent(Context context, int type, String id) {
        Intent intent = new Intent(context, VideosActivity.class);
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        args.putString(ARG_ID, id);
        intent.putExtras(args);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
