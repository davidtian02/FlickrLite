package com.skip.flickrlite.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResponse {
    @SerializedName("photos")
    public
    Photos mPhotos;

    @SerializedName("stat")
    String mStat;


    public static class Photos {
        @SerializedName("page")
        int mPage;

        @SerializedName("pages")
        int mPages;

        @SerializedName("photo")
        public
        ArrayList<Photo> mAllPhotosInPage;
    }
}
