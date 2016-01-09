package com.thea.itailor.community.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.PostsAdapter;
import com.thea.itailor.community.app.DetailsActivity;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.view.IUltimateRecyclerView;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class PostsPresenter implements PostsAdapter.OnItemSelectedListener {
    private static final String TAG = "PostsPresenter";

    private Context context;
    private IUserModel mUserModel;

    private IUltimateRecyclerView mPostsView;
    private PostsAdapter mAdapter;

    public PostsPresenter(Context context, IUltimateRecyclerView mPostsView) {
        this.context = context;
        this.mPostsView = mPostsView;
        mUserModel = new UserModel(context);
        mAdapter = new PostsAdapter(context);
        mAdapter.setOnItemSelectedListener(this);
    }

    public void loadPosts() {
        mPostsView.setLayoutManager(new GridLayoutManager(context, 3));
        mPostsView.setAdapter(mAdapter);

        if (Check.checkForLogin(context)) {
            Header[] headers = new Header[1];
            headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
            HttpUtil.get(context, mUserModel.getBaseUrl() + Path.POSTS + "?accountID=" +
                    mUserModel.getAccountID(), headers, null, httpResponseHandler);
        }
    }

    public void refresh() {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.POSTS + "?accountID=" +
                mUserModel.getAccountID(), headers, null, httpResponseHandler);
    }

    public void loadMore() {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.POSTS + "?accountID=" +
                mUserModel.getAccountID(), headers, null, httpResponseHandler);
    }

    private JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<ArrayList<ShareItem>>(){}.getType();
            ArrayList<ShareItem> shareItems = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(shareItems);
            mPostsView.setRefreshing(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.i(TAG, "onFailure: " + statusCode);
//            if (Check.checkForLogin(context, statusCode))
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemSelected(View v, RecyclerView.ViewHolder holder, ShareItem item) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ExtraName.SHARE_ITEM, new Gson().toJson(
                mAdapter.getItem(holder.getAdapterPosition()), ShareItem.class));
        context.startActivity(intent);
    }
}
