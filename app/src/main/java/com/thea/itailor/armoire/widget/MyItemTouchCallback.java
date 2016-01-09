package com.thea.itailor.armoire.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.thea.itailor.armoire.adapters.ClothAdapter;
import com.thea.itailor.armoire.adapters.ItemTouchHelperAdapter;

/**
 * Created by Thea on 2015/8/2.
 */
public class MyItemTouchCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "MyItemTouchCallback";

    private final ItemTouchHelperAdapter mAdapter;

    public MyItemTouchCallback(ItemTouchHelperAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            if (viewHolder instanceof ClothAdapter.MyViewHolder) {
                ClothAdapter.MyViewHolder holder = (ClothAdapter.MyViewHolder) viewHolder;
                holder.itemView.setSelected(true);
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof ClothAdapter.MyViewHolder) {
            ClothAdapter.MyViewHolder holder = (ClothAdapter.MyViewHolder) viewHolder;
            holder.itemView.setSelected(false);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }
}
