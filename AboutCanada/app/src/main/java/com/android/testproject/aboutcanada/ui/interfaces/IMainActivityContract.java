package com.android.testproject.aboutcanada.ui.interfaces;

import com.android.testproject.aboutcanada.model.dataObjects.GalleryItemsList;

/**
 * Created by ankursharma on 3/5/18.
 */

public interface IMainActivityContract {
    void updateTitleBar(String titleText);
    void displayListOfItems();
    void showErrorDialog(String errorMessage);
}
