package com.skip.flickrlite.search;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;

import java.util.ArrayList;

public class SearchResultsFragment extends Fragment implements SearchImagesTask.OnCompleteListener {

    private RecyclerView mRecyclerView;

    public SearchResultsFragment() {
        // keep public default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_results, container, false);

        mRecyclerView = rootView.findViewById(R.id.search_results_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2); // TODO this is prolly variable based on the images themselves

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int margin = getResources().getDimensionPixelOffset(R.dimen.regular_spacing);
                outRect.set(margin, margin, margin, margin);
            }
        });

        mRecyclerView.setLayoutManager(layoutManager);


        return rootView;
    }

    public void reloadWithQuery(String query) {
        // TODO add ability to view more page than just first page
        new SearchImagesTask(this).execute(query);
    }

    // TODO add failed result action, and retry logic
    @Override
    public void onComplete(ArrayList<Photo> photos) {
        SearchResultsAdapter adapter = new SearchResultsAdapter(Glide.with(this), photos);
        mRecyclerView.setAdapter(adapter);
    }
}
