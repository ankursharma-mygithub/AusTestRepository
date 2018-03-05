package com.android.testproject.aboutcanada.activity;

import com.android.testproject.aboutcanada.model.GalleryItemsList;

/**
 * Created by ankursharma on 3/5/18.
 */

public interface IView {
    void onGetDataSuccess(String message, GalleryItemsList list);
    void onGetDataFailure(String message);
}
