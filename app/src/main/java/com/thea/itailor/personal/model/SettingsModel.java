package com.thea.itailor.personal.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class SettingsModel implements ISettingsModel {
    public static final String TAG = "SettingsModel";

    public static final String SP_SETTINGS = "settings";
    public static final String AUTO_SYNC = "auto_sync";
    public static final String DISPLAY_NOTIFICATION = "display_notification";
    public static final String CHARACTER = "character";

    private SharedPreferences sp;
    private File mCacheDir;

    public SettingsModel(Context context) {
        sp = context.getSharedPreferences(SP_SETTINGS, Context.MODE_PRIVATE);
        mCacheDir = context.getCacheDir();
    }

    @Override
    public boolean getAutoSync() {
        return sp.getBoolean(AUTO_SYNC, false);
    }

    @Override
    public boolean getNotification() {
        return sp.getBoolean(DISPLAY_NOTIFICATION, true);
    }

    @Override
    public String getCharacter() {
        return sp.getBoolean(CHARACTER, true) ? "外向" : "内向";
    }

    @Override
    public String getCacheSize() {
        float size  = mCacheDir.length() / (1024 * 1024);
        return  size < 1 ? "0" : new DecimalFormat(".00").format(size);
    }

    @Override
    public void setAutoSync(boolean autoSync) {
        sp.edit().putBoolean(AUTO_SYNC, autoSync).apply();
    }

    @Override
    public void setNotification(boolean notification) {
        sp.edit().putBoolean(DISPLAY_NOTIFICATION, notification).apply();
    }

    @Override
    public void setCharacter(boolean isChecked) {
        sp.edit().putBoolean(CHARACTER, isChecked).apply();
    }

    @Override
    public boolean clearCache() {
        return mCacheDir.delete();
    }
}
