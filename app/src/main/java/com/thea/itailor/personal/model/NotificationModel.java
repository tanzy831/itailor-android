package com.thea.itailor.personal.model;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public class NotificationModel implements INotificationModel {
    private Context context;
    private IUserModel mUserModel;

    public NotificationModel(Context context) {
        this.context = context;
        this.mUserModel = new UserModel(context);
    }

    public void getNotifications(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.MESSAGE + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }
}
