package com.thea.itailor.community.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thea.itailor.R;
import com.thea.itailor.community.model.FriendModel;
import com.thea.itailor.community.model.IFriendModel;

import org.apache.http.Header;

public class AddFriendActivity extends AppCompatActivity {
    private static final String TAG = "AddFriendActivity";
    private SearchView mSearchView;

    private IFriendModel mFriendModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView = (SearchView) findViewById(R.id.sv_new_friend);
        mFriendModel = new FriendModel(this);

        findViewById(R.id.tv_affirm).setOnClickListener(v -> {
            Log.i(TAG, "click: " + mSearchView.getQuery().toString());
            if (isEmailValid(mSearchView.getQuery().toString()))
                mFriendModel.addFriend(mSearchView.getQuery().toString(), mHttpResponseHandler);
            else
                Toast.makeText(AddFriendActivity.this, R.string.error_invalid_email,
                        Toast.LENGTH_SHORT).show();
        });
    }

    private AsyncHttpResponseHandler mHttpResponseHandler = new AsyncHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i(TAG, "onSuccess: " + statusCode);
            if (new String(responseBody).equalsIgnoreCase("true")) {
                Toast.makeText(AddFriendActivity.this, "已发送好友邀请", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
            else
                Toast.makeText(AddFriendActivity.this, "已经添加Ta为好友啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i(TAG, "onFailure: " + statusCode);
            Toast.makeText(AddFriendActivity.this, R.string.fail_add, Toast.LENGTH_SHORT).show();
        }
    };

    private boolean isEmailValid(String email) {
        return email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
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
}
