package com.thea.itailor.recommend.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public class FeedbackJson {
    private List<Feedback> feedBackItems = new ArrayList<>();

    public FeedbackJson(List<Feedback> feedBackItems) {
        this.feedBackItems = feedBackItems;
    }
}
