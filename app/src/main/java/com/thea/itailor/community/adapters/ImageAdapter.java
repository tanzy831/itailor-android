package com.thea.itailor.community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thea.itailor.R;
import com.thea.itailor.armoire.view.AnimateFirstDisplayListener;
import com.thea.itailor.config.Path;

/**
 * Created by Thea on 2015/8/30 0030.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context context;
    private String[] imageNames;
    private int count;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener = new AnimateFirstDisplayListener();

    private OnSelectedChangeListener onSelectedChangeListener;

    public ImageAdapter(String[] imageNames, ImageLoader imageLoader) {
        this.imageNames = imageNames;
        count = imageNames.length;
        this.imageLoader = imageLoader;
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.image1)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_selectable_image, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String name = imageNames[position];
//        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")) {
            String uri = "file://" + Path.BASE_PATH + Path.DIRECTORY_ARMOIRE + "/" + name;
            imageLoader.displayImage(uri, holder.iv, options, imageLoadingListener);
            holder.cb.setOnCheckedChangeListener((buttonView, isChecked) ->
                    onSelectedChangeListener.OnSelectedChange(position, isChecked));/*
        }
        else {
            System.arraycopy(imageNames, position + 1, imageNames, position, --count - position);
            imageNames[count] = null;
            notifyItemRemoved(position);
        }*/
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        this.onSelectedChangeListener = onSelectedChangeListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public CheckBox cb;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_selectable_image);
            cb = (CheckBox) itemView.findViewById(R.id.cb_selectable_box);
        }
    }

    public interface OnSelectedChangeListener {
        void OnSelectedChange(int position, boolean isSelected);
    }
}
