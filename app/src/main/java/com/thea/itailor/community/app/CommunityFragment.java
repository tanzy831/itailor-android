package com.thea.itailor.community.app;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.BaseFragment;
import com.thea.itailor.R;
import com.thea.itailor.community.presenter.CommunityPresenter;
import com.thea.itailor.community.view.ILoadingView;
import com.thea.itailor.community.view.IUltimateRecyclerView;


public class CommunityFragment extends BaseFragment implements IUltimateRecyclerView, ILoadingView {
    private static final String TAG = "CommunityFragment";

    private CommunityPresenter mPresenter;

    private UltimateRecyclerView mRecyclerView;
    private Snackbar mSnackbar;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    public CommunityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPresenter = new CommunityPresenter(activity, this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        setActionBar(view);

        view.findViewById(R.id.fab_scan_posts).setOnClickListener(fabClickListener);

        mRecyclerView = (UltimateRecyclerView) view.findViewById(R.id.rv_community);

        mPresenter.loadRecyclerView();

        mRecyclerView.setDefaultOnRefreshListener(refreshListener);
//        mRecyclerView.setOnLoadMoreListener(loadMoreListener);
//        mRecyclerView.enableLoadmore();

        SpannableString str = new SpannableString("拼命加载中..."); //#bbdefb
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#42a5f5"));
        str.setSpan(span, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSnackbar = Snackbar.make(mRecyclerView, str, Snackbar.LENGTH_LONG);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.loadFriendsShare();
    }

    public void setActionBar(View view) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
            view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(activity.getString(R.string.friends_share));
    }

    private OnClickListener fabClickListener = v -> startActivity(new Intent(getActivity(), PostsActivity.class));

    private OnRefreshListener refreshListener = () -> {
        Log.i(TAG, "refresh");
        mPresenter.refresh();
    };

    private OnLoadMoreListener loadMoreListener = (itemCount, maxLastVisiblePosition) -> {
        Log.i(TAG, "load more: " + itemCount + "  " + maxLastVisiblePosition);
        mPresenter.loadMore();
    };


    @Override
    public void setAdapter(UltimateViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mRecyclerView.setRefreshing(refreshing);
    }

    @Override
    protected void lazyLoad() {
        if (mPresenter != null)
            mPresenter.loadFriendsShare();
    }

    @Override
    public void showLoadingView() {
        mSnackbar.show();
    }

    @Override
    public void hideLoadingView() {
        mSnackbar.dismiss();
    }
}
