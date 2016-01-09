package com.thea.itailor.community.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.thea.itailor.community.bean.Account;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.Path;

import java.sql.Timestamp;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class UserModel implements IUserModel {

    private SharedPreferences sp;

    public UserModel(Context context) {
        sp = context.getSharedPreferences(SP_USER_INFO, Context.MODE_PRIVATE);
    }

    @Override
    public Account getUserInfo() {
        return new Account(getAccountID(), getAuthenticate(), sp.getString(CUR_USER_EMAIL, ""));
    }

    @Override
    public int getAccountID() {
        return sp.getInt(ACCOUNT_ID, -1);
    }

    @Override
    public String getAuthenticate() {
        return sp.getString(AUTHENTICATE, "");
    }

    @Override
    public boolean getLoginState() {
        return sp.getBoolean(LOGIN_STATE, false);
    }

    @Override
    public int getLoginWay() {
        return sp.getInt(LOGIN_WAY, NO_LOGIN);
    }

    @Override
    public String getCurUserName() {
        return sp.getString(CUR_USER_NAME, "");
    }

    @Override
    public int getRootGroupID() {
        return sp.getInt(ITAILOR_ROOT_GROUP, 0);
    }

    @Override
    public int getTimeLineID() {
        return sp.getInt(ITAILOR_TIME_LINE, 0);
    }

    @Override
    public void setLoginState(boolean loginState) {
        sp.edit().putBoolean(LOGIN_STATE, loginState).apply();
    }

    @Override
    public void setLoginWay(int loginWay) {
        sp.edit().putInt(LOGIN_WAY, loginWay).apply();
    }

    @Override
    public void setCurUserEmail(String email) {
        sp.edit().putString(CUR_USER_EMAIL, email).apply();
    }

    @Override
    public void setCurUserName(String name) {
        sp.edit().putString(CUR_USER_NAME, name).apply();
        sp.edit().putString(ITAILOR_USER_NAME, name).apply();
    }

    @Override
    public void setUserInfo(Account user, boolean login) {
        if (login) {
            String email = user.getEmail();
            sp.edit().putString(CUR_USER_EMAIL, email).apply();
            sp.edit().putString(CUR_USER_NAME, email.split("@")[0]).apply();
            setLoginWay(ITAILOR_LOGIN);
        }
        sp.edit().putInt(ACCOUNT_ID, user.getId()).apply();
        sp.edit().putString(AUTHENTICATE, user.getAuthenticate()).apply();
        sp.edit().putString(ITAILOR_USER_EMAIL, user.getEmail()).apply();
        sp.edit().putString(ITAILOR_USER_PASSWORD, user.getPassword()).apply();
        sp.edit().putInt(ITAILOR_USER_ID, user.getUserID()).apply();
        sp.edit().putInt(ITAILOR_ROOT_GROUP, user.getRootGroupID()).apply();
        sp.edit().putInt(ITAILOR_TIME_LINE, user.getTimeLineID()).apply();
        setLoginState(login);
    }

    @Override
    public void setUserInfo(String email, String password, int accountID, String authenticate,
                            int loginWay, boolean login) {
        if (login) {
            setCurUserEmail(email);
            setCurUserName(email);
            sp.edit().putString(CUR_USER_PASSWORD, password).apply();
        }
        if (loginWay == ITAILOR_LOGIN) {
            sp.edit().putString(ITAILOR_USER_EMAIL, email).apply();
            sp.edit().putString(ITAILOR_USER_PASSWORD, password).apply();
        }
        else if (loginWay == QQ_LOGIN) {
            sp.edit().putString(QQ_USER_EMAIL, email).apply();
        }
        sp.edit().putInt(ACCOUNT_ID, accountID).apply();
        sp.edit().putString(AUTHENTICATE, authenticate).apply();
        setLoginWay(loginWay);
        setLoginState(login);
    }

    @Override
    public void setQQUserInfo(String openid, String expiresIn, String accessToken) {
        sp.edit().putString(QQ_OPEN_ID, openid).apply();
        sp.edit().putString(QQ_EXPIRES_IN, expiresIn).apply();
        sp.edit().putString(QQ_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurPortrait() {
        return sp.getString(ITAILOR_USER_PORTRAIT, Constant.DEFAULT_PORTRAIT);
    }

    @Override
    public void setCurPortrait(String portrait) {
        sp.edit().putString(ITAILOR_USER_PORTRAIT, portrait).apply();
    }

    @Override
    public String getBaseUrl() {
        return sp.getString(BASE_URL, Path.BASE_URL);
    }

    @Override
    public String getServerIp() {
        return sp.getString(SERVER_IP, Path.IP);
    }

    @Override
    public void setBaseUrl(String ip) {
        sp.edit().putString(SERVER_IP, ip).apply();
        sp.edit().putString(BASE_URL, "http://" + ip + ":8080/itailor/").apply();
    }

    @Override
    public void setLastLocationTime(Timestamp time) {
        sp.edit().putLong(LAST_LOCATION_TIME, time.getTime()).apply();
    }

    @Override
    public Timestamp getLastLocationTime() {
        return new Timestamp(sp.getLong(LAST_LOCATION_TIME, System.currentTimeMillis() - 2 * 3600 * 1000));
    }

    @Override
    public void setLoadData(boolean isLoaded) {
        sp.edit().putBoolean(LOAD_DATA, isLoaded).apply();
    }

    @Override
    public boolean getLoadData() {
        return sp.getBoolean(LOAD_DATA, false);
    }
}
