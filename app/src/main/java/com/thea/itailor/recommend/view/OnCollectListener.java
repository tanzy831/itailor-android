package com.thea.itailor.recommend.view;

import android.view.View;

import com.thea.itailor.recommend.bean.Suit;

/**
 * Created by Thea on 2015/8/28 0028.
 */
public interface OnCollectListener {

    void onCollect(View v, Suit suit, int flag);
}
