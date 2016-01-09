package com.thea.itailor.community.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.thea.itailor.R;
import com.thea.itailor.community.presenter.LoginPresenter;
import com.thea.itailor.community.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private static final String TAG = "LoginActivity";

    private LoginPresenter mPresenter;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.actv_email);
        mPasswordView = (EditText) findViewById(R.id.et_password);

        mPresenter = new LoginPresenter(this, this);

        mEmailView.setOnEditorActionListener((v, actionId, event) -> false);

        mPasswordView.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL)
                mPresenter.checkEmailAndPassword();
            return false;
        });

        findViewById(R.id.btn_login).setOnClickListener(v -> mPresenter.login());

        findViewById(R.id.btn_register).setOnClickListener(view -> mPresenter.register());

        findViewById(R.id.btn_qq_login).setOnClickListener(v ->
                mPresenter.qqLogin());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getEmail() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void setEmail(String email) {
        mEmailView.setText(email);
    }

    @Override
    public void setPassword(String password) {
        mPasswordView.setText(password);
    }

    @Override
    public void setEmailError(String error) {
        mEmailView.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        mPasswordView.setError(error);
    }
}
