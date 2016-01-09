package com.thea.itailor.community.model;


import com.thea.itailor.community.bean.Account;

import java.sql.Timestamp;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public interface IUserModel {

    Account getUserInfo();

    int getAccountID();

    String getAuthenticate();

    boolean getLoginState();

    int getLoginWay();

    String getCurUserName();

    int getRootGroupID();

    int getTimeLineID();

    void setLoginState(boolean loginState);

    void setLoginWay(int loginWay);

    void setCurUserEmail(String email);

    void setCurUserName(String name);

    void setUserInfo(Account user, boolean login);

    void setUserInfo(String email, String password, int accountID, String authenticate, int loginWay, boolean login);

    void setQQUserInfo(String openid, String expiresIn, String accessToken);

    String getCurPortrait();

    void setCurPortrait(String portrait);

    String getBaseUrl();

    String getServerIp();

    void setBaseUrl(String ip);

    void setLastLocationTime(Timestamp time);

    Timestamp getLastLocationTime();

    void setLoadData(boolean isLoaded);

    boolean getLoadData();

    String SP_USER_INFO = "user_info";

    String LOGIN_STATE = "login_state";
    String LOGIN_WAY = "login_way";
    String ACCOUNT_ID = "accountID";
    String AUTHENTICATE = "authenticate";

    String CUR_USER_EMAIL = "cur_user_email";
    String CUR_USER_NAME = "cur_user_name";
    String CUR_USER_PASSWORD = "cur_user_password";
    String CUR_USER_PORTRAIT = "cur_user_portrait";

    String ITAILOR_USER_EMAIL = "itailor_user_email";
    String ITAILOR_USER_NAME = "itailor_user_name";
    String ITAILOR_USER_ID = "itailor_user_id";
    String ITAILOR_USER_PASSWORD = "itailor_user_password";
    String ITAILOR_USER_PORTRAIT = "itailor_user_portrait";
    String ITAILOR_ROOT_GROUP = "itailor_root_group";
    String ITAILOR_TIME_LINE = "itailor_time_line";

    String QQ_USER_EMAIL = "qq_user_email";
    String QQ_USER_NAME = "qq_user_name";
    String QQ_USER_PORTRAIT = "qq_portrait.jpg";
    String QQ_OPEN_ID = "qq_open_id";
    String QQ_ACCESS_TOKEN = "qq_access_token";
    String QQ_EXPIRES_IN = "qq_expires_in";

    String LAST_LOCATION_TIME = "last_location_time";

    String LOAD_DATA = "load_data";

    String SERVER_IP = "server_ip";
    String BASE_URL = "base_url";

    int NO_LOGIN = 0;
    int ITAILOR_LOGIN = 1;
    int QQ_LOGIN = 2;
}
