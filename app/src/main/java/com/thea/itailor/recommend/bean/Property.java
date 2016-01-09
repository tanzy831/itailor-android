package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class Property {
    private int propertyID;
    private String theKey;
    private String theValue;

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public void setTheKey(String theKey) {
        this.theKey = theKey;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public String getTheKey() {
        return theKey;
    }

    public String getTheValue() {
        return theValue;
    }
}
