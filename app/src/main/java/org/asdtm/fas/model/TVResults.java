package org.asdtm.fas.model;

import com.google.gson.annotations.SerializedName;

import org.asdtm.fas.service.BaseResults;

import java.util.List;

public class TVResults extends BaseResults{
    @SerializedName("results")
    private List<TV> mTVs;

    public List<TV> getTVs() {
        return mTVs;
    }

    public void setTVs(List<TV> TVs) {
        mTVs = TVs;
    }
}
