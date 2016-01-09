package com.thea.itailor.armoire.adapters;

/**
 * Created by Thea on 2015/8/2.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
