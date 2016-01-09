package com.thea.itailor.community.view;

import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public interface IUltimateRecyclerView {

    void setAdapter(UltimateViewAdapter adapter);

    void setLayoutManager(RecyclerView.LayoutManager layoutManager);

    void addItemDecoration(RecyclerView.ItemDecoration itemDecoration);

    void setRefreshing(boolean refreshing);

}
