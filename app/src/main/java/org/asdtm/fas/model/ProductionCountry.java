package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountry {
    @SerializedName("iso_3166_1")
    @Expose
    private String mIsoCode;
    @SerializedName("name")
    @Expose
    private String mName;

    public String getIsoCode() {
        return mIsoCode;
    }

    public void setIsoCode(String isoCode) {
        mIsoCode = isoCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }
}
