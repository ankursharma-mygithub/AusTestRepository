package com.android.testproject.aboutcanada.presenter.impl;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.model.dataManager.AppPicassoImageDownloader;
import com.android.testproject.aboutcanada.model.dataManager.AppRetrofitHelper;
import com.android.testproject.aboutcanada.model.dataManager.IDataHelperContract;
import com.android.testproject.aboutcanada.model.dataObjects.GalleryItem;
import com.android.testproject.aboutcanada.model.dataObjects.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.interfaces.IGetDataListener;
import com.android.testproject.aboutcanada.presenter.interfaces.IPresenterContract;
import com.android.testproject.aboutcanada.ui.adapter.IGalleryItemHolder;
import com.android.testproject.aboutcanada.ui.interfaces.IMainActivityContract;

/**
 * Created by ankursharma on 3/5/18.
 */

public class MainActivityPresenter implements IPresenterContract, IGetDataListener {

    //Main Activity
    private IMainActivityContract mGalleryActivity;
    //JSON download helper
    private IDataHelperContract mDownloadHelper;
    //Model data
    private GalleryItemsList mGalleryItemsList;
    private Context mContext;
    
    public MainActivityPresenter(IMainActivityContract galleryView){
        mGalleryActivity = galleryView;
        mDownloadHelper = new AppRetrofitHelper(this);
    }
    
    @Override
    public void getDataFromURL(Context context, String url) {
        mContext = context;
        //Check if network is connected
        if(isNetworkConnected() && (mDownloadHelper != null)) {
            mDownloadHelper.initDownload(context, url);
        } else {
            mGalleryActivity.showErrorDialog(mContext.getString(R.string.internet_not_connected));
        }
    }

    @Override
    public void onBindItemAtPosition(IGalleryItemHolder holder, int position) {
        GalleryItem item = mGalleryItemsList.getGalleryItems().get(position);
        holder.updateDescription(item.getDescription());
        holder.updateTitle(item.getTitle());
        holder.getImageView().setVisibility(View.INVISIBLE);
        String imageUrl = item.getImageUrl();
        if(imageUrl != null && !imageUrl.isEmpty()) {
            new AppPicassoImageDownloader().downloadImage(mContext, imageUrl, holder.getImageView());
        }
    }

    @Override
    public int getItemsCount() {
        return mGalleryItemsList.getGalleryItems().size();
    }

    @Override
    public void onSuccess(String message, GalleryItemsList galleryItems) {
        mGalleryItemsList = galleryItems;
        mGalleryActivity.displayListOfItems();
    }

    @Override
    public void onFailure(String message) {
        mGalleryActivity.showErrorDialog(message);
    }

    /**
     * Check if the device is connected to internet or not.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
