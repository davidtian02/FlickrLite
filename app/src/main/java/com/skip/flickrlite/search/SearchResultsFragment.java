package com.skip.flickrlite.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skip.flickrlite.R;

public class SearchResultsFragment extends Fragment {

    public SearchResultsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_results, container, false);
    }

    public void reloadWithQuery(String query) {
        TextView tv = getActivity().findViewById(R.id.search_content);
        tv.setText(query);
    }
}
