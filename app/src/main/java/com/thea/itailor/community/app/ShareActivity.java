package com.thea.itailor.community.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.thea.itailor.R;
import com.thea.itailor.community.presenter.SharePresenter;
import com.thea.itailor.community.view.IShareView;
import com.thea.itailor.config.ExtraName;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity implements IShareView {
    private static final String TAG = "ShareActivity";
    private static final int IMAGES_REQUEST_CODE = 21;

    private SharePresenter mPresenter;

    private EditText mEditText;
    private GridLayout mGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mEditText = (EditText) findViewById(R.id.et_share_description);
        mGridLayout = (GridLayout) findViewById(R.id.gl_will_share_images);
        findViewById(R.id.iv_add_image).setOnClickListener(v -> skipToImages());

        mPresenter = new SharePresenter(this, this);

        if (intent.hasExtra(ExtraName.IMAGE_NAME)) {
            if (intent.hasExtra(ExtraName.RECOMMEND_ID))
                mPresenter.addRecommendImage(intent.getStringExtra(ExtraName.IMAGE_NAME));
            else
                mPresenter.addImage(intent.getStringExtra(ExtraName.IMAGE_NAME));
        }
        if (intent.hasExtra(ExtraName.IMAGE_NAMES)) {
            mPresenter.addImage(intent.getStringArrayListExtra(ExtraName.IMAGE_NAMES));
        }
    }

    public void skipToImages() {
        startActivityForResult(new Intent(this, ImagesActivity.class), IMAGES_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGES_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> imageNames = data.getStringArrayListExtra(ExtraName.IMAGE_NAMES);
            Log.i(TAG, imageNames.size() + "");
            if (imageNames != null && imageNames.size() != 0)
                mPresenter.addImage(imageNames);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_send:
                mPresenter.send();
                mPresenter.sendImages();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public int getImageCount() {
        return mGridLayout.getChildCount() - 1;
    }

    @Override
    public void addShareImage(View v, int position) {
//        LayoutParams params = new LayoutParams();
//        params.width = 200;
        mGridLayout.addView(v, position);
    }

    @Override
    public String getDescription() {
        return mEditText.getText().toString();
    }

    @Override
    public GridLayout getImageParent() {
        return mGridLayout;
    }
}
