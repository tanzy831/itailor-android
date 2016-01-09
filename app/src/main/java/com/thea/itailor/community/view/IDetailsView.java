package com.thea.itailor.community.view;

import android.graphics.Bitmap;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * Created by Thea on 2015/9/8 0008.
 */
public interface IDetailsView {

    GridLayout getImagesLayout();

    void setUserIcon(Bitmap bitmap);

    void setUserName(String name);

    void setDescription(String description);

    void addImage(ImageView imageView);

    void setTime(String time);

    void addFavor(String favor);

    void setAdapter(RecyclerView.Adapter adapter);

    void setLayoutManager(RecyclerView.LayoutManager layoutManager);

    void addItemDecoration(RecyclerView.ItemDecoration itemDecoration);
}
