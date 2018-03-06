package com.android.testproject.aboutcanada.model.dataManager;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by ankursharma on 3/6/18.
 */

public interface IImageDownloader {
    void downloadImage(Context context, String url, ImageView imageView);
}
