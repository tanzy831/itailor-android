package com.thea.itailor.recommend.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/9.
 */
public class Suit {
    private List<RecommendItem> items = new ArrayList<>();

    public Suit() {
    }

    public List<RecommendItem> getItems() {
        return items;
    }

    public void setItems(List<RecommendItem> items) {
        this.items = items;
    }

    public void addItem(RecommendItem item) {
        items.add(item);
    }
}
