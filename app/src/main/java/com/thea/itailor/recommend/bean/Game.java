package com.thea.itailor.recommend.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/9/7 0007.
 */
public class Game {
    private List<String> feedback = new ArrayList<>();

    public Game() {
    }

    public Game(List<String> feedback) {
        this.feedback = feedback;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    public void addFeedback(List<String> f) {
        feedback.addAll(f);
    }

    public boolean removeFeedback(String f) {
        return feedback.remove(f);
    }
}
