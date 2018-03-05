package com.android.testproject.aboutcanada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.testproject.aboutcanada.R;
import com.android.testproject.aboutcanada.model.GalleryItem;
import com.android.testproject.aboutcanada.model.GalleryItemsList;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by ankursharma on 3/5/18.
 */

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.GalleryViewHolder> {

    private GalleryItemsList mItems;
    private Context mContext;

    public GalleryItemAdapter(Context context, GalleryItemsList items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.gallery_item_layout, parent, false);
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GalleryItem item = mItems.getGalleryItems().get(position);
        holder.bindGalleryItem(item);
    }

    @Override
    public int getItemCount() {
        return mItems.getGalleryItems().size();
    }


    /**
     * View holdler for recyclerview
     */
    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDetailsTextView;
        private ImageView mImageView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_title);
            mDetailsTextView = (TextView)itemView.findViewById(R.id.item_description);
            mImageView = (ImageView)itemView.findViewById(R.id.item_image);
        }

        /**
         * Binds each gallery item to individual view elements
         * @param item  :: Individual item in a  row
         */
        public void bindGalleryItem(GalleryItem item) {
            mTitleTextView.setText(item.getTitle());
            mDetailsTextView.setText(item.getDescription());
            if(null != item.getImageUrl()) {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(android.R.drawable.ic_dialog_info);
                OkHttpClient client = new OkHttpClient();

                /* USE "PICASSO" library to download image from given URL into an imageView and PICASSO takes care of caching also.
                /*
                By default Picasso uses UrlConnectionDownloader and HttpURLConnection which won't automatically
                redirect from HTTP to HTTPS (or vice versa).
                So here for now using the OkHttp3Downloader which redirects from HTTP to HTTPS or vice-versa.
                BUT IN PRODUCTION CODE, NEVER USE THIS REDIRECTION AS DOING SO AMY HAVE SERIOUS SECURITY CONSEQUENCES.
                 */
                Picasso picasso = new Picasso.Builder(mContext)
                        .downloader(new OkHttp3Downloader(client))
                        .build();
                picasso.load(item.getImageUrl()).noFade().into(mImageView, new com.squareup.picasso.Callback.EmptyCallback() {
                    @Override
                    public void onError() {
                        super.onError();
                        mImageView.setImageResource(android.R.drawable.ic_dialog_info);
                    }
                });
            } else {
                mImageView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
