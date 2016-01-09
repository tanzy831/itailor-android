package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class RecommendItem {
    private int recommendID;
    private Cloth cloth;
//    private int grade;
    private long timeStamp;
    private String reason;
    private boolean favor = false;
    private boolean dress = false;

    public int getRecommendID() {
        return recommendID;
    }

    public void setRecommendID(int recommendID) {
        this.recommendID = recommendID;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isFavor() {
        return favor;
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    public boolean isDress() {
        return dress;
    }

    public void setDress(boolean dress) {
        this.dress = dress;
    }
}
