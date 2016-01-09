package com.thea.itailor.community.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thea.itailor.R;
import com.thea.itailor.community.presenter.DetailsPresenter;
import com.thea.itailor.community.view.IDetailsView;
import com.thea.itailor.config.ExtraName;

public class DetailsActivity extends AppCompatActivity implements IDetailsView {
    private static final String TAG = "DetailsActivity";

    private DetailsPresenter mPresenter;

    private ImageView icon;
    private TextView name;
    private TextView description;
    private GridLayout images;
    private TextView shareTime;
    private TextView favourPeople;
    private View favourPeopleLayout;
    private RecyclerView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        icon = (ImageView) findViewById(R.id.iv_user_icon);
        name = (TextView) findViewById(R.id.tv_user_name);
        description = (TextView) findViewById(R.id.tv_share_description);
        images = (GridLayout) findViewById(R.id.gl_share_images);
        shareTime = (TextView) findViewById(R.id.tv_share_time);
        favourPeople = (TextView) findViewById(R.id.tv_favour_people);
        favourPeopleLayout = findViewById(R.id.rl_favour_people);
        comments = (RecyclerView) findViewById(R.id.rv_comments);

        mPresenter = new DetailsPresenter(this, this, getIntent().getStringExtra(ExtraName.SHARE_ITEM));
        mPresenter.loadDetails();

        findViewById(R.id.ib_favour_icon).setOnClickListener(v -> mPresenter.onFavor());
        findViewById(R.id.ib_comment_icon).setOnClickListener(v -> mPresenter.onComment());
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

    @Override
    public GridLayout getImagesLayout() {
        return images;
    }

    @Override
    public void setUserIcon(Bitmap bitmap) {
        icon.setImageBitmap(bitmap);
    }

    @Override
    public void setUserName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void addImage(ImageView imageView) {
        images.addView(imageView);
    }

    @Override
    public void setTime(String time) {
        shareTime.setText(time);
    }

    @Override
    public void addFavor(String favor) {
        if (favourPeople.getText().length() != 0)
            favourPeople.append(", ");
        favourPeople.append(favor);
        favourPeopleLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        comments.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        comments.setLayoutManager(layoutManager);
    }

    @Override
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        comments.addItemDecoration(itemDecoration);
    }
}
