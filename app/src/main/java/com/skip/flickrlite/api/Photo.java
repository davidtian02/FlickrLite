package com.skip.flickrlite.api;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("height_s")
    int mHeight;
    @SerializedName("width_s")
    int mWidth;
    @SerializedName("url_s")
    public String mUrl;
    // TODO add other fields

    public Photo() {

    }

}
