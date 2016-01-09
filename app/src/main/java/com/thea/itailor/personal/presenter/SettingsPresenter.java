package com.thea.itailor.personal.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.tauth.Tencent;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.Account;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.Path;
import com.thea.itailor.personal.model.ISettingsModel;
import com.thea.itailor.personal.model.SettingsModel;
import com.thea.itailor.personal.view.ISettingsView;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class SettingsPresenter {
    private static final String TAG = "SettingsPresenter";

    private Context context;
    private Tencent mTencent;

    private ISettingsView mSettingsView;
    private ISettingsModel mSettingsModel;
    private IUserModel mUserModel;

    public SettingsPresenter(Context context, ISettingsView mSettingsView) {
        this.context = context;
        this.mSettingsView = mSettingsView;
        mSettingsModel = new SettingsModel(context);
        mUserModel = new UserModel(context);
        mTencent = Tencent.createInstance(Constant.APP_ID, context.getApplicationContext());
    }

    public void loadSettingInfo() {
        mSettingsView.setAutoSync(mSettingsModel.getAutoSync());
        mSettingsView.setNotification(mSettingsModel.getNotification());
        mSettingsView.setCharacter(mSettingsModel.getCharacter().equalsIgnoreCase("外向"));
        mSettingsView.setCacheSize(mSettingsModel.getCacheSize());
    }

    public void changeSyncSetting(boolean autoSync) {
        mSettingsModel.setAutoSync(autoSync);
    }

    public void changeNotiSetting(boolean notification) {
        mSettingsModel.setNotification(notification);
    }

    public void changeCharacter(boolean isChecked) {
        mSettingsModel.setCharacter(isChecked);
    }

    public void clearCache() {
        mSettingsModel.clearCache();
        mSettingsView.setCacheSize("0");
    }

    public void logout() {
        Account account = mUserModel.getUserInfo();
        if (!mUserModel.getLoginState()) {
            Toast.makeText(context, R.string.already_logout, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUserModel.getLoginWay() == UserModel.ITAILOR_LOGIN) {
            int accountID = account.getId();
            Log.i(TAG, "accountID: " + accountID);
            Header[] headers = new Header[1];
            headers[0] = new BasicHeader("authenticate", account.getAuthenticate());
            HttpUtil.delete(context, mUserModel.getBaseUrl() + Path.LOGIN_RECORD +
                    "?accountID=" + accountID, headers, httpResponseHandler);
        }
        else if (mUserModel.getLoginWay() == UserModel.QQ_LOGIN) {
            mTencent.logout(context.getApplicationContext());
            Toast.makeText(context, R.string.successful_logout, Toast.LENGTH_SHORT).show();
            mUserModel.setLoginState(false);
        }
    }

    private AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i(TAG, "onSuccess: " + statusCode);
            Toast.makeText(context, R.string.successful_logout, Toast.LENGTH_SHORT).show();
            mUserModel.setLoginState(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i(TAG, "onFailure: " + statusCode);
            if (statusCode == 400)
                Toast.makeText(context, R.string.fail_logout, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
        }
    };
}
