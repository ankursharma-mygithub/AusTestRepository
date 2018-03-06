package com.android.testproject.aboutcanada.ui.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ankursharma on 3/6/18.
 */

public interface IGalleryItemHolder {
    void updateTitle(String titleText);
    void updateDescription(String descText);
    ImageView getImageView();
}
