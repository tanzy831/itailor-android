package com.thea.itailor.community.view;

import android.support.v7.widget.GridLayout;
import android.view.View;

/**
 * Created by Thea on 2015/8/28 0028.
 */
public interface IShareView {

    int getImageCount();

    void addShareImage(View v, int position);

    String getDescription();

    GridLayout getImageParent();

//    void setAddImageListener(View.OnClickListener onClickListener);
}
