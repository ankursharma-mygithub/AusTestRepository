package com.android.testproject.aboutcanada.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.ui.interfaces.IView;
import com.android.testproject.aboutcanada.ui.adapter.GalleryItemAdapter;
import com.android.testproject.aboutcanada.model.dataObjects.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.impl.GalleryItemsPresenter;

public class MainGalleryActivity extends AppCompatActivity implements IView {

    private static final String TAG = "MainGalleryActivity";

    //Base URL to download the data.
    private static final String BASE_URL = "https://dl.dropboxusercontent.com/";

    //Presenter class
    private GalleryItemsPresenter mPresenter;

    //RecyclerView
    private RecyclerView mRecyclerView;
    //Adapter class for recyclerview
    private GalleryItemAdapter mAdapter = null;

    //SwipeRefreshLayout is a part of support library and is a standard way to implement
    //common pull to refresh pattern in Android
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //To handle refreshing using swipe refresh layout.
    private boolean mRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        initializeViews();
        mPresenter = new GalleryItemsPresenter(this);
        getAndDisplayListOfItems();
    }

    /**
     * Ask presenter to get the data from the specified URL.
     */
    private void getAndDisplayListOfItems() {
        if(isNetworkConnected()) {
            if(mPresenter != null) {
                mPresenter.getDataFromURL(getApplicationContext(), BASE_URL);
            }
        } else {
                Log.e(TAG, "Device not connected to internet.");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainGalleryActivity.this);
                //To handle use case where user tries to refresh the view and device is not connected.
                int messageId = R.string.internet_dialog_msg;
                if(mRefreshing) {
                    messageId = R.string.internet_refresh_msg;
                    mRefreshing = false;
                }
                builder.setTitle(R.string.internet_dialog_title)
                        .setMessage(messageId)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();

            }
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
                getAndDisplayListOfItems();
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
        if(title != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onGetDataSuccess(String message, GalleryItemsList items) {
        if (mRecyclerView != null && items != null) {
            updateTitleBar(items.getTitle());
            if (mRefreshing == true) {
                //If user has refreshed, then notify adapter that dataset is changed.
                //ToDo: First find if the data has changed, if yes then only update that set of data, don't refresh complete data set.
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mRefreshing = false;
            } else {
                mRecyclerView.setAdapter(mAdapter = new GalleryItemAdapter(this, items));
            }
        }
    }

    @Override
    public void onGetDataFailure(String message) {
        if(mRefreshing) {
            mRefreshing = false;
        }
        Log.e(TAG, message);
    }

    /**
     * Check if the device is connected to internet or not.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}
