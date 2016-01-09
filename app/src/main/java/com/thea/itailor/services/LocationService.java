package com.thea.itailor.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.recommend.bean.Weather;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.WeatherParser;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LocationService extends Service implements LocationListener {
    private static final String TAG = "LocationService";
    private final IBinder mBinder = new LocalBinder();

    private LocationManager mLocationManager;
    private Location mLocation;
    private boolean isGPSOpen;

    private IUserModel mUserModel;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mUserModel = new UserModel(getApplicationContext());
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener(mListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
//        if (System.currentTimeMillis() - mUserModel.getLastLocationTime().getTime() > 1 * 3600 * 1000)
            location();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        Log.i(TAG, mLocation.getLatitude() + "  " + mLocation.getLongitude());
        sendLocation("");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.i(TAG, "当前GPS状态为可见状态");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.i(TAG, "当前GPS状态为服务区外状态");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.i(TAG, "当前GPS状态为暂停服务状态");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        isGPSOpen = true;
    }

    @Override
    public void onProviderDisabled(String provider) {
        isGPSOpen = false;
    }

    public void sendLocation(String citykey) {
        HttpUtil.get(getApplicationContext(), Path.GET_WEATHER_URL + "101070101", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Log.i(TAG, "onSuccess: " + statusCode);
                try {
                    Weather weather = WeatherParser.parse(new ByteArrayInputStream(binaryData));
                    Header[] hs = new Header[1];
                    hs[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
                    StringEntity entity = new StringEntity(
                            new Gson().toJson(weather, Weather.class), HttpUtil.ENCODING);
                    HttpUtil.post(getApplicationContext(), mUserModel.getBaseUrl() + Path.WEATHER +
                            "?accountID=" + mUserModel.getAccountID(), hs, entity,
                            ContentType.APPLICATION_JSON, httpResponseHandler);
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

    public void dealLocation() {
        if (mLocation == null)
            return;

        HttpUtil.get(getApplicationContext(), Path.BAIDU_CONVERT_URL + mLocation.getLatitude()
                + "," + mLocation.getLongitude(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "onSuccess: obj" + statusCode + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i(TAG, "onFailure: " + statusCode);
            }
        });
    }

    private void location() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String provider = mLocationManager.getBestProvider(criteria, true);
        mLocation = mLocationManager.getLastKnownLocation(provider);
        if (mLocation != null)
            Log.i(TAG, mLocation.getLatitude() + "  " + mLocation.getLongitude());
        mLocationManager.requestLocationUpdates(provider, 100 * 1000, 500, this);
    }

    private AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i(TAG, "onSuccess1: " + statusCode);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i(TAG, "onFailure1: " + statusCode);
        }
    };

    private GpsStatus.Listener mListener = event -> {
        switch (event) {
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Log.i(TAG, "第一次定位");
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Log.i(TAG, "卫星状态改变");
                break;
            case GpsStatus.GPS_EVENT_STARTED:
                Log.i(TAG, "定位启动");
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                Log.i(TAG, "定位结束");
                break;
        }
    };

    public class LocalBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }
}
