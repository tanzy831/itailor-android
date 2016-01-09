package com.thea.itailor.community.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Group {
    private int id;
    private String name;
    private int accessControl;
    private List<Account> members = new ArrayList<>();
}
