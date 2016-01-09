package com.thea.itailor.armoire.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.armoire.adapters.MyExpandableAdapter;

/**
 * Created by Thea on 2015/8/17 0017.
 */
public interface IArmoireView {
    RecyclerView.Adapter getAdapter();

    void setAdapter(RecyclerView.Adapter adapter);

    void setAdapter(UltimateViewAdapter adapter);

    RecyclerView.LayoutManager getLayoutManager();

    void setLayoutManager(RecyclerView.LayoutManager layoutManager);

    void setListViewVisibility(int visibility);

    void setListAdapter(MyExpandableAdapter expandableAdapter);

    void expandListGroup(int groupPos);

    void setOnChildClickListener(ExpandableListView.OnChildClickListener onChildClickListener);

    void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener);

    int getCurrentPattern();

    void setCurrentPattern(int pattern);

    void addItemDecoration(RecyclerView.ItemDecoration itemDecoration);

    void attachItemTouchHelper(ItemTouchHelper touchHelper);

    int getEmptyViewVisibility();

    void setEmptyViewVisibility(int visibility);
}
