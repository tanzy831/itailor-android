package com.thea.itailor.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class SyncService extends Service {
    private static final String TAG = "SyncService";
    private final IBinder mBinder = new LocalBinder();

    private Thread thread;
//    private ClothModel dao;
//    private List<Cloth> clothes = new ArrayList<>();

    public SyncService() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                clothes = dao.findNotSynchronized();
                post();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        dao = new ClothModel(new ClothSQLiteOpenHelper(getApplicationContext()));
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void post() {
        /*for (int i = 0; i < clothes.size() / 3 + 1; i++) {
            try {
                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < 3; j++) {
                    if (i * 3 + j >= clothes.size())
                        break;
                    Cloth cloth = clothes.get(i * 3 + j);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("group", cloth.getGroup());
                    jsonObject.put("remark", cloth.getRemark());
                    jsonObject.put("date", cloth.getDate());
                    jsonArray.put(jsonObject);
                }

                StringEntity entity = new StringEntity(jsonArray.toString());
                HttpUtil.post(getApplicationContext(), "", entity, "application/json", mHttpResponseHandler);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/
    }

    private AsyncHttpResponseHandler mHttpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            Log.i(TAG, "onSuccess");
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Log.i(TAG, "onFailure");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public SyncService getService() {
            return SyncService.this;
        }
    }
}
