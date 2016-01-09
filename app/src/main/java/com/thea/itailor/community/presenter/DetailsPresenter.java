package com.thea.itailor.community.presenter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.CommentAdapter;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.bean.Image;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IImageModel;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.view.IDetailsView;
import com.thea.itailor.util.ImageHelper;
import com.thea.itailor.widget.ItemDividerDecoration;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Thea on 2015/9/8 0008.
 */
public class DetailsPresenter {
    private static final String TAG = "DetailsPresenter";

    private Context context;
    private IImageModel mImageModel;
    private IShareModel mShareModel;
    private IUserModel mUserModel;
    private IDetailsView mDetailsView;
    
    private ShareItem item;
    private CommentAdapter mAdapter;

    public DetailsPresenter(Context context, IDetailsView mDetailsView, String details) {
        this.context = context;
        this.mDetailsView = mDetailsView;
        mImageModel = new ImageModel(context);
        mUserModel = new UserModel(context);
        mShareModel = new ShareModel(context);
        item = new Gson().fromJson(details, ShareItem.class);
        mAdapter = new CommentAdapter(context, item.getComments());
    }

    public void loadDetails() {
        mDetailsView.setUserName(item.getOwner().getEmail().split("@")[0]);
        mDetailsView.setDescription(item.getDescription());
        mDetailsView.setTime(new Timestamp(item.getCreatedTime()).toString());

        mImageModel.getImage(item.getOwner().getPortrait(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                mDetailsView.setUserIcon(ImageHelper.getImageFromByte(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0; i < item.getFavors().size(); i++) {
            mDetailsView.addFavor(item.getFavors().get(i).getEmail().split("@")[0]);
        }

        final List<Image> images = item.getImageJsons();
        for (int i = 0; i < images.size(); i++) {
            final ImageView iv = (ImageView) LayoutInflater.from(context)
                    .inflate(R.layout.image_view, mDetailsView.getImagesLayout(), false);
            mImageModel.getImage(images.get(i).getImageID(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                    iv.setImageBitmap(BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length));
                    mDetailsView.addImage(iv);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
//                    if (Check.checkForLogin(context, statusCode))
                        Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
                }
            });
        }

        mDetailsView.setLayoutManager(new LinearLayoutManager(context));
        mDetailsView.setAdapter(mAdapter);
        mDetailsView.addItemDecoration(new ItemDividerDecoration(context,
                R.drawable.small_item_divider, ItemDividerDecoration.HORIZONTAL_DIVIDER));
    }

    public void onFavor() {
        mShareModel.addFavor(item.getShareItemID(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                if (new String(responseBody).equalsIgnoreCase("true")) {
                    mDetailsView.addFavor(mUserModel.getCurUserName());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                if (Check.checkForLogin(context, statusCode))
                    Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onComment() {
        View mInputView = LayoutInflater.from(context)
                .inflate(R.layout.comment_input_box, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(mInputView).create();
        final EditText et = (EditText) mInputView.findViewById(R.id.et_comment_input);
        Button btn = (Button) mInputView.findViewById(R.id.btn_send_comment);
        btn.setOnClickListener(view -> {
            CommentJson comment = new CommentJson();
            comment.setAccountJson(mUserModel.getUserInfo());
            comment.setContent(et.getText().toString());
            mAdapter.addItem(item.getShareItemID(), comment);
            dialog.hide();
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.MyBottomViewStyle);
        dialog.show();
    }
}
