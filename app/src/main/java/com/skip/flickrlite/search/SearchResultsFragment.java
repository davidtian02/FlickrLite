package com.skip.flickrlite.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
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
        SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(), photos);
        mRecyclerView.setAdapter(adapter);
    }
}
