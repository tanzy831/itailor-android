package com.thea.itailor.armoire.view;

import android.graphics.Bitmap;

/**
 * Created by Thea on 2015/8/28 0028.
 */
public interface IShakeView {

    void setImageBitmap(Bitmap bitmap);

    void vibrate(long milliseconds);
}
