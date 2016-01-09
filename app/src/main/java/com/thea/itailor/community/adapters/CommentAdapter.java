package com.thea.itailor.community.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/9/8 0008.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private static final String TAG = "CommentAdapter";
    private List<CommentJson> items = new ArrayList<>();

    private IShareModel mShareModel;
    private IImageModel mImageModel;

    public CommentAdapter(Context context, List<CommentJson> items) {
        this.items = items;
        mShareModel = new ShareModel(context);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mImageModel = new ImageModel(parent.getContext());
        return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentJson item = items.get(position);
        holder.name.setText(item.getAccountJson().getEmail().split("@")[0]);
        holder.description.setText(item.getContent());
        holder.time.setText(new Timestamp(item.getCreatedTime()).toString());

        holder.icon.setImageResource(R.mipmap.head_icon);
        mImageModel.getImage(item.getAccountJson().getPortrait(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
//                Bitmap bitmap = ImageHelper.getImageFromByte(responseBody);
                holder.icon.setImageBitmap(ImageHelper.getImageFromByte(responseBody));
//                bitmap.recycle();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(int shareItemID, final CommentJson comment) {
        mShareModel.addComment(shareItemID, comment, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                if (new String(responseBody).equalsIgnoreCase("true")) {
                    items.add(comment);
                    notifyItemChanged(items.size() - 1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView description;
        public TextView time;

        public CommentViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_user_icon);
            name = (TextView) itemView.findViewById(R.id.tv_user_name);
            time = (TextView) itemView.findViewById(R.id.tv_comment_time);
            description = (TextView) itemView.findViewById(R.id.tv_comment_description);
        }
    }
}
