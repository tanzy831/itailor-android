package com.thea.itailor.community.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.thea.itailor.R;
import com.thea.itailor.community.model.ILoginModel;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.LoginModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.view.ILoginView;
import com.thea.itailor.config.Constant;
import com.thea.itailor.util.ImageHelper;
import com.thea.itailor.util.MD5;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    public static final int QQ_LOGIN_SUCCESSFUL = 24;

    private static Activity activity;
    private static ILoginModel mLoginModel;
    private static IUserModel mUserModel;

    private Tencent mTencent;
    private ILoginView mLoginView;

    private MyHandler mHandler = new MyHandler();

    public LoginPresenter(Activity activity, ILoginView mLoginView) {
        LoginPresenter.activity = activity;
        this.mLoginView = mLoginView;
        mUserModel = new UserModel(activity);
        mLoginModel = new LoginModel(activity);
        mTencent = Tencent.createInstance(Constant.APP_ID, activity.getApplicationContext());
    }

    public void register() {
        if (checkEmailAndPassword()) {
            String email = mLoginView.getEmail();
            String password = mLoginView.getPassword();
            TelephonyManager tm = (TelephonyManager)
                    activity.getSystemService(Context.TELEPHONY_SERVICE);
            mLoginModel.register(email, password, tm.getDeviceId());
        }
    }

    public void login() {
        if (checkEmailAndPassword()) {
            String email = mLoginView.getEmail();
            String password = mLoginView.getPassword();
            TelephonyManager tm = (TelephonyManager)
                    activity.getSystemService(Context.TELEPHONY_SERVICE);
            mLoginModel.login(email, password, tm.getDeviceId());
        }
    }

    public void qqLogin() {
        mTencent.login(activity, "all", new BaseUiListener());
    }

    public boolean checkEmailAndPassword() {
        mLoginView.setEmailError(null);
        mLoginView.setPasswordError(null);
        return checkPassword(mLoginView.getPassword()) & checkEmail(mLoginView.getEmail());
    }

    public boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            mLoginView.setEmailError(activity.getString(R.string.error_field_required));
            return false;
        } else if (!isEmailValid(email)) {
            mLoginView.setEmailError(activity.getString(R.string.error_invalid_email));
            return false;
        }
        return true;
    }

    public boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            mLoginView.setPasswordError(activity.getString(R.string.error_field_required));
            return false;
        }
        else if (!isPasswordValid(password)) {
            mLoginView.setPasswordError(activity.getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        return email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            try {
                JSONObject jsonObject = (JSONObject) o;
                Log.i(TAG, jsonObject.toString());
                mUserModel.setQQUserInfo(jsonObject.getString("openid"),
                        jsonObject.getString("expires_in"), jsonObject.getString("access_token"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            QQToken qqToken = mTencent.getQQToken();
            UserInfo userInfo = new UserInfo(activity.getApplicationContext(), qqToken);
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    final JSONObject response = (JSONObject) o;
                    Log.i(TAG, response.toString());
                    mUserModel.setLoginWay(UserModel.QQ_LOGIN);
                    if (response.has("nickname")) {
                        try {
                            mUserModel.setCurUserName(response.getString("nickname"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap bitmap = ImageHelper.getImageFromInternet(response.getString("figureurl_qq_2"));
                                ImageHelper.saveImageToStore(bitmap, Constant.DIRECTORY_USER, Constant.CUR_PORTRAIT);
                                mUserModel.setLoginState(true);
                                mHandler.sendEmptyMessage(QQ_LOGIN_SUCCESSFUL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                @Override
                public void onError(UiError uiError) {
                }

                @Override
                public void onCancel() {
                }
            });
        }

        @Override
        public void onError(UiError uiError) {
            Log.i(TAG, "onError");
            Toast.makeText(activity, R.string.fail_login, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "onCancel");
        }
    }

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == QQ_LOGIN_SUCCESSFUL) {
                TelephonyManager tm = (TelephonyManager)
                        activity.getSystemService(Context.TELEPHONY_SERVICE);
                mLoginModel.register(MD5.getMD5(mUserModel.getCurUserName()) + "@qq.com", "", tm.getDeviceId());
                activity.onBackPressed();
            }
            super.handleMessage(msg);
        }
    }
}
