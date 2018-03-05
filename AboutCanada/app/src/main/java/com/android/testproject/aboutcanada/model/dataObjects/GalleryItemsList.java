package com.android.testproject.aboutcanada.model.dataObjects;

import com.android.testproject.aboutcanada.model.dataObjects.GalleryItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankursharma on 2/15/18.
 */

/**
 * Model class representing the parsed data from JSON file
 */
public class GalleryItemsList {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<GalleryItem> rows = null;

    public GalleryItemsList() {

    }

    public List<GalleryItem> getGalleryItems() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public void setGalleryItems(List<GalleryItem> items) {
        rows = items;
    }

}
