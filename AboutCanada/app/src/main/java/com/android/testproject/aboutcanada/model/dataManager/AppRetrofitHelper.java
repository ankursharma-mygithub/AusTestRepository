package com.android.testproject.aboutcanada.model.dataManager;

import android.content.Context;
import android.util.Log;

import com.android.testproject.aboutcanada.model.dataObjects.GalleryItem;
import com.android.testproject.aboutcanada.model.dataObjects.GalleryItemsList;
import com.android.testproject.aboutcanada.presenter.interfaces.IGetDataListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
* Created by ankursharma on 3/5/18.
*/

public class AppRetrofitHelper implements IDataHelperContract {

     private IGetDataListener mGetDataListener;

    public AppRetrofitHelper(IGetDataListener listener) {
        mGetDataListener = listener;
    }

    @Override
    public void initDownload(Context context, String url) {
        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        /*Create handle for retrofit instance*/
        GetGalleryItemResponse service = retrofitInstance.create(GetGalleryItemResponse.class);

        /*Call the method with parameter in the interface to get the employee data*/
        Call<GalleryItemsList> call = service.getItems();
        Log.d(TAG, call.request().url() + "");

        if (null != call) {
            call.enqueue(new Callback<GalleryItemsList>() {
                @Override
                public void onResponse(Call<GalleryItemsList> call, Response<GalleryItemsList> response) {
                    GalleryItemsList itemsList = response.body();
                    if (null != itemsList) {
                        removeNullItemsFromList(itemsList);
                        mGetDataListener.onSuccess("List Size: " + itemsList.getGalleryItems().size(), itemsList);
                    }
                }

                @Override
                public void onFailure(Call<GalleryItemsList> call, Throwable t) {
                    mGetDataListener.onFailure("Failed");
                }
            });
        }
    }

/**
 * To remove null items from the list.
 * @param items
 */
private void removeNullItemsFromList(GalleryItemsList items) {
    //Clear out null values from the list
    for (GalleryItem item : new ArrayList<>(items.getGalleryItems())) {
        if (item.getDescription() == null && item.getImageUrl() == null && item.getTitle() == null) {
            items.getGalleryItems().remove(item);
        }
    }
}
}
