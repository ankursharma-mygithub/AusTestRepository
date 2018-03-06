package com.android.testproject.aboutcanada.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.testproject.aboutcanada.Constants;
import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.ui.interfaces.IMainActivityContract;
import com.android.testproject.aboutcanada.ui.adapter.GalleryItemAdapter;
import com.android.testproject.aboutcanada.presenter.impl.MainActivityPresenter;

public class MainGalleryActivity extends AppCompatActivity implements IMainActivityContract {

    private static final String TAG = "MainGalleryActivity";

    //Presenter class
    private MainActivityPresenter mPresenter;

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
        mPresenter = new MainActivityPresenter(this);
        getAndDisplayListOfItems();
    }

    /**
     * Ask presenter to get the data from the specified URL.
     */
    private void getAndDisplayListOfItems() {
        mPresenter.getDataFromURL(getApplicationContext(), Constants.BASE_URL);
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

    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRefreshing = false;
    }

    @Override
    public void displayListOfItems() {
        if(mRefreshing) {
            stopRefreshing();
        }
        mRecyclerView.setAdapter(mAdapter = new GalleryItemAdapter(mPresenter));
    }

    @Override
    public void showErrorDialog(String errorMessage) {
        Log.e(TAG, "Device not connected to internet.");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGalleryActivity.this);
        //To handle use case where user tries to refresh the view and device is not connected.
        builder.setTitle(R.string.internet_dialog_title)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void updateTitleBar(String titleText) {
        if(titleText != null && !titleText.isEmpty()) {
            getSupportActionBar().setTitle(titleText);
        }
    }




}
