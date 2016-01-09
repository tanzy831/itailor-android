package com.thea.itailor.recommend.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.Check;
import com.thea.itailor.R;
import com.thea.itailor.community.view.IUltimateRecyclerView;
import com.thea.itailor.recommend.adapters.FavouritesAdapter;
import com.thea.itailor.recommend.bean.RecommendItem;
import com.thea.itailor.recommend.model.FavouritesModel;
import com.thea.itailor.recommend.model.IFavouritesModel;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.widget.ItemDividerDecoration;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class FavouritesPresenter {
    private static final String TAG = "FavouritesPresenter";

    private Context context;
    private IFavouritesModel mFavouritesModel;

    private IUltimateRecyclerView mFavouritesView;
    private FavouritesAdapter mAdapter;

    public FavouritesPresenter(Context context, IUltimateRecyclerView mFavouritesView) {
        this.context = context;
        this.mFavouritesView = mFavouritesView;
        mFavouritesModel = new FavouritesModel(context);
        mAdapter = new FavouritesAdapter(context);
    }

    public void loadFavourites() {
        mAdapter.setCustomLoadMoreView(LayoutInflater.from(context)
                .inflate(R.layout.loading_progress, null));
        mFavouritesView.setLayoutManager(new LinearLayoutManager(context));
        mFavouritesView.setAdapter(mAdapter);
        mFavouritesView.addItemDecoration(new ItemDividerDecoration(
                context, R.drawable.item_divider, ItemDividerDecoration.HORIZONTAL_DIVIDER));

        if (Check.checkForLogin(context)) {
            mFavouritesModel.getFavours(httpResponseHandler);
        }
    }

    public void loadMore() {

    }

    private JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<ArrayList<RecommendItem>>(){}.getType();
            ArrayList<RecommendItem> favourites = new Gson().fromJson(response.toString(), type);
            mAdapter.addItems(favourites);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.i(TAG, "onFailure: " + statusCode);
//            if (Check.checkForLogin(context, statusCode))
                Toast.makeText(context, R.string.fail_by_network, Toast.LENGTH_SHORT).show();
        }
    };
}
