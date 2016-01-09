package com.thea.itailor;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thea.itailor.armoire.app.ArmoireFragment;
import com.thea.itailor.community.app.CommunityFragment;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.community.presenter.PursuersPresenter;
import com.thea.itailor.personal.app.PersonalFragment;
import com.thea.itailor.personal.bean.Message;
import com.thea.itailor.personal.model.INotificationModel;
import com.thea.itailor.personal.model.NotificationModel;
import com.thea.itailor.recommend.app.RecommendFragment;
import com.thea.itailor.services.LocationService;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.widget.MyTabLayout;
import com.thea.itailor.widget.NoScrollViewPager;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyTabLayout.OnTabSelectedListener {
    private static final String TAG = "MainActivity";

//    private FragmentManager mManager;
//    private FragmentTransaction mTransaction = null;

//    private MyViewPagerAdapter mAdapter;
//    private Fragment mCurrentPrimaryItem = null;
//    private FrameLayout mContainer;
//
//    private RadioGroup mGroup;

    private INotificationModel mNotificationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationModel = new NotificationModel(this);

        MyTabLayout tabLayout = (MyTabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(R.mipmap.ic_armoire, R.string.armoire);
        tabLayout.addTab(R.mipmap.ic_recommend, R.string.recommend);
        tabLayout.addTab(R.mipmap.ic_community, R.string.community);
        tabLayout.addTab(R.mipmap.ic_personal, R.string.personal);

        NoScrollViewPager viewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addItem(ArmoireFragment.newInstance());
        adapter.addItem(RecommendFragment.newInstance());
        adapter.addItem(CommunityFragment.newInstance());
        adapter.addItem(PersonalFragment.newInstance());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.selectTab(0);

        /*mManager = getSupportFragmentManager();
        mAdapter = new MyViewPagerAdapter(mManager);
        mContainer = (FrameLayout) findViewById(R.id.fl_container);
        mGroup = (RadioGroup) findViewById(R.id.rg_tabs);

        mGroup.setOnCheckedChangeListener(mChangeListener);*/

        if (System.currentTimeMillis() - new UserModel(this).getLastLocationTime().getTime() > 1 * 3600 * 1000) {
            openGPSSettings();
            startService(new Intent(this, LocationService.class));
        }
    }

    /*private RadioGroup.OnCheckedChangeListener mChangeListener = (group, checkedId) -> {
        Fragment fragment = (Fragment) mAdapter.instantiateItem(mContainer, checkedId);
        mAdapter.setPrimaryItem(mContainer, 0, fragment);
        mAdapter.finishUpdate(mContainer);
    };*/

    private void openGPSSettings() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
        }
    }

    @Override
    public void notification() {
        Log.i(TAG, "notification");
        mNotificationModel.getNotifications(mHandler);
    }

    private JsonHttpResponseHandler mHandler = new JsonHttpResponseHandler(HttpUtil.ENCODING) {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            Log.i(TAG, "onSuccess: " + statusCode + response.toString());
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> messages = new Gson().fromJson(response.toString(), type);
            if (messages.size() > 0) {
                push(messages.get(messages.size() - 1));
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.i(TAG, "onFailure: " + statusCode);
        }
    };

    public void push(Message message) {
        Log.i(TAG, "cf");
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent intent = PendingIntent.getActivity(this, 1,
                new Intent(this, PursuersPresenter.class), PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_sewing_white)
                .setContentText(message.getContext())
                .setTicker("有消息啦~")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.contentIntent = intent;
//        notificationManager.notify(0, notification);
//        notification.notify();
        NotificationManagerCompat.from(this).notify(9, notification);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }
}
