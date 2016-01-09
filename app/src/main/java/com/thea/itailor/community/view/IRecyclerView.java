package com.thea.itailor.community.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public interface IRecyclerView {

    void setAdapter(RecyclerView.Adapter adapter);

    void setLayoutManager(RecyclerView.LayoutManager layoutManager);

    void addItemDecoration(RecyclerView.ItemDecoration itemDecoration);
}
