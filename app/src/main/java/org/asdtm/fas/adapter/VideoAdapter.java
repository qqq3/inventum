package org.asdtm.fas.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.asdtm.fas.R;
import org.asdtm.fas.activity.VideoActivity;
import org.asdtm.fas.model.Video;
import org.asdtm.fas.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private List<Video> mVideos;

    public VideoAdapter(List<Video> videos) {
        mVideos = videos;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_video, parent, false);

        return new VideoHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Video video = mVideos.get(position);
        holder.bindVideo(video);
    }

    @Override
    public int getItemCount() {
        return (mVideos != null) ? mVideos.size() : 0;
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Video mVideo;

        @BindView(R.id.video_preview) ImageView videoPreview;
        @BindView(R.id.video_name) TextView videoName;

        VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindVideo(Video video) {
            mVideo = video;
            Picasso.with(mContext)
                    .load(String.format(Constants.YOUTUBE_THUMBNAIL_URL, video.getKey()))
                    .fit().centerCrop()
                    .into(videoPreview);
            videoName.setText(video.getName());
        }

        @OnClick(R.id.video_root)
        void openVideo() {
            String url = String.format(Constants.YOUTUBE_EMBED_VIDEO_URL, mVideo.getKey());
            Intent intent = VideoActivity.newIntent(mContext, url);
            mContext.startActivity(intent);
        }

        @OnLongClick(R.id.video_root)
        boolean openOnExternalApp() {
            String url = String.format(Constants.YOUTUBE_VIDEO_URL, mVideo.getKey());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(intent);
            return true;
        }
    }
}
