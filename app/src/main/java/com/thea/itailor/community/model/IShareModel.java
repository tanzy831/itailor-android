package com.thea.itailor.community.model;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.bean.ShareItem;

/**
 * Created by Thea on 2015/9/5 0005.
 */
public interface IShareModel {

    void getShareItems(Handler handler);

    void getTimeLine(JsonHttpResponseHandler handler);

    void getPosts(JsonHttpResponseHandler handler);

    void getMyShareItems(JsonHttpResponseHandler handler);

    void addShareItem(ShareItem item, AsyncHttpResponseHandler handler);

    void deleteShareItem(int shareItemID, Handler handler);

    void addComment(int shareItemID, CommentJson comment, AsyncHttpResponseHandler handler);

    void addFavor(int shareItemID, AsyncHttpResponseHandler handler);

    int SUCCESSFUL_GET_SHARE_ITEMS = 61;
    int SUCCESSFUL_DELETE_SHARE_ITEM = 65;
}
