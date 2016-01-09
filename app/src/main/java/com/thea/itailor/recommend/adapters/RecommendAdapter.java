package com.thea.itailor.recommend.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thea.itailor.R;

import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/29 0029.
 */
public class RecommendAdapter extends PagerAdapter {
    private Context context;
    private final ArrayList<View> items;

    public RecommendAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
//        items.add(generateFirstView());
    }

    public RecommendAdapter(Context context, ArrayList<View> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = items.get(position);
        container.addView(view, position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View) object;
        return items.indexOf(view);
    }

    public void setItems(ArrayList<View> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
//        this.items.add(generateLastView());
    }

    private View generateFirstView() {
        return LayoutInflater.from(context).inflate(R.layout.loading_progress, null);
    }

    /*private View generateLastView() {
        return
    }*/
}
