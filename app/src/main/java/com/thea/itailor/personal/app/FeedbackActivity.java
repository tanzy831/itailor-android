package com.thea.itailor.personal.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.model.IUserModel;
import com.thea.itailor.community.model.UserModel;
import com.thea.itailor.config.ContentType;
import com.thea.itailor.config.Path;
import com.thea.itailor.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "FeedbackActivity";
    private EditText fbInfo;

    private IUserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserModel = new UserModel(this);

        fbInfo = (EditText) findViewById(R.id.et_feedback);
        findViewById(R.id.btn_submit).setOnClickListener(mClickListener);
        findViewById(R.id.btn_reset).setOnClickListener(mClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    Header[] headers = new Header[1];
                    headers[0] = new BasicHeader("authenticate", mUserModel.getAuthenticate());
                    HttpUtil.post(getApplicationContext(), mUserModel.getBaseUrl() +
                            Path.DEVELOPER_FEEDBACK + "?accountID=" + mUserModel.getAccountID()
                            + "&content=" + fbInfo.getText().toString(), headers, null,
                            ContentType.TEXT_PLAIN, mHttpResponseHandler);
                    break;
                case R.id.btn_reset:
                    fbInfo.setText("");
                    break;
            }
        }
    };

    private AsyncHttpResponseHandler mHttpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i(TAG, "onSuccess:" + statusCode);
            Toast.makeText(FeedbackActivity.this,
                    "谢谢您的反馈，我们会更加努力的~", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i(TAG, "onFailure:" + statusCode);
        }
    };
}
