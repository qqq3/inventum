package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePhoto {
    @SerializedName("aspect_ratio")
    @Expose
    private double mAspectRatio;
    @SerializedName("file_path")
    @Expose
    private String mFilePath;
    @SerializedName("height")
    @Expose
    private int mHeight;
    @SerializedName("iso_639_1")
    @Expose
    private Object mIso6391;
    @SerializedName("vote_average")
    @Expose
    private double mVoteAverage;
    @SerializedName("vote_count")
    @Expose
    private int mVoteCount;
    @SerializedName("width")
    @Expose
    private int mWidth;

    public double getAspectRatio() {
        return mAspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        mAspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public Object getIso6391() {
        return mIso6391;
    }

    public void setIso6391(Object iso6391) {
        mIso6391 = iso6391;
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

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }
}
