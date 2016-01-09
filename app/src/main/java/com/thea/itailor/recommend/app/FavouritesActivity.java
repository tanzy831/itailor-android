package com.thea.itailor.recommend.app;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.community.view.IUltimateRecyclerView;
import com.thea.itailor.recommend.presenter.FavouritesPresenter;

public class FavouritesActivity extends AppCompatActivity implements IUltimateRecyclerView {
    private static final String TAG = "FavouritesActivity";

    private FavouritesPresenter mPresenter;

    private UltimateRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.rv_favourites);
        mRecyclerView.setOnLoadMoreListener(loadMoreListener);
        mRecyclerView.enableLoadmore();

        mPresenter = new FavouritesPresenter(this, this);
        mPresenter.loadFavourites();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        initSearchView(searchView);
        return true;
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

    public void initSearchView(SearchView searchView) {
        searchView.findViewById(R.id.search_plate)
                .setBackgroundResource(R.drawable.sv_et_background);
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete)
                searchView.findViewById(R.id.search_src_text);
        mEdit.setHint(getString(R.string.search_hint));
        TintImageView searchButton = (TintImageView) searchView.findViewById(R.id.search_go_btn);
        searchButton.setImageResource(R.mipmap.ic_action_search_white);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "submit");
//                favourites = dao.search(query);
//                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore(int itemCount, int maxLastVisiblePosition) {
            Log.i(TAG, "load more: " + itemCount + "  " + maxLastVisiblePosition);
            mPresenter.loadMore();
        }
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
