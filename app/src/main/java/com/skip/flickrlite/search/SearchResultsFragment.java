package com.skip.flickrlite.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.skip.flickrlite.R;

import java.util.ArrayList;

public class SearchResultsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public SearchResultsFragment() {

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
//        TextView tv = getActivity().findViewById(R.id.search_content);
//        tv.setText(query);
        String example = "{\n" +
                "    \"farm\": 1,\n" +
                "    \"height_s\": \"240\",\n" +
                "    \"id\": \"33094387050\",\n" +
                "    \"isfamily\": 0,\n" +
                "    \"isfriend\": 0,\n" +
                "    \"ispublic\": 1,\n" +
                "    \"owner\": \"29314320@N07\",\n" +
                "    \"secret\": \"89019909cc\",\n" +
                "    \"server\": \"667\",\n" +
                "    \"title\": \"Stanley T. 3-16-2017\",\n" +
                "    \"url_s\": \"https://farm1.staticflickr.com/667/33094387050_89019909cc_m.jpg\",\n" +
                "    \"width_s\": \"180\"\n" +
                "}";

        Gson gson = new Gson();
        SearchResultData searchResultData = gson.fromJson(example, SearchResultData.class);
        ArrayList<SearchResultData> data = new ArrayList<>(); // FIXME

        for (int i=0; i<20; i++) {
            data.add(searchResultData);
        }

        SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(), data);
        mRecyclerView.setAdapter(adapter);
    }
}
