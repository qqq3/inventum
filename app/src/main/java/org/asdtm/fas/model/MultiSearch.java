package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.asdtm.fas.service.BaseResults;

import java.util.List;

public class MultiSearch extends BaseResults {

    @SerializedName("results")
    private List<MultiSearchItem> mMultiSearchItems;

    public List<MultiSearchItem> getMultiSearchItems() {
        return mMultiSearchItems;
    }

    public void setMultiSearchItems(List<MultiSearchItem> multiSearchItems) {
        mMultiSearchItems = multiSearchItems;
    }

    public class MultiSearchItem {
        @SerializedName("media_type")
        @Expose
        private String mMediaType;
        @SerializedName("id")
        @Expose
        private String mId;
        @SerializedName("title")
        @Expose
        private String mTitle;
        @SerializedName("original_title")
        @Expose
        private String mOriginalTitle;
        @SerializedName("release_date")
        @Expose
        private String mReleaseDate;
        @SerializedName("name")
        @Expose
        private String mName;
        @SerializedName("original_name")
        @Expose
        private String mOriginalName;
        @SerializedName("poster_path")
        @Expose
        private String mPosterPath;
        @SerializedName("profile_path")
        @Expose
        private String mProfilePath;
        @SerializedName("first_air_date")
        @Expose
        private String mFirstAirDate;
        @SerializedName("vote_average")
        @Expose
        private double mVoteAverage;
        @SerializedName("vote_count")
        @Expose
        private int mVoteCount;

        @SerializedName("known_for")
        @Expose
        private List<KnownFor> mKnownFor;

        public String getFirstAirDate() {
            return mFirstAirDate;
        }

        public void setFirstAirDate(String firstAirDate) {
            mFirstAirDate = firstAirDate;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getMediaType() {
            return mMediaType;
        }

        public void setMediaType(String mediaType) {
            mMediaType = mediaType;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getOriginalName() {
            return mOriginalName;
        }

        public void setOriginalName(String originalName) {
            mOriginalName = originalName;
        }

        public String getOriginalTitle() {
            return mOriginalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            mOriginalTitle = originalTitle;
        }

        public String getPosterPath() {
            return mPosterPath;
        }

        public void setPosterPath(String posterPath) {
            mPosterPath = posterPath;
        }

        public String getProfilePath() {
            return mProfilePath;
        }

        public void setProfilePath(String profilePath) {
            mProfilePath = profilePath;
        }

        public String getReleaseDate() {
            return mReleaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            mReleaseDate = releaseDate;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public double getVoteAverage() {
            return mVoteAverage;
        }

        public void setVoteAverage(double voteAverage) {
            mVoteAverage = voteAverage;
        }

        public int getVoteCount() {
            return mVoteCount;
        }

        public void setVoteCount(int voteCount) {
            mVoteCount = voteCount;
        }

        public List<KnownFor> getKnownFor() {
            return mKnownFor;
        }

        public void setKnownFor(List<KnownFor> knownFor) {
            mKnownFor = knownFor;
        }

        public class KnownFor {
            @SerializedName("title")
            private String mTitle;
            @SerializedName("name")
            private String mName;

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public String getTitle() {
                return mTitle;
            }

            public void setTitle(String title) {
                mTitle = title;
            }
        }
    }
}
