package com.skip.flickrlite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageViewerFragment extends Fragment {

    public static final String IMAGE_URL_KEY = "image_url";
    public static final String TAG = "ImageViewerFragment";

    public ImageViewerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_image_viewer, container, false);

        ImageView imageView = rootView.findViewById(R.id.full_image_viewer_image_view);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String url = arguments.getString(IMAGE_URL_KEY);
            Glide.with(this).load(url).into(imageView);
        }

        return rootView;
    }
}
