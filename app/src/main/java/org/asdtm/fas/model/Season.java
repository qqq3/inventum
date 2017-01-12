package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season {
    @SerializedName("air_date")
    @Expose
    private String mAirDate;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;
    @SerializedName("season_number")
    @Expose
    private int mSeasonNumber;

    public String getAirDate() {
        return mAirDate;
    }

    public void setAirDate(String airDate) {
        mAirDate = airDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public int getSeasonNumber() {
        return mSeasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        mSeasonNumber = seasonNumber;
    }
}
