package com.skip.flickrlite.search;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.skip.flickrlite.api.Photo;
import com.skip.flickrlite.api.SearchResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchImagesTask extends AsyncTask<String, Void, ArrayList<Photo>> {

    private static final Gson GSON = new Gson();
    private static final String TAG = "SearchImagesTask";
    private static final int CONNECTION_TIMEOUT = 5000;;

    interface OnCompleteListener {
        void onComplete(ArrayList<Photo> photos);
    }

    private final OnCompleteListener mCallback;
    SearchImagesTask(OnCompleteListener listener) {
        mCallback = listener;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... queries) {
        String query = queries[0];

        if (!isValidQuery(query)) {
            throw new IllegalArgumentException();
        }

        // TODO: pull this out to a Flickr API jar
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=675894853ae8ec6c242fa4c077bcf4a0&text=" + query + "&extras=url_s&format=json&nojsoncallback=1"; // TODO hide these keys!!!!

        StringBuilder result = new StringBuilder();

        boolean success = runRequest(url, result);

        Log.d(TAG, result.toString());

        if (success) {
            SearchResponse searchResponse = GSON.fromJson(result.toString(), SearchResponse.class);
            return searchResponse.mPhotos.mAllPhotosInPage;
        } else {
            return null;
        }
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
            mCallback.onComplete(photos);
        }
    }

    private static boolean isValidQuery(String query) {
        // TODO
        return true;
    }


}
