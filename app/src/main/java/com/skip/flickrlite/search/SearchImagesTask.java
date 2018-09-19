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

    interface OnCompleteListener {
        void onComplete(ArrayList<Photo> photos);
    }

    private final OnCompleteListener mCallback;
    SearchImagesTask(OnCompleteListener listener) {
        mCallback = listener;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... queries) {

//        String query = queries[0];
        String query = "cats";

        if (!isValidQuery(query)) {
            throw new IllegalArgumentException();
        }

        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=675894853ae8ec6c242fa4c077bcf4a0&text=" + query + "&extras=url_s&format=json&nojsoncallback=1"; // TODO hide these keys!!!!

        StringBuilder result = new StringBuilder();

        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection =(HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();
            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
        } catch (MalformedURLException e) {
            // TODO show some error or retry here.
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, result.toString());

        SearchResponse searchResponse = GSON.fromJson(result.toString(), SearchResponse.class);
        return searchResponse.mPhotos.mAllPhotosInPage;
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
