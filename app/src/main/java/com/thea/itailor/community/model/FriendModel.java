package com.thea.itailor.community.model;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by Thea on 2015/9/4 0004.
 */
public class FriendModel implements IFriendModel {
    private static final String TAG = "FriendModel";

    private Context context;
    private IUserModel mUserModel;

    public FriendModel(Context context) {
        this.context = context;
        mUserModel = new UserModel(context);
    }

    @Override
    public void getFriends(int groupID, JsonHttpResponseHandler mHandler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.FRIENDS + "?accountID=" +
                mUserModel.getAccountID() + "&groupID=" + mUserModel.getRootGroupID(), headers,
                null, mHandler);
    }

    @Override
    public void deleteFriend(int accountID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.delete(context, mUserModel.getBaseUrl() + Path.MEMBER + "?accountID=" +
                mUserModel.getAccountID() + "&accountID2=" + accountID, headers, handler);

    }

    @Override
    public void addFriend(String email, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.post(context, mUserModel.getBaseUrl() + Path.PURSUER + "?accountID=" +
                mUserModel.getAccountID() + "&email=" + email, headers, null,
                ContentType.APPLICATION_JSON, handler);
    }

    @Override
    public void rejectAddFriend(String email, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.delete(context, mUserModel.getBaseUrl() + Path.PURSUER + "?accountID=" +
                mUserModel.getAccountID() + "&email=" + email, headers, handler);
    }

    @Override
    public void getPursuers(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.PURSUER + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }
}
