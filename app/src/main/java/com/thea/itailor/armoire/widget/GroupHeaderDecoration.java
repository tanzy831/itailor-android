package com.thea.itailor.armoire.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.adapters.ClothAdapter;

/**
 * Created by Thea on 2015/8/1.
 */
public class GroupHeaderDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "GroupHeaderDecoration";

    private final ClothAdapter adapter;
    private int[] isDrawn;
    private final SparseArray<View> mHeaderViews = new SparseArray<>();

    public GroupHeaderDecoration(ClothAdapter adapter) {
        this.adapter = adapter;
        isDrawn = new int[adapter.getGroupCount()];
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
//        Log.i(TAG, "onDrawOver");
        isDrawn = new int[adapter.getGroupCount()];
        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int[] index = adapter.getItemIndex(parent.getChildAdapterPosition(child));
            drawHeaderView(canvas, parent, child, index[0]);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        Log.i(TAG, "getItemOffsets");
        int position = parent.getChildAdapterPosition(view);
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        if (adapter.isFirstRawChild(position, spanCount)) {
            View header = getHeaderView(parent, adapter.getItemIndex(position)[0]);
            view.measure(view.getWidth(), view.getHeight() + header.getHeight());
            outRect.top = header.getHeight();
        }
    }

    public void drawHeaderView(Canvas canvas, RecyclerView parent, View child, int groupIndex) {
        if (isHeaderDrawn(groupIndex))
            return;
        canvas.save();
        View view = getHeaderView(parent, groupIndex);
        canvas.translate(0, Math.max(0, child.getTop() - view.getHeight()));
        view.draw(canvas);
        canvas.restore();
        isDrawn[groupIndex] = -1;
    }

    public boolean isHeaderDrawn(int groupIndex) {
        return isDrawn[groupIndex] != 0;
    }

    public View getHeaderView(RecyclerView parent, int groupIndex) {
        Lattice lattice = adapter.getLattice(groupIndex);
//        View header = mHeaderViews.get(lattice.getId());
        View header = mHeaderViews.get(groupIndex);

        if (header == null) {
            header = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_group, parent, false);
            ImageView icon = (ImageView) header.findViewById(R.id.civ_group_icon);
            TextView tv = (TextView) header.findViewById(R.id.tv_group_name);

            tv.setText(lattice.getName());
            icon.setImageResource(R.mipmap.ic_lattice);

            int heightSpec = MeasureSpec.makeMeasureSpec(parent.getHeight(), MeasureSpec.AT_MOST);

            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
            header.measure(0, childHeight);
            header.layout(0, 0, parent.getWidth(), header.getMeasuredHeight());
            mHeaderViews.put(groupIndex, header);
        }

        return header;
    }
}
