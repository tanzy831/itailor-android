package com.thea.itailor.community.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Comment {
    private int messageID = 0;
    private int accountID = 0;
    private int thumbNum = 0;
    private Account accountJson;
    private String content;
    private List<Integer> favorAccounts = new ArrayList<>();
    private long createdTime;

    public Account getOwner() {
        return accountJson;
    }

    public void setOwner(Account owner) {
        this.accountJson = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
