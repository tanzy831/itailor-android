package com.thea.itailor.personal.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.model.ClothModel;
import com.thea.itailor.armoire.model.ClothSQLiteOpenHelper;
import com.thea.itailor.armoire.model.IClothModel;
import com.thea.itailor.armoire.model.ILatticeModel;
import com.thea.itailor.armoire.model.LatticeModel;
import com.thea.itailor.armoire.model.LatticeSQLiteOpenHelper;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;
import com.thea.itailor.util.ImageHelper;
import com.thea.itailor.util.XmlUtil;

import org.apache.http.Header;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity {
    private static final String TAG = "AboutUsActivity";

    private IUserModel mUserModel;

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mUserModel = new UserModel(this);
        et = (EditText) findViewById(R.id.et_ip);
        findViewById(R.id.btn_affirm).setOnClickListener(v -> {
            mUserModel.setBaseUrl(et.getText().toString().trim());
            finish();
        });

        findViewById(R.id.btn_test_connection).setOnClickListener(v1 -> testConnection());
        findViewById(R.id.btn_init_data).setOnClickListener(v -> initData());
        findViewById(R.id.btn_compulsory_logout).setOnClickListener(v -> {
            mUserModel.setLoginState(false);
            Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
        });
    }

    public void testConnection() {
        HttpUtil.get(this, mUserModel.getBaseUrl() + Path.TEST,
                new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                Toast.makeText(AboutUsActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode);
                Toast.makeText(AboutUsActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initData() {
        if (mUserModel.getLoadData())
            return;
        try {
            List<Lattice> lattices = XmlUtil.parseArmoire(this);
            ILatticeModel mLatticeModel = new LatticeModel(new LatticeSQLiteOpenHelper(this));
            IClothModel mClothModel = new ClothModel(new ClothSQLiteOpenHelper(this));
            for (int i = 0; i < lattices.size(); i++) {
                Lattice lattice = lattices.get(i);
                mLatticeModel.add(lattice.getName(), R.mipmap.head_icon);
                int id = mLatticeModel.find(lattice.getName());
                for (int j = 0; j < lattice.size(); j++) {
                    Cloth cloth = lattice.getCloth(j);
                    mClothModel.add(cloth.getName(), cloth.getFilename(), id);
                    ImageHelper.saveImageToStore(getAssets().open("aaa/" + cloth.getFilename()),
                            Constant.DIRECTORY_ARMOIRE, "/" + cloth.getFilename());
                }
            }

            /*for (int i = 0; i < images.length; i++) {
                ImageHelper.saveImageToStore(getAssets().open("aaa/" + images[i]),
                        Constant.DIRECTORY_ARMOIRE, "/" + images[i]);
            }*/

            ImageHelper.saveImageToStore(getAssets().open("aaa/" + "default_portrait.png"),
                    Constant.DIRECTORY_USER, "/" + "default_portrait.png");
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        mUserModel.setLoadData(true);
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        et.setText(mUserModel.getServerIp());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    String[] images = {"recommend1_top.jpg", "recommend1_bottom.jpg", "recommend2_top.jpg",
            "recommend2_bottom.jpg", "recommend3_top.jpg"};
}
