package com.thea.itailor.community.model;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicHeader;

import java.io.File;

/**
 * Created by Thea on 2015/9/5 0005.
 */
public class ImageModel implements IImageModel {
    private static final String TAG = "ImageModel";

    private Context context;
    private IUserModel mUserModel;

    public ImageModel(Context context) {
        this.context = context;
        mUserModel = new UserModel(context);
    }

    @Override
    public void getImage(String imageID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.get(context, mUserModel.getBaseUrl() + Path.IMAGE_SERVER + "?accountID=" +
                mUserModel.getAccountID() + "&imageID=" + imageID, headers, null, handler);
    }

    @Override
    public void sendImage(String imageID, AsyncHttpResponseHandler handler) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        Log.i(TAG, mUserModel.getAuthenticate());

        String path = Path.BASE_PATH + Constant.DIRECTORY_ARMOIRE + "/" + imageID;
        FileEntity entity = new FileEntity(new File(path), ContentType.APPLICATION_OCTET_STREAM);
        HttpUtil.post(context, mUserModel.getBaseUrl() + Path.IMAGE_SERVER + "?accountID=" +
                mUserModel.getAccountID() + "&imageID=account" + mUserModel.getAccountID() + "_" + imageID,
                headers, entity, ContentType.APPLICATION_OCTET_STREAM, handler);
    }
}
