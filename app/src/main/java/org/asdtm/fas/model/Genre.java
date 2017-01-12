package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("name")
    @Expose
    private String mName;

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
}
