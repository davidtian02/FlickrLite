package com.skip.flickrlite.search;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.skip.flickrlite.HomeActivity;
import com.skip.flickrlite.R;
import com.skip.flickrlite.api.Photo;
import com.skip.flickrlite.model.PersistedSearchResultDao;
import com.skip.flickrlite.model.PersistedSearchResultDatabase;

import java.util.ArrayList;

public class SearchResultsFragment extends Fragment implements SearchImagesTask.OnCompleteListener, SearchResultsAdapter.OnItemClickCallback, QueryFromDbTask.OnDbQueriedCallback {

    private RecyclerView mRecyclerView;
    private RequestManager mGlide;
    private PersistedSearchResultDatabase mDb;
    private static final String DB_NAME = "search_result_db";

    public SearchResultsFragment() {
        // keep public default constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mDb = Room.databaseBuilder(context, PersistedSearchResultDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_results, container, false);

        mGlide = Glide.with(this);

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
        new SearchImagesTask(this, mDb).execute(query);
    }

    // TODO add failed result action, and retry logic
    @Override
    public void onComplete(final ArrayList<Photo> photos, final String query) {
        if (photos != null) {
            setupAdapter(photos);
        } else {
            // the user couldn't connect to network, most likely
            attemptToLoadFromDb(query);
        }
    }

    private void attemptToLoadFromDb(final String query) {
        new QueryFromDbTask(SearchResultsFragment.this, mDb).execute(query);
    }

    private void setupAdapter(ArrayList<Photo> photos) {
        SearchResultsAdapter adapter = new SearchResultsAdapter(mGlide, photos, SearchResultsFragment.this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String url) {
        FragmentActivity activity = getActivity();
        if (activity instanceof HomeActivity) { // a bit hacky
            ((HomeActivity)activity).goToFullScreenOn(url);
        }
    }

    @Override
    public void onQueryComplete(final ArrayList<Photo> photos) {
        if (photos != null) {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Failed to Search");
            alertDialog.setMessage("Due to a network issue, we couldn't search images. Would you like to attempt to load previously searched images for your search term?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            setupAdapter(photos);
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            Toast.makeText(getActivity(), "Failed to search. Bad internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
