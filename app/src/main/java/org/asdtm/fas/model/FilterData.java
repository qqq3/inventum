package org.asdtm.fas.model;

import java.io.Serializable;

public class FilterData implements Serializable {
    private int mType;
    private String mGenres;
    private String mSortType;
    private String mMinRating;

    public String getGenres() {
        return mGenres;
    }

    public void setGenres(String genres) {
        mGenres = genres;
    }

    public String getMinRating() {
        return mMinRating;
    }

    public void setMinRating(String minRating) {
        mMinRating = minRating;
    }

    public String getSortType() {
        return mSortType;
    }

    public void setSortType(String sortType) {
        mSortType = sortType;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
