package com.skip.flickrlite.search;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.skip.flickrlite.api.Photo;
import com.skip.flickrlite.api.SearchResponse;
import com.skip.flickrlite.model.PersistedSearchResult;
import com.skip.flickrlite.model.PersistedSearchResultDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchImagesTask extends AsyncTask<String, Void, ArrayList<Photo>> {

    private static final Gson GSON = new Gson();
    private static final String TAG = "SearchImagesTask";
    private static final int CONNECTION_TIMEOUT = 4000;;

    private final PersistedSearchResultDatabase mDb;
    private String mQuery;

    interface OnCompleteListener {
        void onComplete(ArrayList<Photo> photos, String originalQuery);
    }

    private final OnCompleteListener mCallback;
    SearchImagesTask(OnCompleteListener listener, PersistedSearchResultDatabase db) {
        mCallback = listener;
        mDb = db;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... queries) {
        mQuery = queries[0];

        if (!isValidQuery(mQuery)) {
            throw new IllegalArgumentException();
        }

        // TODO: pull this out to a Flickr API jar
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=675894853ae8ec6c242fa4c077bcf4a0&text=" + mQuery + "&extras=url_s&format=json&nojsoncallback=1"; // TODO hide these keys!!!!

        StringBuilder result = new StringBuilder();

        boolean success = runRequest(url, result);

        String strResponse = result.toString();
        Log.d(TAG, strResponse);

        if (success) {
            backUpToDatabase(strResponse);

            SearchResponse searchResponse = GSON.fromJson(strResponse, SearchResponse.class);
            return searchResponse.mPhotos.mAllPhotosInPage;
        } else {
            return null;
        }
    }

    private void backUpToDatabase(final String response) {
        // don't block.
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mDb.getDao().responseForKey(mQuery) == null) {
                    PersistedSearchResult entry = new PersistedSearchResult();
                    entry.searchQuery = mQuery;
                    entry.response = response;
                    mDb.getDao().insert(entry);
                } else {
                    // TODO update it
                }
            }
        }).start();
    }

    private boolean runRequest(String url, StringBuilder result) {
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection =(HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
        } catch (MalformedURLException e) {
            // TODO show some error or retry here.
            Log.e(TAG, e.getMessage());
            return false;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    // TODO publish a wait dialog

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        if (mCallback != null) {
            mCallback.onComplete(photos, mQuery);
        }
    }

    private static boolean isValidQuery(String query) {
        // TODO
        return true;
    }


}
