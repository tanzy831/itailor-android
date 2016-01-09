package com.thea.itailor.armoire.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.presenter.ScanPresenter;
import com.thea.itailor.armoire.view.IStoreClothView;
import com.thea.itailor.config.ExtraName;

public class ScanActivity extends AppCompatActivity implements IStoreClothView {
    private static final String TAG = "ScanActivity";

    private ScanPresenter presenter;

    private EditText mNameText;
    private Spinner mLatticesSpinner;
    private EditText mStoryText;
    private TextView mTime;
    private ImageView mClothView;

    private Lattice lattice;
    private Cloth cloth;
    private int groupPosition = -1;
    private int childPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(ExtraName.IMAGE_NAME)) {
            cloth = new Cloth("", getIntent().getStringExtra(ExtraName.IMAGE_NAME));
        }
        else {
            lattice = new Lattice(getIntent().getIntExtra(ExtraName.LATTICE_ID, -1),
                    getIntent().getStringExtra(ExtraName.LATTICE_NAME), R.mipmap.head_icon);
            cloth = new Gson().fromJson(getIntent().getStringExtra(ExtraName.CLOTH), Cloth.class);
            groupPosition = getIntent().getIntExtra(ExtraName.GROUP_POSITION, -1);
            childPosition = getIntent().getIntExtra(ExtraName.CHILD_POSITION, -1);
        }

        mNameText = (EditText) findViewById(R.id.et_cloth_name);
        mLatticesSpinner = (Spinner) findViewById(R.id.spn_lattices);
        mStoryText = (EditText) findViewById(R.id.et_cloth_story);
        mTime = (TextView) findViewById(R.id.tv_save_time);
        mClothView = (ImageView) findViewById(R.id.iv_camera_image);

        presenter = new ScanPresenter(this, this);

        presenter.loadLattices();
        presenter.loadClothImage(cloth);

        if (lattice != null) {
            Log.i(TAG, lattice.getName() + cloth.getName());
            mLatticesSpinner.setSelection(Math.max(0, groupPosition));
        }

//        Check.checkForLogin(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
//                if (Check.checkForLogin(this))
                    presenter.storeCloth();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public int getClothId() {
        return cloth.getId();
    }

    @Override
    public int getOldGroupPosition() {
        return groupPosition;
    }

    @Override
    public int getChildPosition() {
        return childPosition;
    }

    @Override
    public String getFilename() {
        return cloth.getFilename();
    }

    @Override
    public int getSelectedLatticePosition() {
        return mLatticesSpinner.getSelectedItemPosition();
    }

    @Override
    public Object getSelectedLattice() {
        return mLatticesSpinner.getSelectedItem();
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        mLatticesSpinner.setAdapter(adapter);
    }

    @Override
    public String getName() {
        return mNameText.getText().toString();
    }

    @Override
    public void setName(String name) {
        mNameText.setText(name);
    }

    @Override
    public String getStory() {
        return mStoryText.getText().toString();
    }

    @Override
    public void setStory(String story) {
        mStoryText.setText(story);
    }

    @Override
    public void setClothImage(Bitmap bitmap) {
        mClothView.setImageBitmap(bitmap);
    }

    @Override
    public void setTime(String time) {
        mTime.setText(time);
    }
}
