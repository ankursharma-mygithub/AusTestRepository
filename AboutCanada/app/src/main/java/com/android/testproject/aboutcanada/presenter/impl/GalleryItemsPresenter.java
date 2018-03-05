package com.android.testproject.aboutcanada.presenter.impl;

import android.content.Context;

import com.android.testproject.aboutcanada.model.dataManager.AppRetrofitHelper;
import com.android.testproject.aboutcanada.model.dataManager.IApiHelper;
import com.android.testproject.aboutcanada.model.dataObjects.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.interfaces.IGetDataListener;
import com.android.testproject.aboutcanada.presenter.interfaces.IPresenter;
import com.android.testproject.aboutcanada.ui.interfaces.IView;


/**
 * Created by ankursharma on 3/5/18.
 */

public class GalleryItemsPresenter implements IPresenter, IGetDataListener {

    private IView mGalleryView;
    private IApiHelper mDownloadHelper;
    
    public GalleryItemsPresenter(IView mGetDataView){
        this.mGalleryView = mGetDataView;
        mDownloadHelper = new AppRetrofitHelper(this);
    }
    
    @Override
    public void getDataFromURL(Context context, String url) {
        mDownloadHelper.initDownload(context, url);
    }

    @Override
    public void onSuccess(String message, GalleryItemsList galleryItems) {
        mGalleryView.onGetDataSuccess(message, galleryItems);
    }

    @Override
    public void onFailure(String message) {
        mGalleryView.onGetDataFailure(message);
    }


}
