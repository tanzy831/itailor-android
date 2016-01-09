package com.thea.itailor.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.internal.widget.TintManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thea.itailor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/7/29.
 */
public class MyTabLayout extends LinearLayout {
    private final List<Tab> tabs = new ArrayList<>();
    private ViewPager viewPager;
    private OnTabSelectedListener onTabSelectedListener;

    public MyTabLayout(Context context) {
        super(context);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public void addTab(int iconResId, int textResId) {
        final Tab tab = new Tab(this);
        tab.setContent(iconResId, textResId, tabs.size());
        tab.getCustomView().setOnClickListener(v -> selectTab(tab));
        this.addView(tab.getCustomView());
        tabs.add(tab);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void selectTab(Tab tab) {
        selectTab(tab.getPosition());
    }

    public void selectTab(int position) {
        if (onTabSelectedListener != null)
            onTabSelectedListener.notification();
        if (viewPager != null)
            viewPager.setCurrentItem(position, true);
        setSelectedTab(position);
    }

    public void setSelectedTab(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            Tab tab = tabs.get(i);
            tab.setSelected(i == position);
        }
    }

    public static class Tab {
        private final MyTabLayout parent;
        private View customView;
        private ImageView iv;
        private TextView tv;

        private Object tag;
        private Drawable icon;
        private CharSequence text;
        private int position = -1;

        public Tab(MyTabLayout parent) {
            this.parent = parent;
            customView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab, parent, false);
            iv = (ImageView) customView.findViewById(R.id.iv_tab);
            tv = (TextView) customView.findViewById(R.id.tv_tab);
        }

        public MyTabLayout getParent() {
            return parent;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public View getCustomView() {
            return customView;
        }

        public Tab setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        public Tab setCustomView(int layoutId) {
            return setCustomView(LayoutInflater.from(parent.getContext()).inflate(layoutId, (ViewGroup)null));
        }

        public Object getTag() {
            return tag;
        }

        public Tab setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Drawable getIcon() {
            return icon;
        }

        public Tab setIcon(Drawable icon) {
            this.icon = icon;
            iv.setImageDrawable(icon);
            return this;
        }

        public Tab setIcon(int iconResId) {
            return setIcon(TintManager.getDrawable(parent.getContext(), iconResId));
        }

        public CharSequence getText() {
            return text;
        }

        public Tab setText(CharSequence text) {
            this.text = text;
            tv.setText(text);
            return this;
        }

        public Tab setText(int textResId) {
            return setText(parent.getResources().getText(textResId));
        }

        public void setContent(int iconResId, int textResId, int position) {
            setIcon(iconResId);
            setText(textResId);
            setPosition(position);
        }

        public void setSelected(boolean selected) {
            customView.setSelected(selected);
            boolean changed = customView.isSelected() != selected;
            if (selected && changed) {
                iv.setSelected(true);
                tv.setSelected(true);
            }
        }
    }

    public interface OnTabSelectedListener {
        void notification();
    }
}
