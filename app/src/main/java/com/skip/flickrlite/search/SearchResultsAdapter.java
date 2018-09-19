package com.skip.flickrlite.search;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;

import java.util.ArrayList;

class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final ArrayList<Photo> mData;

    interface OnItemClickCallback {
        void onItemClick(String url);
    }

    SearchResultsAdapter(ArrayList<Photo> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        new DownloadImageTask(viewHolder).execute(mData.get(i).mUrl);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements DownloadImageTask.OnCompleteCallback {
        private ImageView mContent;
        private ProgressBar mProgress;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mContent = itemView.findViewById(R.id.search_result_cell_image_view);
            mProgress = itemView.findViewById(R.id.search_result_cell_progress_bar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onComplete(Bitmap result) {
            mContent.setImageBitmap(result);
            mContent.setVisibility(View.VISIBLE);

            mProgress.setVisibility(View.GONE);
        }
    }
}
