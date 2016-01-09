package com.thea.itailor.recommend.app;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.thea.itailor.BaseFragment;
import com.thea.itailor.R;
import com.thea.itailor.recommend.presenter.RecommendPresenter;
import com.thea.itailor.recommend.view.IRecommendView;

import pl.droidsonroids.gif.GifImageView;


public class RecommendFragment extends BaseFragment implements IRecommendView {
    private static final String TAG = "RecommendFragment";

    private RecommendPresenter mPresenter;

    private ViewPager mViewPager;
    private FrameLayout mContainer;
    private GifImageView mLoadingView;
//    private Snackbar mSnackbar;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPresenter = new RecommendPresenter(activity, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);

        mContainer = (FrameLayout) view.findViewById(R.id.fl_recommend_container);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_recommend);
        mLoadingView = (GifImageView) inflater.inflate(R.layout.loading_progress, mContainer, false);

//        SpannableString str = new SpannableString("拼命加载中..."); //#bbdefb
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#42a5f5"));
//        str.setSpan(span, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mSnackbar = Snackbar.make(mViewPager, str, Snackbar.LENGTH_LONG);

        mPresenter.loadRecommends();

        return view;
    }

    @Override
    public void setPagerAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    @Override
    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void showLoadingView() {
        mContainer.addView(mLoadingView);
    }

    @Override
    public void hideLoadingView() {
        mContainer.removeView(mLoadingView);
    }

    @Override
    protected void lazyLoad() {
        if (mPresenter != null)
            mPresenter.loadData();
    }

    @Override
    protected void onInvisible() {
        if (mPresenter != null)
            mPresenter.sendFeedback();
    }
}
