package com.thea.itailor.community.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.bean.Account;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

/**
 * Created by Thea on 2015/9/7 0007.
 */
public class LoginModel implements ILoginModel {
    private static final String TAG = "LoginModel";

    private Activity activity;
    private IUserModel mUserModel;

    public LoginModel(Activity activity) {
        this.activity = activity;
        mUserModel = new UserModel(activity);
    }

    @Override
    public void register(final String email, final String password, final String udid) {
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader("password", password);
        Log.i("postparam", mUserModel.getBaseUrl() + Path.ACCOUNT + "?email=" + email+ headers+ContentType.TEXT_PLAIN);
        HttpUtil.post(activity, mUserModel.getBaseUrl() + Path.ACCOUNT + "?email=" + email, headers,
                ContentType.TEXT_PLAIN, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "onSuccess: " + i + "  " + new String(bytes));
                if (new String(bytes).equalsIgnoreCase("true")) {
                    mUserModel.setCurPortrait(Constant.DEFAULT_PORTRAIT);
                    login(email, password, udid);
                }
                else {
                    Toast.makeText(activity, R.string.exist_email_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: " + i);
                Toast.makeText(activity, R.string.fail_register, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void login(String email, String password, String udid) {
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("password", password);
        headers[1] = new BasicHeader("UDID", udid);
        HttpUtil.post(activity, mUserModel.getBaseUrl() + Path.LOGIN_RECORD + "?email=" + email, headers,
                ContentType.APPLICATION_JSON, null, new JsonHttpResponseHandler(HttpUtil.ENCODING) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "onSuccess: " + statusCode + response.toString());
                Gson gson = new Gson();
                mUserModel.setUserInfo(gson.fromJson(response.toString(), Account.class), true);
                Toast.makeText(activity, R.string.successful_login, Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i(TAG, "onFailure: " + statusCode);
                if (statusCode == 400)
                    Toast.makeText(activity, R.string.fail_login, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, R.string.fail_login_with_bad_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
