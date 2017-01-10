package org.asdtm.inmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.asdtm.inmovie.util.Constants;
import org.asdtm.inmovie.R;
import org.asdtm.inmovie.activity.MovieDetailsActivity;
import org.asdtm.inmovie.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMovieAdapter extends RecyclerView.Adapter<HomeMovieAdapter.MovieHolder> {

    private List<Movie> mMovies;

    public HomeMovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_home_movie, parent, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bindMovie(movie);
    }

    @Override
    public int getItemCount() {
        return (mMovies != null) ? mMovies.size() : 0;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private Movie mMovie;

        @BindView(R.id.home_movie_poster)
        ImageView moviePoster;
        @BindView(R.id.home_move_title)
        TextView movieTitle;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindMovie(Movie movie) {
            mMovie = movie;
            movieTitle.setText(movie.getTitle());
            Drawable placeholder = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.background_reel, null);
            Picasso.with(mContext)
                    .load(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W342 + movie.getPosterPath())
                    .placeholder(placeholder)
                    .fit().centerCrop()
                    .noFade()
                    .into(moviePoster);
        }

        @OnClick(R.id.home_movie_root)
        void startMovieDetailActivity() {
            Intent intent = MovieDetailsActivity.newIntent(mContext, mMovie.getId());
            mContext.startActivity(intent);
        }
    }
}
