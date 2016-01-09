package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public class Feedback {
    private int clothID;
    private int grade;

    public Feedback(int clothID, int grade) {
        this.clothID = clothID;
        this.grade = grade;
    }

    public int getClothID() {
        return clothID;
    }

    public void setClothID(int clothID) {
        this.clothID = clothID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
