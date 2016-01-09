package com.thea.itailor.personal.view;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface ISettingsView {
    boolean getAutoSync();

    boolean getNotification();

    void setAutoSync(boolean autoSync);

    void setNotification(boolean notification);

    void setCharacter(boolean character);

    void setCacheSize(String cacheSize);
}
