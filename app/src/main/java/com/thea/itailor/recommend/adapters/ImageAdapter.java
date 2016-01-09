package com.thea.itailor.recommend.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.ImageModel;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Thea on 2015/9/12 0012.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private static final String TAG = "ImageAdapter";

    private Context context;
    private ArrayList<String> mItems;

    private IImageModel mImageModel;

    public ImageAdapter(Context context, ArrayList<String> imageIDs) {
        this.context = context;
        this.mItems = imageIDs;
        mImageModel = new ImageModel(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new MyViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        mImageModel.getImage(mItems.get(position), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "图片onSuccess: " + statusCode);
                ((ImageView)holder.itemView).setImageBitmap(BitmapFactory.decodeByteArray(
                        responseBody, 0, responseBody.length));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "图片onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
