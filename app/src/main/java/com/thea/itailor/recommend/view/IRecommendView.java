package com.thea.itailor.recommend.view;

import android.support.v4.view.PagerAdapter;

/**
 * Created by Thea on 2015/8/18 0018.
 */
public interface IRecommendView {
    void setPagerAdapter(PagerAdapter adapter);

    int getCurrentItem();

    void showLoadingView();

    void hideLoadingView();
}
