package com.thea.itailor.recommend.model;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by Thea on 2015/8/18 0018.
 */
public class FavouritesModel implements IFavouritesModel {
    private Context context;
    private IUserModel mUserModel;

    public FavouritesModel(Context context) {
        this.context = context;
        mUserModel = new UserModel(context);
    }

    @Override
    public void addItem(int recommendID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.post(context, mUserModel.getBaseUrl() + Path.FAVOURITES + "?accountID=" +
                mUserModel.getAccountID() + "&recommendID=" + recommendID, headers, null,
                ContentType.APPLICATION_JSON, handler);
    }

    @Override
    public void deleteItem(int recommendID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.delete(context, mUserModel.getBaseUrl() + Path.FAVOURITES + "?accountID=" +
                        mUserModel.getAccountID() + "&recommendID=" + recommendID, headers, handler);
    }

    @Override
    public void getFavours(JsonHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.FAVOURITES + "?accountID=" +
                mUserModel.getAccountID(), headers, null, handler);
    }
}
