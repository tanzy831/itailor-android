package com.thea.itailor.community.bean;

import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class ShareItem {
    private int accountID = -1;
    private Account accountJson;
    private int shareItemID;
    private int thumbUp;
    private String description;
    private List<Image> imageJsons;
    private List<CommentJson> commentJsons;
    private List<Account> favors;
    private long createdTime;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setShareItemID(int shareItemID) {
        this.shareItemID = shareItemID;
    }

    public void setThumbUp(int thumbUp) {
        this.thumbUp = thumbUp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return accountJson;
    }

    public void setOwner(Account owner) {
        this.accountJson = owner;
    }

    public List<Account> getFavors() {
        return favors;
    }

    public void setFavors(List<Account> favors) {
        this.favors = favors;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setImageJsons(List<Image> imageJsons) {
        this.imageJsons = imageJsons;
    }

    public void setComments(List<CommentJson> commentJsons) {
        this.commentJsons = commentJsons;
    }

    public int getShareItemID() {
        return shareItemID;
    }

    public int getThumbUp() {
        return thumbUp;
    }

    public String getDescription() {
        return description;
    }

    public List<Image> getImageJsons() {
        return imageJsons;
    }

    public List<CommentJson> getComments() {
        return commentJsons;
    }

    public void addImage(Image image) {
        imageJsons.add(image);
    }
}
