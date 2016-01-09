package com.thea.itailor.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.bean.BodyData;
import com.thea.itailor.recommend.model.BodyDataModel;
import com.thea.itailor.recommend.model.IBodyDataModel;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;

public class BodyDataService extends Service {
    private static final String TAG = "BodyDataService";
    private final IBinder mBinder = new LocalBinder();

    private IUserModel mUserModel;
    private IBodyDataModel mBodyDataModel;

    public BodyDataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mUserModel = new UserModel(getApplicationContext());
        mBodyDataModel = new BodyDataModel(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        post();
        return super.onStartCommand(intent, flags, startId);
    }

    public void post() {
        try {
            StringEntity entity = new StringEntity(
                    new Gson().toJson(mBodyDataModel.getBodyData2(), BodyData.class), HttpUtil.ENCODING);
            Log.i(TAG, mBodyDataModel.getBodyData2().getAge() + "  " + mBodyDataModel.getBodyData2().getWeight() +"");
            Header[] headers = new Header[1];
            headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
            HttpUtil.post(getApplicationContext(), mUserModel.getBaseUrl() + Path.BODY_STATUS +
                    "?accountID=" + mUserModel.getAccountID(), headers, entity, ContentType.APPLICATION_JSON,
                    mHttpResponseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private AsyncHttpResponseHandler mHttpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            Log.i(TAG, "onSuccess: " + i);
            stopSelf();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Log.i(TAG, "onFailure: " + i);
            stopSelf();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public BodyDataService getService() {
            return BodyDataService.this;
        }
    }
}
