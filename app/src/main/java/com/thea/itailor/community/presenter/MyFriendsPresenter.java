package com.thea.itailor.community.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.FriendAdapter;
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
 * Created by Thea on 2015/8/29 0029.
 */
public class MyFriendsPresenter implements FriendAdapter.OnItemSelectedListener {
    private static final String TAG = "MyFriendsPresenter";
    private Context context;

    private IFriendModel mFriendModel;
    private IRecyclerView mFriendsView;
    private static FriendAdapter mAdapter;

    public MyFriendsPresenter(Context context, IRecyclerView mFriendsView) {
        this.context = context;
        this.mFriendsView = mFriendsView;
        mFriendModel = new FriendModel(context);
        mAdapter = new FriendAdapter();
        mAdapter.setOnItemSelectedListener(this);
    }

    public void loadFriends() {
        mFriendModel.getFriends(0, mHttpResponseHandler);
        mFriendsView.setLayoutManager(new LinearLayoutManager(context));
        mFriendsView.setAdapter(mAdapter);
        mFriendsView.addItemDecoration(new ItemDividerDecoration(context,
                R.drawable.item_divider, ItemDividerDecoration.HORIZONTAL_DIVIDER));
    }

    private JsonHttpResponseHandler mHttpResponseHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        /*@Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<List<Account>>(){}.getType();
            List<Account> friends = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(friends);
        }*/

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<List<Account>>(){}.getType();
            List<Account> friends = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(friends);
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
    public void onItemSelected(View v, RecyclerView.ViewHolder holder, Account item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] dialogMenu = context.getResources().getTextArray(R.array.friend_edit);
        builder.setItems(dialogMenu, (dialog, which) -> {
            switch (which) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    deleteFriend(holder.getAdapterPosition(), item.getId());
                    break;
            }
        }).show();
    }

    private void deleteFriend(final int position, int accountID) {
        mFriendModel.deleteFriend(accountID, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                mAdapter.removeItem(position);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                if (Check.checkForLogin(context, statusCode))
                    Toast.makeText(context, R.string.fail_delete, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
