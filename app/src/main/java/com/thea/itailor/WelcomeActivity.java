package com.thea.itailor;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.bean.DetailWeather;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.WeatherParser;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;


public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";

    private TextView temperature;
    private TextView humidity;
    private TextView wind;
//    private LinearLayout llExponents;

    private TextView comfortValue;
    private TextView comfortDetail;
    private TextView dressValue;
    private TextView dressDetail;
    private TextView dateValue;
    private TextView dateDetail;
    private TextView sportValue;
    private TextView sportDetail;
    private TextView raysValue;
    private TextView raysDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /*GifImageView giv = (GifImageView) findViewById(R.id.giv_welcome);
        GifDrawable gd = (GifDrawable) giv.getBackground();*/

        temperature = (TextView) findViewById(R.id.tv_temperature);
        humidity = (TextView) findViewById(R.id.tv_humidity);
        wind = (TextView) findViewById(R.id.tv_wind);
//        llExponents = (LinearLayout) findViewById(R.id.ll_exponents);

        comfortValue = (TextView) findViewById(R.id.tv_comfort_value);
        comfortDetail = (TextView) findViewById(R.id.tv_comfort_detail);
        dressValue = (TextView) findViewById(R.id.tv_dress_value);
        dressDetail = (TextView) findViewById(R.id.tv_dress_detail);
        dateValue = (TextView) findViewById(R.id.tv_date_value);
        dateDetail = (TextView) findViewById(R.id.tv_date_detail);
        sportValue = (TextView) findViewById(R.id.tv_sport_value);
        sportDetail = (TextView) findViewById(R.id.tv_sport_detail);
        raysValue = (TextView) findViewById(R.id.tv_rays_value);
        raysDetail = (TextView) findViewById(R.id.tv_rays_detail);

        if (isConnect(this)) {
            HttpUtil.get(this, Path.GET_WEATHER_URL + "101070101", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                    Log.i(TAG, "onSuccess: " + statusCode);
                    try {
                        init(WeatherParser.parseDetail(new ByteArrayInputStream(binaryData)));
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                    Log.i(TAG, "onFailure: " + statusCode);
                }
            });
        }
        else
            startActivity(new Intent(this, MainActivity.class));

    }

    public static boolean isConnect(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        } catch (Exception e) {
            Log.v("error",e.toString());
        }
        return false;
    }

    public void init(DetailWeather weather) {
        temperature.setText(weather.getTemperature()+"℃");
        humidity.setText(weather.getHumidity());
        wind.setText(weather.getWind());
        comfortValue.setText(weather.getExponent("舒适度").getName());
        comfortDetail.setText(weather.getExponent("舒适度").getDetail());
        dressValue.setText(weather.getExponent("穿衣指数").getName());
        dressDetail.setText(weather.getExponent("穿衣指数").getDetail());
        dateValue.setText(weather.getExponent("约会指数").getName());
        dateDetail.setText(weather.getExponent("约会指数").getDetail());
        sportValue.setText(weather.getExponent("运动指数").getName());
        sportDetail.setText(weather.getExponent("运动指数").getDetail());
        raysValue.setText(weather.getExponent("紫外线强度").getName());
        raysDetail.setText(weather.getExponent("紫外线强度").getDetail());

        /*List<Exponent> exponents = weather.getExponents();
        for (Exponent e : exponents) {
            View view = getLayoutInflater().inflate(R.layout.item_exponent, llExponents, false);
            TextView name = (TextView) view.findViewById(R.id.tv_exponent_name);
            TextView value = (TextView) view.findViewById(R.id.tv_exponent_value);
            TextView detail = (TextView) view.findViewById(R.id.tv_exponent_detail);

            name.setText(e.getName());
            value.setText(e.getValue());
            detail.setText(e.getDetail());
            llExponents.addView(view);
        }*/

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra(Constant.EXTRA_FIRST_PAGE, true);
            startActivity(intent);
        }, 4500);
    }
}
