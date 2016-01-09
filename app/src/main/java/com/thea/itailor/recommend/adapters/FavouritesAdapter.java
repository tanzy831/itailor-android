package com.thea.itailor.recommend.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.recommend.bean.RecommendItem;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/5.
 */
public class FavouritesAdapter extends UltimateViewAdapter<FavouritesAdapter.FavouritesViewHolder> {
    private static final String TAG = "FavouritesAdapter";

    private List<RecommendItem> mItems = new ArrayList<>();

    private Context context;
    private IImageModel mImageModel;

    public FavouritesAdapter(Context context) {
        this.context = context;
        mImageModel = new ImageModel(context);
    }

    @Override
    public FavouritesViewHolder getViewHolder(View view) {
        return new FavouritesViewHolder(view);
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new FavouritesViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_favourite, viewGroup, false));
    }

    @Override
    public int getAdapterItemCount() {
        return mItems.size();
    }

    @Override
    public long generateHeaderId(int i) {
        return i;
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        if (position >= getAdapterItemCount())
            return;
        RecommendItem item = mItems.get(position);
        holder.tv.setText(item.getReason());
        holder.time.setText(new Timestamp(item.getTimeStamp()).toString());
        mImageModel.getImage(item.getCloth().getItem().getClothingImages().get(0).getImageID(),
                new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                holder.iv.setImageBitmap(BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    public void addItems(ArrayList<RecommendItem> items) {
        int fromPosition = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(fromPosition, items.size());
    }

    public void addItems(int position, ArrayList<RecommendItem> items) {
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public RecommendItem getItem(int position) {
        return mItems.get(position);
    }

    public static class FavouritesViewHolder extends UltimateRecyclerviewViewHolder {
        public ImageView iv;
        public TextView tv;
        public TextView time;

        public FavouritesViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_collection_image);
            tv = (TextView) itemView.findViewById(R.id.tv_collection_description);
            time = (TextView) itemView.findViewById(R.id.tv_collection_time);
        }
    }
}
