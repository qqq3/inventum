package org.asdtm.inmovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("adult")
    @Expose
    private boolean mAdult;
    @SerializedName("backdrop_path")
    @Expose
    private String mBackdropPath;
    @SerializedName("budget")
    @Expose
    private long mBudget;
    @SerializedName("homepage")
    @Expose
    private String mHomepage;
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("imdb_id")
    @Expose
    private String mImdbId;
    @SerializedName("original_language")
    @Expose
    private String mOriginalLanguage;
    @SerializedName("original_title")
    @Expose
    private String mOriginalTitle;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("popularity")
    @Expose
    private double mPopularity;
    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;
    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;
    @SerializedName("revenue")
    @Expose
    private long mRevenue;
    @SerializedName("runtime")
    @Expose
    private int mRuntime;
    @SerializedName("status")
    @Expose
    private String mStatus;
    @SerializedName("tagline")
    @Expose
    private String mTagline;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("video")
    @Expose
    private boolean mVideo;
    @SerializedName("vote_average")
    @Expose
    private float mVoteAverage;
    @SerializedName("vote_count")
    @Expose
    private int mVoteCount;
    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> mProductionCountries;

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public long getBudget() {
        return mBudget;
    }

    public void setBudget(long budget) {
        mBudget = budget;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        mHomepage = homepage;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public long getRevenue() {
        return mRevenue;
    }

    public void setRevenue(long revenue) {
        mRevenue = revenue;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int runtime) {
        mRuntime = runtime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTagline() {
        return mTagline;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        mVideo = video;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        mVoteCount = voteCount;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public List<ProductionCountry> getProductionCountries() {
        return mProductionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        mProductionCountries = productionCountries;
    }

    @Override
    public String toString() {
        return mTitle + "(" + mReleaseDate + ")";
    }
}
