package com.thea.itailor.armoire.presenter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.model.ClothModel;
import com.thea.itailor.armoire.model.ClothSQLiteOpenHelper;
import com.thea.itailor.armoire.model.IClothModel;
import com.thea.itailor.armoire.view.IShakeView;
import com.thea.itailor.config.Constant;
import com.thea.itailor.util.ImageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thea on 2015/8/28 0028.
 */
public class ShowBodyPresenter implements SensorEventListener {
    private IShakeView mShakeView;

    private List<Cloth> cloths = new ArrayList<>();

    public ShowBodyPresenter(Context context, IShakeView mShakeView) {
        this.mShakeView = mShakeView;
        IClothModel mClothModel = new ClothModel(new ClothSQLiteOpenHelper(context));
        cloths = mClothModel.findAll();
    }

    public void loadImage(String filename) {
        mShakeView.setImageBitmap(ImageHelper.getImageFromStore(
                Constant.DIRECTORY_ARMOIRE, "/" + filename));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if (Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math.abs(values[2]) > 14) {
                mShakeView.vibrate(300);
                int position = new Random().nextInt(cloths.size());
                mShakeView.setImageBitmap(ImageHelper.getImageFromStore(
                        Constant.DIRECTORY_ARMOIRE, "/" + cloths.get(position).getFilename()));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
