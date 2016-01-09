package com.thea.itailor.armoire.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.thea.itailor.R;
import com.thea.itailor.armoire.presenter.ShowBodyPresenter;
import com.thea.itailor.armoire.view.IShakeView;
import com.thea.itailor.config.ExtraName;

public class ShowBodyActivity extends AppCompatActivity implements IShakeView {
    private static final String TAG = "ShowBodyActivity";

    private ShowBodyPresenter mPresenter;

    private SensorManager mSensorManager = null;
    private Vibrator mVibrator = null;

    private ImageView mImageView;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_body);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mPresenter = new ShowBodyPresenter(this, this);

        view = findViewById(R.id.rl_show_body);
        mImageView = (ImageView) findViewById(R.id.iv_show_cloth);

        findViewById(R.id.fab_to_armoire).setOnClickListener(mClickListener);
//        findViewById(R.id.ib_previous).setOnClickListener(mClickListener);
//        findViewById(R.id.ib_next).setOnClickListener(mClickListener);

        mPresenter.loadImage(getIntent().getStringExtra(ExtraName.IMAGE_NAME));
    }

    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {
            case R.id.fab_to_armoire:
//                startActivity(new Intent(this, ArmoireFragment.class));
                onBackPressed();
                break;
//            case R.id.ib_previous:
//                break;
//            case R.id.ib_next:
//                break;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mPresenter, mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mPresenter);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
//        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void vibrate(long milliseconds) {
        mVibrator.vibrate(milliseconds);
    }
}
