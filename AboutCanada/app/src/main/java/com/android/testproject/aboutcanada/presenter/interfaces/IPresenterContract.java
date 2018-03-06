package com.android.testproject.aboutcanada.presenter.interfaces;

import android.content.Context;

import com.android.testproject.aboutcanada.ui.adapter.IGalleryItemHolder;

/**
 * Created by ankursharma on 3/5/18.
 */

public interface IPresenterContract {
    void getDataFromURL(Context context, String url);
    void onBindItemAtPosition(IGalleryItemHolder holder, int position);
    int getItemsCount();
}
