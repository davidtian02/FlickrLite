package com.skip.flickrlite.search;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.skip.flickrlite.api.Photo;
import com.skip.flickrlite.api.SearchResponse;
import com.skip.flickrlite.model.PersistedSearchResult;
import com.skip.flickrlite.model.PersistedSearchResultDatabase;

import java.util.ArrayList;

public class QueryFromDbTask extends AsyncTask<String, Void, ArrayList<Photo>>{

    private final PersistedSearchResultDatabase mDb;
    private final OnDbQueriedCallback mCallback;

    interface OnDbQueriedCallback {
        void onQueryComplete(ArrayList<Photo> photos);
    }

    QueryFromDbTask(OnDbQueriedCallback callback, PersistedSearchResultDatabase db) {
        mCallback = callback;
        mDb = db;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... queries) {
        String query = queries[0];

        PersistedSearchResult persistedSearchResult = mDb.getDao().responseForKey(query);

        if (persistedSearchResult != null) {
            SearchResponse searchResponse = new Gson().fromJson(persistedSearchResult.response, SearchResponse.class); // should extract this out too
            return searchResponse.mPhotos.mAllPhotosInPage;
        } else {
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        if (mCallback != null) {
            mCallback.onQueryComplete(photos);
        }
    }
}
