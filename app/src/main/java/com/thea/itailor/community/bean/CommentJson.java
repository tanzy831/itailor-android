package com.thea.itailor.community.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liker on 10/08/2015 0010.
 * Group iTailor.hunters.neu.edu.cn
 */
public class CommentJson {
    private int messageID = 0;
    private int accountID = 0;
    private Account accountJson;
    private String content = "";
    private int thumbNum = 0;
    private List<Integer> favorAccounts = new ArrayList<>();
    private long createdTime;

    public Account getAccountJson() {
        return accountJson;
    }

    public void setAccountJson(Account accountJson) {
        this.accountJson = accountJson;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public List<Integer> getFavorAccounts() {
        return favorAccounts;
    }

    public void setFavorAccounts(List<Integer> favorAccounts) {
        this.favorAccounts = favorAccounts;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(int thumbNum) {
        this.thumbNum = thumbNum;
    }
}
