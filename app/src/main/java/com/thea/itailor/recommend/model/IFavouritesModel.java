package com.thea.itailor.recommend.model;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by Thea on 2015/8/18 0018.
 */
public interface IFavouritesModel {
    void addItem(int recommendID, AsyncHttpResponseHandler handler);

    void deleteItem(int recommendID, AsyncHttpResponseHandler handler);

    void getFavours(JsonHttpResponseHandler httpResponseHandler);
}
