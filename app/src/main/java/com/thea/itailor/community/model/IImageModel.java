package com.thea.itailor.community.model;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by Thea on 2015/8/30 0030.
 */
public interface IImageModel {
    void getImage(String imageID, AsyncHttpResponseHandler handler);

    void sendImage(String imageID, AsyncHttpResponseHandler handler);
}
