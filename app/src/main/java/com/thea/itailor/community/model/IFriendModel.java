package com.thea.itailor.community.model;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by Thea on 2015/9/4 0004.
 */
public interface IFriendModel {
    void getFriends(int groupID, JsonHttpResponseHandler mHandler);

    void deleteFriend(int accountID, AsyncHttpResponseHandler handler);

    void addFriend(String email, AsyncHttpResponseHandler handler);

    void rejectAddFriend(String email, AsyncHttpResponseHandler handler);

    void getPursuers(JsonHttpResponseHandler mHandler);

}
