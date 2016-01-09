package com.thea.itailor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Thea on 2015/7/28.
 */
public class ItemDividerDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "ItemDivider";

    public static final int HORIZONTAL_DIVIDER = 45;
    public static final int VERTICAL_DIVIDER = 46;
    public static final int BOTH_DIVIDER = 47;

    private Drawable mDivider;
    private int orientation;

    public ItemDividerDecoration(Context context, int resId, int orientation) {
        mDivider = context.getResources().getDrawable(resId);
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        Log.i(TAG, parent.getChildCount() + "  " + state.getItemCount() + "  " + state.getTargetScrollPosition());
//        super.onDraw(c, parent, state);
        if (orientation == HORIZONTAL_DIVIDER) {
            drawHorizontal(c, parent);
        }
        else if (orientation == VERTICAL_DIVIDER) {
            drawVertical(c, parent);
        }
        else if (orientation == BOTH_DIVIDER) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == HORIZONTAL_DIVIDER && !isLastRaw(view, parent)) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
        else if (orientation == VERTICAL_DIVIDER && !isLastColumn(view, parent)) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
        else if (orientation == BOTH_DIVIDER) {
            if (isLastColumn(view, parent))
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            else if (isLastRaw(view, parent))
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            else
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int top = child.getTop() - params.topMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            final int bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public boolean isLastColumn(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        int direction = layoutManager.getLayoutDirection();
        int spanCount;

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

            if (direction == GridLayoutManager.HORIZONTAL &&
                spanSizeLookup.getSpanIndex(position, spanCount) +
                spanSizeLookup.getSpanSize(position) == spanCount) {
                return true;
            }
            else if (direction == GridLayoutManager.VERTICAL &&
                    position + 1 > (itemCount - itemCount % spanCount)) {
                return true;
            }
        }
        else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            if (spanCount < 0) {
                return position + 1 == itemCount;
            }
            if (direction == StaggeredGridLayoutManager.HORIZONTAL &&
                (position + 1) % spanCount == 0) {
                return true;
            }
            else if (direction == StaggeredGridLayoutManager.VERTICAL &&
                    position + 1 > (itemCount - itemCount % spanCount)) {
                return true;
            }
        }
        else if (layoutManager instanceof LinearLayoutManager) {
            if (direction == LinearLayoutManager.VERTICAL)
                return parent.getChildAdapterPosition(view) + 1 == itemCount;
            else if (direction == LinearLayoutManager.HORIZONTAL)
                return true;
        }
        return false;
    }

    public boolean isLastRaw(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view) + 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        int direction = layoutManager.getLayoutDirection();
        int spanCount;

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            if (direction == GridLayoutManager.HORIZONTAL) {

            }
        }
        else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();

            if (spanCount < 0) {
                return position == itemCount;
            }
            if (direction == StaggeredGridLayoutManager.VERTICAL && position % spanCount == 0) {
                return true;
            }
            else if (direction == StaggeredGridLayoutManager.HORIZONTAL &&
                    position > (itemCount - itemCount % spanCount)) {
                return true;
            }
        }
        else if (layoutManager instanceof LinearLayoutManager) {
            if (direction == LinearLayoutManager.HORIZONTAL)
                return parent.getChildAdapterPosition(view) + 1 == layoutManager.getItemCount();
            else if (direction == LinearLayoutManager.VERTICAL)
                return true;
        }
        return false;
    }
}
