package com.thea.itailor.community.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.CommentJson;
import com.thea.itailor.community.bean.ShareItem;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Thea on 2015/9/5 0005.
 */
public class ShareModel implements IShareModel {
    private static final String TAG = "ShareModel";

    private Context context;
    private IUserModel mUserModel;

    public ShareModel(Context context) {
        this.context = context;
        mUserModel = new UserModel(context);
    }

    @Override
    public void getShareItems(final Handler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.SHARE_ITEM + "?accountID=" +
                mUserModel.getAccountID(), headers, null, new JsonHttpResponseHandler(HttpUtil.ENCODING) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "onSuccess: " + statusCode);
                Message message = new Message();
                message.what = SUCCESSFUL_GET_SHARE_ITEMS;
                message.obj = response;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addShareItem(ShareItem item, final AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        item.setAccountID(mUserModel.getAccountID());
        item.setOwner(mUserModel.getUserInfo());

        try {
            StringEntity entity = new StringEntity(
                    new Gson().toJson(item, ShareItem.class), HttpUtil.ENCODING);
            HttpUtil.post(context, mUserModel.getBaseUrl() + Path.SHARE_ITEM + "?accountID=" +
                mUserModel.getAccountID(), headers, entity, ContentType.APPLICATION_JSON, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteShareItem(int shareItemID, final Handler handler) {
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        headers[1] = new BasicHeader("shareItemID", shareItemID+"");
        HttpUtil.delete(context, mUserModel.getBaseUrl() + Path.SHARE_ITEM + "?accountID=" +
                mUserModel.getAccountID(), headers, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                Message message = new Message();
                message.what = SUCCESSFUL_DELETE_SHARE_ITEM;
                message.obj = new String(responseBody);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(context, R.string.fail_delete, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getTimeLine(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.TIMELINE + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }

    @Override
    public void getPosts(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.POSTS + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }

    @Override
    public void getMyShareItems(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.MY_SHARE_ITEM + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }

    @Override
    public void addComment(int shareItemID, CommentJson commentJson, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());

        try {
            StringEntity entity = new StringEntity(
                    new Gson().toJson(commentJson, CommentJson.class), HttpUtil.ENCODING);
            HttpUtil.post(context, mUserModel.getBaseUrl() + Path.SHARE_ITEM_COMMENT + "?accountID=" +
                    mUserModel.getAccountID() + "&shareItemID=" + shareItemID, headers, entity,
                    ContentType.APPLICATION_JSON, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFavor(int shareItemID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());

        HttpUtil.post(context, mUserModel.getBaseUrl() + Path.SHARE_ITEM_FAVOR + "?accountID=" +
                mUserModel.getAccountID() + "&shareItemID=" + shareItemID, headers, null,
                ContentType.APPLICATION_JSON, handler);
    }
}
