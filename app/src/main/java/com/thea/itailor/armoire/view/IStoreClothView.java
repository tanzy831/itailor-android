package com.thea.itailor.armoire.view;

import android.graphics.Bitmap;
import android.widget.SpinnerAdapter;

/**
 * Created by Thea on 2015/8/17 0017.
 */
public interface IStoreClothView {

    int getClothId();

    int getOldGroupPosition();

    int getChildPosition();

    String getFilename();

    int getSelectedLatticePosition();

    Object getSelectedLattice();

    void setAdapter(SpinnerAdapter adapter);

    String getName();

    void setName(String name);

    String getStory();

    void setStory(String story);

    void setClothImage(Bitmap bitmap);

    void setTime(String time);
}
