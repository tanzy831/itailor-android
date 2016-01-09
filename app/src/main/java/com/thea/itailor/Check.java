package com.thea.itailor;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.thea.itailor.community.app.LoginActivity;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.recommend.app.PreferenceActivity;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class Check {
    public static boolean checkForLogin(Context context) {
        if (new UserModel(context).getAccountID() == -1 || !new UserModel(context).getLoginState()) {
            Toast.makeText(context, R.string.need_login, Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }
        return true;
    }

    public static boolean checkForLogin(Context context, int statusCode) {
        if (statusCode == 400) {
            Toast.makeText(context, R.string.overdue_login, Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }
        return true;
    }

    public static boolean checkForPreference(boolean hasTested, Context context) {
        if (!hasTested) {
            context.startActivity(new Intent(context, PreferenceActivity.class));
            return false;
        }
        return true;
    }
}
