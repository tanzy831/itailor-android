package com.thea.itailor.community.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.FriendsShareAdapter;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.ImageModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.view.ILoadingView;
import com.thea.itailor.community.view.IUltimateRecyclerView;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.ImageHelper;
import com.thea.itailor.widget.ItemDividerDecoration;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class CommunityPresenter implements FriendsShareAdapter.OnItemCommentListener {
    private static final String TAG = "CommunityPresenter";

    private Context context;

    private IShareModel mShareModel;
    private IUserModel mUserModel;
    private IUltimateRecyclerView mFriendsShareView;
    private ILoadingView mLoadingView;
    private FriendsShareAdapter mAdapter;

    private boolean alreadyLoad = false;

    public CommunityPresenter(Context context, IUltimateRecyclerView friendsShareView,
                              ILoadingView loadingView) {
        this.context = context;
        this.mFriendsShareView = friendsShareView;
        this.mLoadingView = loadingView;
        mShareModel = new ShareModel(context);
        mUserModel = new UserModel(context);
        mAdapter = new FriendsShareAdapter(context, mShareModel);
    }

    public void loadFriendsShare() {
        if (Check.checkForLogin(context) && !alreadyLoad) {
            mLoadingView.showLoadingView();
            mShareModel.getTimeLine(httpResponseHandler);
        }
    }

    public void loadRecyclerView() {
        mAdapter.setOnItemCommentListener(this);
//        mAdapter.setCustomLoadMoreView(LayoutInflater.from(context)
//                .inflate(R.layout.loading_progress, null));
        mFriendsShareView.setLayoutManager(new LinearLayoutManager(context));
        mFriendsShareView.setAdapter(mAdapter);
        mFriendsShareView.addItemDecoration(new ItemDividerDecoration(context,
                R.drawable.item_divider, ItemDividerDecoration.HORIZONTAL_DIVIDER));
    }

    public void refresh() {
        mShareModel.getTimeLine(httpResponseHandler);
    }

    public void loadMore() {
        mShareModel.getTimeLine(httpResponseHandler);
    }

    private JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            alreadyLoad = true;
            Type type = new TypeToken<ArrayList<ShareItem>>(){}.getType();
            ArrayList<ShareItem> shareItems = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(0, shareItems);
            mFriendsShareView.setRefreshing(false);
            mLoadingView.hideLoadingView();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.i(TAG, "onFailure: " + statusCode);
//            if (Check.checkForLogin(context, statusCode))
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
        }
    };

    public static ArrayList<ShareItem> getItemsFromJson(JSONArray jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ShareItem>>(){}.getType();
        return gson.fromJson(jsonArray.toString(), listType);
    }

    @Override
    public void onImageClick(View v, String imageID) {
        final ImageView iv = new ImageView(context);
        new ImageModel(context).getImage(imageID, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                iv.setImageBitmap(ImageHelper.getImageFromByte(responseBody, 1));
                final AlertDialog dialog = new AlertDialog.Builder(context).setView(iv).create();
                Window window = dialog.getWindow();
//                window.setLayout(200, 300);
                window.setWindowAnimations(R.style.MyGrowViewStyle);
                dialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCommentClick(View v, View parent, RecyclerView.ViewHolder holder) {
        Log.i(TAG, "onCommentClick");
        View mInputView = LayoutInflater.from(context)
                .inflate(R.layout.comment_input_box, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(mInputView).create();
        final EditText et = (EditText) mInputView.findViewById(R.id.et_comment_input);
        Button btn = (Button) mInputView.findViewById(R.id.btn_send_comment);
        btn.setOnClickListener(view -> {
            CommentJson comment = new CommentJson();
            comment.setAccountJson(mUserModel.getUserInfo());
            comment.setContent(et.getText().toString());
            mAdapter.addComment(holder, comment);
            dialog.hide();
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.MyBottomViewStyle);
        dialog.show();
    }

    @Override
    public void onFavourClick(View v, View parent, RecyclerView.ViewHolder holder) {
        Log.i(TAG, "onFavourClick");
        mAdapter.addFavour(holder, mUserModel.getUserInfo());
    }
}
