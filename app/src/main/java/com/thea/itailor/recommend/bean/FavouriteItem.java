package com.thea.itailor.recommend.bean;

import com.thea.itailor.community.bean.Account;

import java.sql.Timestamp;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class FavouriteItem {
    private int id;
    private Account owner;
    private String description;
    private int[] imageID;
    private Timestamp createdTime;
}
