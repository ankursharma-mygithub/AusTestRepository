package com.android.testproject.aboutcanada.presenter;

import com.android.testproject.aboutcanada.model.GalleryItemsList;

/**
 * Created by ankursharma on 3/5/18.
 */

public interface IGetDataListener {
    void onSuccess(String message, GalleryItemsList list);
    void onFailure(String message);
}
