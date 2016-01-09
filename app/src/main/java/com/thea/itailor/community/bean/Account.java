package com.thea.itailor.community.bean;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Account {
    private int accountID = -1;
    private String authenticate;
//    private String name;
    private String email;
    private String password;
    private String portrait;
//    private Timestamp createdTime;
    private int userID = 0;
    private int rootGroupID = 0;
    private int timeLineID = 0;

    public Account(int accountID, String authenticate, String email) {
        this.accountID = accountID;
        this.authenticate = authenticate;
//        this.name = name;
        this.email = email;
    }

    public int getId() {
        return accountID;
    }

    public void setId(int id) {
        this.accountID = id;
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRootGroupID() {
        return rootGroupID;
    }

    public void setRootGroupID(int rootGroupID) {
        this.rootGroupID = rootGroupID;
    }

    public int getTimeLineID() {
        return timeLineID;
    }

    public void setTimeLineID(int timeLineID) {
        this.timeLineID = timeLineID;
    }

    @Override
    public String toString() {
        return email.split("@")[0];
    }
}
