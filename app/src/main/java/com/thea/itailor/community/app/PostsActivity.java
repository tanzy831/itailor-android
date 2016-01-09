package com.thea.itailor.community.app;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.community.presenter.PostsPresenter;
import com.thea.itailor.community.view.IUltimateRecyclerView;

public class PostsActivity extends AppCompatActivity implements IUltimateRecyclerView {
    private static final String TAG = "PostsActivity";

    private PostsPresenter mPresenter;

    private UltimateRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.rv_posts);

        mRecyclerView.setDefaultOnRefreshListener(refreshListener);
//        mRecyclerView.setOnLoadMoreListener(loadMoreListener);
//        mRecyclerView.enableLoadmore();

        mPresenter = new PostsPresenter(this, this);
        mPresenter.loadPosts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

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
}
