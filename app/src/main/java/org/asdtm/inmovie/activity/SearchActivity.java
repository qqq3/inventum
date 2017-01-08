package org.asdtm.inmovie.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.asdtm.inmovie.InMovieContextWrapper;
import org.asdtm.inmovie.util.Constants;
import org.asdtm.inmovie.R;
import org.asdtm.inmovie.service.ServiceGenerator;
import org.asdtm.inmovie.model.MultiSearch;
import org.asdtm.inmovie.service.SearchService;
import org.asdtm.inmovie.util.PrefUtils;
import org.asdtm.inmovie.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindDrawable(R.drawable.background_reel) Drawable placeholderImage;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.search_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.search_view) SearchView mSearchView;

    private SearchAdapter mAdapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(InMovieContextWrapper.wrap(base));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setupActionBar();
        setupSearchView();

        List<MultiSearch.MultiSearchItem> searchItems = new ArrayList<>();
        mAdapter = new SearchAdapter(searchItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 2) {
                    mAdapter.getFilter().filter(newText);
                    return true;
                }

                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return true;
            }
        });
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    class SearchAdapter extends RecyclerView.Adapter<SearchHolder> implements Filterable {

        private List<MultiSearch.MultiSearchItem> mMultiSearchItems;

        public SearchAdapter(List<MultiSearch.MultiSearchItem> multiMultiSearchItems) {
            mMultiSearchItems = multiMultiSearchItems;
        }

        @Override
        public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_search, parent, false);
            return new SearchHolder(v);
        }

        @Override
        public void onBindViewHolder(SearchHolder holder, int position) {
            MultiSearch.MultiSearchItem item = mMultiSearchItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return (mMultiSearchItems != null) ? mMultiSearchItems.size() : 0;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    final FilterResults results = new FilterResults();
                    String lang = PrefUtils.getFormatLocale(SearchActivity.this);
                    SearchService service = ServiceGenerator.createService(SearchService.class);
                    Call<MultiSearch> call = service.multiSearch(charSequence.toString(), ServiceGenerator.API_KEY, lang, "1");
                    call.enqueue(new Callback<MultiSearch>() {
                        @Override
                        public void onResponse(Call<MultiSearch> call, Response<MultiSearch> response) {
                            if (response.isSuccessful()) {
                                List<MultiSearch.MultiSearchItem> movies = response.body().getMultiSearchItems();
                                results.values = movies;
                                results.count = movies != null ? movies.size() : 0;
                                mMultiSearchItems.clear();
                                if (movies != null) {
                                    mMultiSearchItems.addAll(movies);
                                    notifyDataSetChanged();
                                }
                            } else {
                                Log.i(TAG, "Error: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<MultiSearch> call, Throwable t) {
                            Log.i(TAG, "Error: " + t.getMessage());
                        }
                    });

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (filterResults.values != null) {
                        mMultiSearchItems.addAll((Collection<? extends MultiSearch.MultiSearchItem>) filterResults.values);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }

    public class SearchHolder extends RecyclerView.ViewHolder {

        private final String TYPE_MOVIE = "movie";
        private final String TYPE_TV = "tv";
        private final String TYPE_PERSON = "person";

        private Context mContext;
        private MultiSearch.MultiSearchItem mItem;

        @BindView(R.id.search_poster) ImageView poster;
        @BindView(R.id.search_name) TextView name;
        @BindView(R.id.search_original_name) TextView originalName;
        @BindView(R.id.search_vote_average) TextView voteAverage;
        @BindView(R.id.search_vote_count) TextView voteCount;

        public SearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindItem(MultiSearch.MultiSearchItem item) {
            mItem = item;
            switch (item.getMediaType()) {
                case TYPE_MOVIE:
                        Picasso.with(mContext).setIndicatorsEnabled(true);
                        Picasso.with(mContext)
                                .load(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W154 + item.getPosterPath())
                                .placeholder(placeholderImage)
                                .fit().centerCrop()
                                .noFade()
                                .error(placeholderImage)
                                .into(poster);
                    name.setText(mContext.getString(R.string.search_name, item.getTitle(), item.getMediaType()));
                    originalName.setText(mContext.getString(R.string.search_original_name, item.getOriginalTitle(), StringUtils.getYear(item.getReleaseDate())));
                    voteAverage.setText(String.valueOf(item.getVoteAverage()));
                    voteCount.setText(String.valueOf(item.getVoteCount()));
                    break;
                case TYPE_TV:
                        Picasso.with(mContext).setIndicatorsEnabled(true);
                        Picasso.with(mContext)
                                .load(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W154 + item.getPosterPath())
                                .placeholder(placeholderImage)
                                .fit().centerCrop()
                                .noFade()
                                .error(placeholderImage)
                                .into(poster);
                    name.setText(mContext.getString(R.string.search_name, item.getName(), item.getMediaType()));
                    originalName.setText(mContext.getString(R.string.search_original_name, item.getOriginalName(), StringUtils.getYear(item.getReleaseDate())));
                    voteAverage.setText(String.valueOf(item.getVoteAverage()));
                    voteCount.setText(String.valueOf(item.getVoteCount()));
                    break;
                case TYPE_PERSON:
                        Picasso.with(mContext).setIndicatorsEnabled(true);
                        Picasso.with(mContext)
                                .load(Constants.TMDB_IMAGE_URL + Constants.PROFILE_SIZE_W185 + item.getProfilePath())
                                .placeholder(placeholderImage)
                                .fit().centerCrop()
                                .noFade()
                                .error(placeholderImage)
                                .into(poster);
                    name.setText(item.getName());
                    originalName.setText("");
                    voteAverage.setText("");
                    voteCount.setText("");
                    break;
            }
        }

        @OnClick(R.id.search_root)
        void startDetailActivity() {
            switch (mItem.getMediaType()) {
                case "movie":
                    Intent movie = MovieDetailsActivity.newIntent(SearchActivity.this, mItem.getId());
                    startActivity(movie);
                    break;
                case "tv":
                    Intent tv = TvDetailsActivity.newIntent(SearchActivity.this, mItem.getId());
                    startActivity(tv);
                    break;
                case "person":
                    Intent person = PersonDetailsActivity.newIntent(SearchActivity.this, mItem.getId());
                    startActivity(person);
                    break;
            }
        }
    }
}
