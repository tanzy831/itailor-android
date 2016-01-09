package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class Cloth {
    private int clothID;
    private Item item;
    private int score;
    private int size = 0;

    public void setClothID(int clothID) {
        this.clothID = clothID;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getClothID() {
        return clothID;
    }

    public Item getItem() {
        return item;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
