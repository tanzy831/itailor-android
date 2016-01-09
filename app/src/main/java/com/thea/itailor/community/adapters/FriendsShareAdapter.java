package com.thea.itailor.community.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.Account;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.bean.Image;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.util.AsyncImageLoader;
import com.thea.itailor.util.ImageHelper;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/5.
 */
public class FriendsShareAdapter extends UltimateViewAdapter<FriendsShareAdapter.ShareViewHolder> {
    private static final String TAG = "FriendsShareAdapter";

    private Context context;
    private IShareModel mShareModel;
    private List<ShareItem> mItems = new ArrayList<>();

    private IImageModel mImageModel;
    private IUserModel mUserModel;

    private OnItemCommentListener onItemCommentListener;

    private AsyncImageLoader mImageLoader;

    public FriendsShareAdapter(Context context, IShareModel shareModel) {
        mImageModel = new ImageModel(context);
        mShareModel = new ShareModel(context);
        mUserModel = new UserModel(context);
        mImageLoader = new AsyncImageLoader(context);
    }

    @Override
    public ShareViewHolder getViewHolder(View view) {
        return new ShareViewHolder(view);
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        return new ShareViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_friends_share, parent, false));
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
    public void onBindViewHolder(final ShareViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        if (position >= getAdapterItemCount())
            return;
        final ShareItem item = mItems.get(holder.getAdapterPosition());

        holder.name.setText(item.getOwner().getEmail().split("@")[0]);
        holder.description.setText(item.getDescription());
        holder.shareTime.setText(new Timestamp(item.getCreatedTime()).toString());

        holder.icon.setImageResource(R.mipmap.head_icon);
        mImageModel.getImage(item.getOwner().getPortrait(), new AsyncHttpResponseHandler() {
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
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });

        final List<Image> images = item.getImageJsons();
        for (int i = holder.images.getChildCount(); i < images.size(); i++) {
            final ImageView iv = (ImageView) LayoutInflater.from(context)
                .inflate(R.layout.image_view, holder.images, false);
            final String imageID = images.get(i).getImageID();
            iv.setOnClickListener(v -> onItemCommentListener.onImageClick(v, imageID));
            /*String url = mUserModel.getBaseUrl() + Path.IMAGE_SERVER + "?accountID=" +
                    mUserModel.getAccountID() + "&imageID=" + images.get(i).getImageID();
            Log.i(TAG, url + "  " + mUserModel.getAuthenticate());
            mImageLoader.downloadImage(url, mUserModel.getAuthenticate(), (bitmap, imageUrl) -> {
                    iv.setImageBitmap(bitmap);
                    holder.images.addView(iv);
                });*/
//            iv.setImageResource(R.mipmap.loading_image);
            mImageModel.getImage(imageID, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
//                    Bitmap bmp = ImageHelper.getImageFromByte(responseBody);
                    iv.setImageBitmap(ImageHelper.getImageFromByte(responseBody));
//                    bmp.recycle();
                    holder.images.addView(iv);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
                    Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
        }

//        holder.shareTime.setText(item.getShareTime(System.currentTimeMillis()));
        final List<Account> favors = item.getFavors();
        if (favors.size() <= 0)
            holder.favourPeopleLayout.setVisibility(View.GONE);
        else {
            Log.i(TAG, favors.get(0).getEmail());
            holder.favourPeopleLayout.setVisibility(View.VISIBLE);
            holder.favourPeople.setText(favors.get(0).getEmail());
        }

        final List<CommentJson> comments = item.getComments();
        if (comments.size() <= 0)
            holder.comments.setVisibility(View.GONE);
        for (int i = 0; i < comments.size(); i++) {
            CommentJson comment = comments.get(i);
            TextView tv = new TextView(context);
            String name = comment.getAccountJson().getEmail().split("@")[0];
            Log.i(TAG, name);
            String text = comment.getContent();
            SpannableStringBuilder builder = new SpannableStringBuilder(name + ": " + text);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#42a5f5"));
            builder.setSpan(span, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(builder);
            holder.comments.setVisibility(View.VISIBLE);
            holder.comments.addView(tv);
        }

        holder.comment.setOnClickListener(v ->
            onItemCommentListener.onCommentClick(v, holder.itemView, holder));

        holder.favour.setOnClickListener(v ->
            onItemCommentListener.onFavourClick(v, holder.favourPeopleLayout, holder));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    public void addItems(ArrayList<ShareItem> items) {
        int fromPosition = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(fromPosition, items.size());
    }

    public void addItems(int position, ArrayList<ShareItem> items) {
        int size = mItems.size();
        mItems.clear();
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size() - size);
    }

    public void addComment(final RecyclerView.ViewHolder holder, final CommentJson comment) {
        int position = holder.getAdapterPosition();
        final ShareItem item = mItems.get(position);

        mShareModel.addComment(item.getShareItemID(), comment, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                if (new String(responseBody).equalsIgnoreCase("true")) {
                    item.getComments().add(comment);
                    notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });
    }

    public void addFavour(final RecyclerView.ViewHolder holder, final Account user) {
        int position = holder.getAdapterPosition();
        final ShareItem item = mItems.get(position);

        mShareModel.addFavor(item.getShareItemID(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                if (new String(responseBody).equalsIgnoreCase("true")) {
                    item.getFavors().add(user);
                    notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });
    }

    public void setOnItemCommentListener(OnItemCommentListener onItemCommentListener) {
        this.onItemCommentListener = onItemCommentListener;
    }

    public class ShareViewHolder extends UltimateRecyclerviewViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView description;
        public GridLayout images;
        public TextView shareTime;
        public TextView favourPeople;
        public LinearLayout comments;
        public View favourPeopleLayout;

        public ImageButton comment;
        public ImageButton favour;

        public ShareViewHolder(final View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_user_icon);
            name = (TextView) itemView.findViewById(R.id.tv_user_name);
            description = (TextView) itemView.findViewById(R.id.tv_share_description);
            images = (GridLayout) itemView.findViewById(R.id.gl_share_images);
            shareTime = (TextView) itemView.findViewById(R.id.tv_share_time);
            favourPeople = (TextView) itemView.findViewById(R.id.tv_favour_people);
            favourPeopleLayout = itemView.findViewById(R.id.rl_favour_people);
            comments = (LinearLayout) itemView.findViewById(R.id.ll_comments);

            comment = (ImageButton) itemView.findViewById(R.id.ib_comment_icon);
            favour = (ImageButton) itemView.findViewById(R.id.ib_favour_icon);
        }
    }

    public interface OnItemCommentListener {
        void onImageClick(View v, String imageID);

        void onCommentClick(View v, View parent, RecyclerView.ViewHolder holder);

        void onFavourClick(View v, View parent, RecyclerView.ViewHolder holder);
    }
}
