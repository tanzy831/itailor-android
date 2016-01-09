package com.thea.itailor.community.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thea.itailor.R;
import com.thea.itailor.community.adapters.ImageAdapter;
import com.thea.itailor.community.app.ShareActivity;
import com.thea.itailor.community.view.IRecyclerView;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.config.Path;
import com.thea.itailor.widget.ItemDividerDecoration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thea on 2015/8/30 0030.
 */
public class ImagesPresenter implements ImageAdapter.OnSelectedChangeListener {
    private static final String TAG = "ImagesPresenter";

    private Context context;
    private String[] mImageNames;

    private IRecyclerView mImagesView;
    private ArrayList<String> mSelectedImages = new ArrayList<>();

    private ImageLoader imageLoader;

    public ImagesPresenter(Context context, IRecyclerView mImagesView) {
        this.context = context;
        this.mImagesView = mImagesView;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        mImageNames = new File(Path.BASE_PATH + Path.DIRECTORY_ARMOIRE).list();
    }

    public void loadImages() {
        ImageAdapter adapter = new ImageAdapter(mImageNames, imageLoader);
        adapter.setOnSelectedChangeListener(this);
        mImagesView.setLayoutManager(new GridLayoutManager(context, 4));
        mImagesView.setAdapter(adapter);
        mImagesView.addItemDecoration(new ItemDividerDecoration(context,
                R.drawable.item_divider, ItemDividerDecoration.BOTH_DIVIDER));
    }

    public Intent resultIntent() {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putStringArrayListExtra(ExtraName.IMAGE_NAMES, mSelectedImages);
        return intent;
    }

    @Override
    public void OnSelectedChange(int position, boolean isSelected) {
        String name = mImageNames[position];
        if (isSelected && !mSelectedImages.contains(name))
            mSelectedImages.add(name);
        else if (!isSelected && mSelectedImages.contains(name))
            mSelectedImages.remove(name);
    }
}
