package com.thea.itailor.community.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thea.itailor.R;
import com.thea.itailor.community.presenter.ImagesPresenter;
import com.thea.itailor.community.view.IRecyclerView;

public class ImagesActivity extends AppCompatActivity implements IRecyclerView {

    private ImagesPresenter mPresenter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);

        findViewById(R.id.btn_cancel_add).setOnClickListener(mClickListener);
        findViewById(R.id.btn_affirm_add).setOnClickListener(mClickListener);

        mPresenter = new ImagesPresenter(this, this);
        mPresenter.loadImages();
    }

    private View.OnClickListener mClickListener = v -> {
        if (v.getId() == R.id.btn_affirm_add)
            setResult(RESULT_OK, mPresenter.resultIntent());
        finish();
    };

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
