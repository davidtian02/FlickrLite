package com.skip.flickrlite.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private final OnCompleteCallback mCallback;

    interface OnCompleteCallback {
        void onComplete(Bitmap result);
    }

    public DownloadImageTask(OnCompleteCallback callback) {
        mCallback = callback;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap icon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            icon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return icon;
    }

    protected void onPostExecute(Bitmap result) {
        if (mCallback != null) {
            mCallback.onComplete(result);
        }
    }
}