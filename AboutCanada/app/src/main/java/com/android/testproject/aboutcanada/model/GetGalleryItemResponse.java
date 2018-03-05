package com.android.testproject.aboutcanada.model;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ankursharma on 2/18/18.
 */

/**
 * Used by the Retrofit library.
 */
public interface GetGalleryItemResponse {
    @GET("/s/2iodh4vg0eortkl/contacts.json")
    Call<GalleryItemsList> getItems();
}
