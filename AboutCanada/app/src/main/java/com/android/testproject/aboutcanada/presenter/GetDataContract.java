package com.android.testproject.aboutcanada.presenter;

import android.content.Context;

import com.android.testproject.aboutcanada.model.GalleryItemsList;

/**
 * Created by ankursharma on 3/5/18.
 */

public interface GetDataContract {

    interface IView {
        void onGetDataSuccess(String message, GalleryItemsList list);
        void onGetDataFailure(String message);
    }

    interface IPresenter {
        void getDataFromURL(Context context, String url);
    }

    interface IGetDataListener {
        void onSuccess(String message, GalleryItemsList list);
        void onFailure(String message);
    }

    interface IApiHelper {
        void initDownload(Context context, String url);
    }

}
