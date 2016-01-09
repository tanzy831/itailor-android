package com.thea.itailor.community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.Image;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/5.
 */
public class MyShareAdapter extends RecyclerView.Adapter<MyShareAdapter.ShareViewHolder> {
    private static final String TAG = "MyShareAdapter";

    private Context context;
    private IImageModel mImageModel;

    private List<ShareItem> mItems = new ArrayList<>();

    private OnItemSelectedListener onItemSelectedListener;

    public MyShareAdapter(Context context) {
        this.context = context;
        mImageModel = new ImageModel(context);
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_my_share, parent, false));
    }

    @Override
    public void onBindViewHolder(final ShareViewHolder holder, int position) {
        ShareItem item = mItems.get(position);

        holder.itemView.setOnClickListener(v -> onItemSelectedListener.onItemSelected(v, holder, item));

        holder.description.setText(item.getDescription());
        holder.createdTime.setText(new Timestamp(item.getCreatedTime()).toString());
        holder.favourCount.setText(item.getFavors().size()+"");
        holder.commentCount.setText(item.getComments().size() + "");

        final List<Image> images = item.getImageJsons();
        for (int i = holder.images.getChildCount(); i < images.size(); i++) {
            final ImageView iv = (ImageView) LayoutInflater.from(context)
                    .inflate(R.layout.small_image_view, holder.images, false);
//            iv.setImageResource(R.mipmap.loading_image);
            mImageModel.getImage(images.get(i).getImageID(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
//                    Bitmap bitmap = ImageHelper.getImageFromByte(responseBody);
                    iv.setImageBitmap(ImageHelper.getImageFromByte(responseBody));
//                    bitmap.recycle();
                    holder.images.addView(iv);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
                    Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void addItems(ArrayList<ShareItem> items) {
        int fromPosition = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(fromPosition, items.size());
    }

    public void addItems(int position, ArrayList<ShareItem> items) {
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public ShareItem getItem(int position) {
        return mItems.get(position);
    }

    public class ShareViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private GridLayout images;
        private TextView createdTime;
        private TextView favourCount;
        private TextView commentCount;

        public ShareViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.tv_my_share_description);
            images = (GridLayout) itemView.findViewById(R.id.gl_my_share_images);
            createdTime = (TextView) itemView.findViewById(R.id.tv_share_time);
            favourCount = (TextView) itemView.findViewById(R.id.tv_favour_count);
            commentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View v, RecyclerView.ViewHolder holder, ShareItem item);
    }
}
