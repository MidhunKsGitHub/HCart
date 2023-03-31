package com.midhun.hawks_hcart.Model;

import com.google.gson.annotations.SerializedName;
import com.midhun.hawks_hcart.View.Profile;

import java.util.List;

import kotlin.jvm.internal.SerializedIr;

public class ProfileApiModel {
    public ProfileApiModel(List<Profile> data) {
        this.data = data;
    }

    public List<Profile> getData() {
        return data;
    }

    public void setData(List<Profile> data) {
        this.data = data;
    }

    List<Profile> data;

}
