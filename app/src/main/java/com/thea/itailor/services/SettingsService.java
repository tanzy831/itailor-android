package com.thea.itailor.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.personal.model.ISettingsModel;
import com.thea.itailor.personal.model.SettingsModel;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class SettingsService extends Service {
    private static final String TAG = "SettingsService";
    private final IBinder mBinder = new LocalBinder();

    private IUserModel mUserModel;
    private ISettingsModel mSettingsModel;

    public SettingsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mUserModel = new UserModel(getApplicationContext());
        mSettingsModel = new SettingsModel(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        post();
        return super.onStartCommand(intent, flags, startId);
    }

    public void post() {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
        HttpUtil.post(getApplicationContext(), mUserModel.getBaseUrl() + Path.DEVELOPER_SYSTEM +
                "?accountID=" + mUserModel.getAccountID() + "&sync=" + mSettingsModel.getAutoSync()
                + "&push=" + mSettingsModel.getNotification() + "&character=" +
                mSettingsModel.getCharacter(), headers, null, ContentType.APPLICATION_JSON,
                mHttpResponseHandler);
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
        public SettingsService getService() {
            return SettingsService.this;
        }
    }
}
