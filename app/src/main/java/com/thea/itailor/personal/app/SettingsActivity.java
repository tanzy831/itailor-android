package com.thea.itailor.personal.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.thea.itailor.R;
import com.thea.itailor.personal.presenter.SettingsPresenter;
import com.thea.itailor.personal.view.ISettingsView;
import com.thea.itailor.services.SettingsService;


public class SettingsActivity extends AppCompatActivity implements ISettingsView {
    private static final String TAG = "SettingsActivity";

    private SettingsPresenter mPresenter;

    private Switch autoSyncSwitch;
    private Switch notificationSwitch;
    private Switch characterSwitch;

    private TextView mCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoSyncSwitch = (Switch) findViewById(R.id.switch_auto_sync);
        notificationSwitch = (Switch) findViewById(R.id.switch_notification);
        characterSwitch = (Switch) findViewById(R.id.switch_character);
        mCacheSize = (TextView) findViewById(R.id.tv_cache_size);

        mPresenter = new SettingsPresenter(this, this);

        findViewById(R.id.btn_logout).setOnClickListener(v -> mPresenter.logout());
        findViewById(R.id.rl_clear_cache).setOnClickListener(v -> mPresenter.clearCache());
        autoSyncSwitch.setOnCheckedChangeListener(mChangeListener);
        notificationSwitch.setOnCheckedChangeListener(mChangeListener);
        characterSwitch.setOnCheckedChangeListener(mChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadSettingInfo();
    }

    private OnCheckedChangeListener mChangeListener = (buttonView, isChecked) -> {
        switch (buttonView.getId()) {
            case R.id.switch_auto_sync:
                mPresenter.changeSyncSetting(isChecked);
                break;
            case R.id.switch_notification:
                mPresenter.changeNotiSetting(isChecked);
                break;
            case R.id.switch_character:
                mPresenter.changeCharacter(isChecked);
                break;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startService(new Intent(this, SettingsService.class));
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean getAutoSync() {
        return autoSyncSwitch.isChecked();
    }

    @Override
    public boolean getNotification() {
        return notificationSwitch.isChecked();
    }

    @Override
    public void setAutoSync(boolean autoSync) {
        autoSyncSwitch.setChecked(autoSync);
    }

    @Override
    public void setNotification(boolean notification) {
        notificationSwitch.setChecked(notification);
    }

    @Override
    public void setCharacter(boolean character) {
        characterSwitch.setChecked(character);
    }

    @Override
    public void setCacheSize(String cacheSize) {
        mCacheSize.setText(cacheSize+"MB");
    }
}
