package com.thea.itailor.personal.model;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public interface INotificationModel {
    void getNotifications(JsonHttpResponseHandler handler);
}
