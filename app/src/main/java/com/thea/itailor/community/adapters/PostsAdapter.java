package com.thea.itailor.community.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/5.
 */
public class PostsAdapter extends UltimateViewAdapter<PostsAdapter.PostsViewHolder> {
    private static final String TAG = "PostsAdapter";
    private List<ShareItem> posts = new ArrayList<>();

    private Context context;
    private IImageModel mImageModel;

    private OnItemSelectedListener onItemSelectedListener;

    public PostsAdapter(Context context) {
        this.context = context;
        mImageModel = new ImageModel(context);
    }

    @Override
    public PostsViewHolder getViewHolder(View view) {
        return new PostsViewHolder(view);
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new PostsViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_post, viewGroup, false));
    }

    @Override
    public int getAdapterItemCount() {
        int count = 0;
        for (ShareItem child : posts) {
            count += child.getImageJsons().size();
        }
        return count + 1;
    }

    @Override
    public long generateHeaderId(int i) {
        return i;
    }

    @Override
    public void onBindViewHolder(final PostsViewHolder holder, int position) {
        if (position >= getAdapterItemCount())
            return;
        if (position == getAdapterItemCount() - 1) {
            holder.iv.setImageResource(R.mipmap.ic_add_one);
            return;
        }

        int[] index = getItemIndex(position);
        ShareItem item = posts.get(index[0]);
        holder.itemView.setOnClickListener(v -> onItemSelectedListener.onItemSelected(v, holder, item));
        holder.iv.setImageResource(R.mipmap.loading_image);
        mImageModel.getImage(item.getImageJsons().get(index[1]).getImageID(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
//                Bitmap bitmap = ImageHelper.getImageFromByte(responseBody);
                holder.iv.setImageBitmap(ImageHelper.getImageFromByte(responseBody));
//                bitmap.recycle();
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

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public int[] getItemIndex(int position) {
        int[] index = new int[2];
        int count = 0;
        for (ShareItem child : posts) {
            count += child.getImageJsons().size();
            if (count > position) {
                index[1] = position + child.getImageJsons().size() - count;
                break;
            }
            index[0]++;
        }
        return index;
    }

    public void addItem(ShareItem item) {
        posts.add(item);
        notifyItemInserted(posts.size() - 1);
    }

    public void addItem(int position, ShareItem item) {
        posts.add(position, item);
        notifyItemInserted(position);
    }

    public void addItems(List<ShareItem> items) {
        int form = posts.size();
        posts.addAll(items);
        notifyItemRangeInserted(form, items.size());
    }

    public ShareItem getItem(int position) {
        return posts.get(position);
    }

    public static class PostsViewHolder extends UltimateRecyclerviewViewHolder {
        public ImageView iv;

        public PostsViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View v, RecyclerView.ViewHolder holder, ShareItem item);
    }
}
