package com.android.testproject.aboutcanada.presenter;

import android.content.Context;
import android.util.Log;

import com.android.testproject.aboutcanada.model.GetGalleryItemResponse;
import com.android.testproject.aboutcanada.model.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.GetDataContract.IView;
import com.android.testproject.aboutcanada.presenter.GetDataContract.IApiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by ankursharma on 3/5/18.
 */

public class GalleryItemsPresenter implements GetDataContract.IPresenter, GetDataContract.IGetDataListener {

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
