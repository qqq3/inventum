package org.asdtm.fas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("profiles")
    @Expose
    private List<ProfilePhoto> profiles = null;

    public List<ProfilePhoto> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilePhoto> profiles) {
        this.profiles = profiles;
    }

}