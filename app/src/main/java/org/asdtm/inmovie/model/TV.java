package org.asdtm.inmovie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class TV {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("original_language")
    @Expose
    private String mOriginalLanguage;
    @SerializedName("original_name")
    @Expose
    private String mOriginalName;
    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;
    @SerializedName("backdrop_path")
    @Expose
    private String mBackdropPath;
    @SerializedName("homepage")
    @Expose
    private String mHomepage;
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> mEpisodeRunTime = null;
    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;
    @SerializedName("in_production")
    @Expose
    private boolean mInProduction;
    @SerializedName("languages")
    @Expose
    private List<String> mLanguages = null;
    @SerializedName("number_of_episodes")
    @Expose
    private int mNumberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private int mNumberOfSeasons;
    @SerializedName("origin_country")
    @Expose
    private List<String> mOriginCountry = null;
    @SerializedName("first_air_date")
    @Expose
    private String mFirstAirDate;
    @SerializedName("last_air_date")
    @Expose
    private String mLastAirDate;
    @SerializedName("networks")
    @Expose
    private List<Network> mNetworks = null;
    @SerializedName("status")
    @Expose
    private String mStatus;
    @SerializedName("popularity")
    @Expose
    private double mPopularity;
    @SerializedName("vote_count")
    @Expose
    private int mVoteCount;
    @SerializedName("vote_average")
    @Expose
    private float mVoteAverage;
    @SerializedName("seasons")
    @Expose
    private List<Season> mSeasons = null;

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Integer> getEpisodeRunTime() {
        return mEpisodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        mEpisodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return mFirstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        mFirstAirDate = firstAirDate;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
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

    public boolean isInProduction() {
        return mInProduction;
    }

    public void setInProduction(boolean inProduction) {
        mInProduction = inProduction;
    }

    public List<String> getLanguages() {
        return mLanguages;
    }

    public void setLanguages(List<String> languages) {
        mLanguages = languages;
    }

    public String getLastAirDate() {
        return mLastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        mLastAirDate = lastAirDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Network> getNetworks() {
        return mNetworks;
    }

    public void setNetworks(List<Network> networks) {
        mNetworks = networks;
    }

    public int getNumberOfEpisodes() {
        return mNumberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        mNumberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return mNumberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        mNumberOfSeasons = numberOfSeasons;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return mOriginalName;
    }

    public void setOriginalName(String originalName) {
        mOriginalName = originalName;
    }

    public List<String> getOriginCountry() {
        return mOriginCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        mOriginCountry = originCountry;
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

    public List<Season> getSeasons() {
        return mSeasons;
    }

    public void setSeasons(List<Season> seasons) {
        mSeasons = seasons;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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
}
