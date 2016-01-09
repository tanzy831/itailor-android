package com.thea.itailor.personal.model;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface ISettingsModel {
    boolean getAutoSync();

    boolean getNotification();

    String getCharacter();

    String getCacheSize();

    void setAutoSync(boolean autoSync);

    void setNotification(boolean notification);

    void setCharacter(boolean isChecked);

    boolean clearCache();
}
