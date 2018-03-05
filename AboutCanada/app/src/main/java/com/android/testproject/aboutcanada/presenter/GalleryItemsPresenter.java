package com.android.testproject.aboutcanada.presenter;

import android.content.Context;

import com.android.testproject.aboutcanada.activity.IView;
import com.android.testproject.aboutcanada.model.GalleryItemsList;

import java.util.List;

/**
 * Created by ankursharma on 3/5/18.
 */

public class GalleryItemsPresenter implements IPresenter, IGetDataListener {

    private IView mGalleryView;
    
    public GalleryItemsPresenter(IView mGetDataView){
        this.mGalleryView = mGetDataView;
    }
    
    @Override
    public void getDataFromURL(Context context, String url) {
        //Todo: Initialize the retrofit call
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
