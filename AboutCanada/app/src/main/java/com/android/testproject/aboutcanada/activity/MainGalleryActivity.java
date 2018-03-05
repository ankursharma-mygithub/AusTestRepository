package com.android.testproject.aboutcanada.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.adapter.GalleryItemAdapter;
import com.android.testproject.aboutcanada.model.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.GalleryItemsPresenter;
import com.android.testproject.aboutcanada.presenter.GetDataContract;

public class MainGalleryActivity extends AppCompatActivity implements GetDataContract.IView{

    private static final String BASE_URL = "https://dl.dropboxusercontent.com/";
    private static final String TAG = "MainGalleryActivity";
    private GalleryItemsPresenter mPresenter;
    private boolean mRefreshing = false;
    private GalleryItemAdapter mAdapter = null;
    private RecyclerView mRecyclerView;
    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        initializeViews();
        mPresenter = new GalleryItemsPresenter(this);
        getDataFromUrl();
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
                mRefreshing = true;
                getDataFromUrl();
            }
        });

        //initialize recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainGalleryActivity.this));

    }

    private void getDataFromUrl() {
        mPresenter.getDataFromURL(getApplicationContext(), BASE_URL);
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
    public void onGetDataSuccess(String message, GalleryItemsList items) {
        if (mRecyclerView != null) {
            updateTitleBar(items.getTitle());
            if (mRefreshing == true) {
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mRecyclerView.setAdapter(mAdapter = new GalleryItemAdapter(this, items));
            }
        }
    }

    @Override
    public void onGetDataFailure(String message) {
        Log.d(TAG, message);
    }
}
