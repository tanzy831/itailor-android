package com.thea.itailor.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class CollectionSyncService extends Service {
    private static final String TAG = "CollectionSyncService";
    private final IBinder mBinder = new LocalBinder();

    private Thread thread;

    public CollectionSyncService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public CollectionSyncService getService() {
            return CollectionSyncService.this;
        }
    }
}
