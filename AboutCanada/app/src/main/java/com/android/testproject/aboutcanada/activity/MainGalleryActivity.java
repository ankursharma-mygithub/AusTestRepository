package com.android.testproject.aboutcanada.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.model.GalleryItemsList;

public class MainGalleryActivity extends AppCompatActivity implements IView{

    private static final String TAG = "MainGalleryActivity";

    private RecyclerView mRecyclerView;
    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        initializeViews();
    }

    /**
     * Initialize the widgets
     */
    private void initializeViews() {
        updateTitleBar("");

        //initialize swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //TODO: When user refreshes the list by pulling down
            }
        });

        //initialize recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainGalleryActivity.this));
    }

    /**
     * To update the title of the action bar.
     * @param title
     */
    private void updateTitleBar(String title) {
        if(null != title) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onGetDataSuccess(String message, GalleryItemsList list) {
        //Todo: To implement
    }

    @Override
    public void onGetDataFailure(String message) {
        //Todo: To implement
    }
}
