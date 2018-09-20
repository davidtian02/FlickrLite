package com.skip.flickrlite.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;

import java.util.ArrayList;

class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final ArrayList<Photo> mData;
    private final RequestManager mGlide;


    SearchResultsAdapter(RequestManager glide, ArrayList<Photo> data) {
        mData = data;
        mGlide = glide;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RequestOptions options = new RequestOptions();
        // TODO add drawable placeholder
//        options.placeholder();
//        options.error(R.drawable.ic_error);

        mGlide.setDefaultRequestOptions(options)
                .load(mData.get(i).mUrl)
                .into(viewHolder.mContent);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mContent = itemView.findViewById(R.id.search_result_cell_image_view);

        }

    }
}
