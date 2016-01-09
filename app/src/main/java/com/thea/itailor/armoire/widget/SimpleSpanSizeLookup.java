package com.thea.itailor.armoire.widget;

import android.support.v7.widget.GridLayoutManager;

import com.thea.itailor.armoire.adapters.ClothAdapter;

/**
 * Created by Thea on 2015/8/3.
 */
public class SimpleSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private static final String TAG = "SimpleSpanSizeLookup";
    private final ClothAdapter adapter;
    private int spanCount;

    public SimpleSpanSizeLookup(ClothAdapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (position >= adapter.getItemCount())
            return 0;
        int[] index = adapter.getItemIndex(position);
        int childCount = adapter.getOneGroupCount(index[0]);
        if (childCount % spanCount != 0 && adapter.isLastRawChild(index, spanCount)) {
            int lastRawCount = childCount % spanCount;
            if (adapter.isLastChild(index))
                return spanCount / lastRawCount + spanCount % lastRawCount;
            else
                return spanCount / lastRawCount;
        }
        return 1;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }
}
