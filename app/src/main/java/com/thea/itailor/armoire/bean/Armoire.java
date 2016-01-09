package com.thea.itailor.armoire.bean;

import com.thea.itailor.community.bean.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Armoire {
    private Account owner;
    private List<Lattice> lattices = new ArrayList<>();

    public Armoire() {
    }
}
