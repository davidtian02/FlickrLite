package com.skip.flickrlite.search;

import com.google.gson.annotations.SerializedName;

class SearchResultData {

    @SerializedName("height_s") int mHeight;
    @SerializedName("width_s") int mWidth;
    @SerializedName("url_s") String mUrl;
    // TODO add other fields

    SearchResultData() {

    }

}
