package com.thea.itailor.community.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.PursuerAdapter;
import com.thea.itailor.community.bean.Account;
import com.thea.itailor.community.model.FriendModel;
import com.thea.itailor.community.model.IFriendModel;
import com.thea.itailor.community.view.IRecyclerView;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.widget.ItemDividerDecoration;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Thea on 2015/9/4 0004.
 */
public class PursuersPresenter implements PursuerAdapter.OnResponsePursueListener {
    private static final String TAG = "PursuersPresenter";
    private Context context;
    private IFriendModel mFriendsModel;

    private IRecyclerView mPursuersView;
    private static PursuerAdapter mAdapter;

    public PursuersPresenter(Context context, IRecyclerView pursuersView) {
        this.context = context;
        this.mPursuersView = pursuersView;
        mFriendsModel = new FriendModel(context);
        mAdapter = new PursuerAdapter();
        mAdapter.setOnResponsePursueListener(this);
    }

    public void loadPursuers() {
        mFriendsModel.getPursuers(mGetPursuersResponseHandler);
        mPursuersView.setLayoutManager(new LinearLayoutManager(context));
        mPursuersView.setAdapter(mAdapter);
        mPursuersView.addItemDecoration(new ItemDividerDecoration(context,
                R.drawable.item_divider, ItemDividerDecoration.HORIZONTAL_DIVIDER));
    }

    private JsonHttpResponseHandler mGetPursuersResponseHandler =
            new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<List<Account>>(){}.getType();
            List<Account> pursuers = new Gson().fromJson(response.toString(), type);
            Log.i(TAG, pursuers.size() + "");
//            Log.i(TAG, pursuers.get(0).getAuthenticate() + "  " + pursuers.get(0).getId());
            mAdapter.addItems(pursuers);
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
    public void onAccept(View v, final RecyclerView.ViewHolder holder, String email) {
        mFriendsModel.addFriend(email, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                mAdapter.removeItem(holder.getAdapterPosition());
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
//                if (Check.checkForLogin(context, statusCode))
                    Toast.makeText(context, R.string.fail_add, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onReject(View v, final RecyclerView.ViewHolder holder, String email) {
        mFriendsModel.rejectAddFriend(email, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                mAdapter.removeItem(holder.getAdapterPosition());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
//                if (Check.checkForLogin(context, statusCode))
                    Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
