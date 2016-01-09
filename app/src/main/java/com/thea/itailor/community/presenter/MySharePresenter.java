package com.thea.itailor.community.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.MyShareAdapter;
import com.thea.itailor.community.app.DetailsActivity;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.community.model.IShareModel;
import com.thea.itailor.community.model.ShareModel;
import com.thea.itailor.community.view.IRecyclerView;
import com.thea.itailor.config.ExtraName;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Thea on 2015/9/9 0009.
 */
public class MySharePresenter implements MyShareAdapter.OnItemSelectedListener {
    private static final String TAG = "MySharePresenter";

    private Context context;
    private IRecyclerView mShareView;
    private IShareModel mShareModel;

    private MyShareAdapter mAdapter;

    public MySharePresenter(Context context, IRecyclerView mShareView) {
        this.context = context;
        this.mShareView = mShareView;
        mShareModel = new ShareModel(context);
        mAdapter = new MyShareAdapter(context);
        mAdapter.setOnItemSelectedListener(this);
    }

    public void loadShareItems() {
        mShareView.setLayoutManager(new LinearLayoutManager(context));
        mShareView.setAdapter(mAdapter);
        if (Check.checkForLogin(context))
            mShareModel.getMyShareItems(httpResponseHandler);
    }

    private JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler("UTF-8") {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<ArrayList<ShareItem>>(){}.getType();
            ArrayList<ShareItem> shareItems = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(shareItems);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
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
