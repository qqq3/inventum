package org.asdtm.fas.adapter;


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

import org.asdtm.fas.R;
import org.asdtm.fas.activity.TvDetailsActivity;
import org.asdtm.fas.model.TV;
import org.asdtm.fas.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeTvAdapter extends RecyclerView.Adapter<HomeTvAdapter.TvHolder> {

    private List<TV> mTVs;

    public HomeTvAdapter(List<TV> tvs) {
        mTVs = tvs;
    }

    @Override
    public TvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_home_tv, parent, false);
        return new TvHolder(v);
    }

    @Override
    public void onBindViewHolder(TvHolder holder, int position) {
        TV tv = mTVs.get(position);
        holder.bindTv(tv);
    }

    @Override
    public int getItemCount() {
        return (mTVs != null) ? mTVs.size() : 0;
    }

    public class TvHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TV mTV;

        @BindView(R.id.home_tv_poster)
        ImageView tvPoster;
        @BindView(R.id.home_tv_title)
        TextView tvTitle;

        public TvHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindTv(TV tv) {
            mTV = tv;
            tvTitle.setText(tv.getName());
            Drawable placeholder = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.background_reel, null);
            Picasso.with(mContext)
                    .load(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W342 + tv.getPosterPath())
                    .placeholder(placeholder)
                    .fit().centerCrop()
                    .noFade()
                    .into(tvPoster);
        }

        @OnClick(R.id.home_tv_root)
        void startTvDetailActivity() {
            Intent intent = TvDetailsActivity.newIntent(mContext, mTV.getId());
            mContext.startActivity(intent);
        }
    }
}
