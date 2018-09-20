package com.skip.flickrlite.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;

import java.util.ArrayList;

class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final ArrayList<Photo> mData;
    private final RequestManager mGlide;
    private final OnItemClickCallback mCallback;

    interface OnItemClickCallback {
        void onItemClick(String url);
    }

    SearchResultsAdapter(RequestManager glide, ArrayList<Photo> data, OnItemClickCallback callback) {
        mData = data;
        mGlide = glide;
        mCallback = callback;
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

        String url = mData.get(i).mUrl;
        mGlide.setDefaultRequestOptions(options)
                .load(url)
                .into(viewHolder.mContent);
        viewHolder.setTag(url);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mContent;
        private String mTag;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mContent = itemView.findViewById(R.id.search_result_cell_image_view);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onItemClick(mTag);
                    }
                }
            });
        }

        void setTag(String tag) {
            mTag = tag;
        }

    }
}
