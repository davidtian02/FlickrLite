package com.skip.flickrlite.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;

import java.util.ArrayList;

class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final ArrayList<Photo> mData;

    SearchResultsAdapter(Context context, ArrayList<Photo> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.title.setText(galleryList.get(i).getImage_title());
//        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        viewHolder.mContent.setImageResource((galleryList.get(i).getImage_ID()));
        viewHolder.mTitle.setText(mData.get(i).mUrl);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mContent;
        private TextView mTitle;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mContent = itemView.findViewById(R.id.search_result_cell_image_view);
            mTitle = itemView.findViewById(R.id.search_result_cell_title_text_view);
        }
    }
}
