package com.thea.itailor.recommend.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thea.itailor.R;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.recommend.adapters.ImageAdapter;
import com.thea.itailor.widget.ItemDividerDecoration;

public class ColorImageActivity extends AppCompatActivity {
    private static final String TAG = "ColorImageActivity";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_image);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_color_images);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ImageAdapter(this, getIntent().getStringArrayListExtra(ExtraName.IMAGE_NAMES)));
        mRecyclerView.addItemDecoration(new ItemDividerDecoration(
                this, R.drawable.item_divider, ItemDividerDecoration.VERTICAL_DIVIDER));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.grow_fade_in_from_bottom, R.anim.shrink_fade_out_from_top);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
