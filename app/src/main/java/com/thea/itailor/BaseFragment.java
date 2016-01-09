package com.thea.itailor;

import android.support.v4.app.Fragment;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }
        else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();
}
