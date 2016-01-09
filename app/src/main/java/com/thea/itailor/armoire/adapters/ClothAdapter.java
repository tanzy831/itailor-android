package com.thea.itailor.armoire.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.view.AnimateFirstDisplayListener;
import com.thea.itailor.armoire.view.OnMenuSelectedListener;
import com.thea.itailor.config.Constant;

import java.util.List;

/**
 * Created by Thea on 2015/8/17 0017.
 */
public class ClothAdapter extends SwipeableUltimateViewAdapter implements ItemTouchHelperAdapter {
    private static final String TAG = "ClothAdapter";

    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener = new AnimateFirstDisplayListener();

    private final List<Lattice> lattices;

    private OnItemClickListener onItemClickListener;
    private OnMenuSelectedListener onMenuSelectedListener;

    public ClothAdapter(List<Lattice> lattices, ImageLoader imageLoader) {
        this.lattices = lattices;
        this.imageLoader = imageLoader;

        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.loading_image)
                .considerExifParams(true)
                .build();
    }

    /*@Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_image, viewGroup, false));
    }*/

    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_image, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(UltimateRecyclerviewViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        int[] index = getItemIndex(position);
        Cloth cloth = lattices.get(index[0]).getCloth(index[1]);
//        viewHolder.itemView.setOnClickListener(this);
        SwipeLayout swipeLayout = viewHolder.swipeLayout;
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//        swipeLayout.setOnDoubleClickListener((layout, surface) ->
//                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show());
        viewHolder.iv.setOnClickListener(v1 -> {if (onItemClickListener != null)
            onItemClickListener.onItemClick(v1, cloth.getFilename());});
        String uri = "file://" + Constant.PATH + Constant.DIRECTORY_ARMOIRE + "/" + cloth.getFilename();
        imageLoader.displayImage(uri, viewHolder.iv, options, imageLoadingListener);

        viewHolder.delete.setOnClickListener(v -> onItemSelected(viewHolder, v));
        viewHolder.share.setOnClickListener(v -> onItemSelected(viewHolder, v));
        viewHolder.edit.setOnClickListener(v -> onItemSelected(viewHolder, v));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Lattice child : lattices) {
            count += child.size();
        }
        return count;
    }

    @Override
    public int getAdapterItemCount() {
        return getItemCount();
    }

    @Override
    public long generateHeaderId(int i) {
        return i;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
    }

    public int[] getItemIndex(int position) {
        int[] index = new int[2];
        int count = 0;
        for (Lattice child : lattices) {
            count += child.size();
            if (count > position) {
                index[1] = position + child.size() - count;
                break;
            }
            index[0]++;
        }
        return index;
    }

    public int getOneGroupCount(int index) {
        return lattices.get(index).size();
    }

    public int getCountBefore(int index) {
        int count = 0;
        for (int i = 0; i < index; i++) {
            count += getOneGroupCount(i);
        }
        return count;
    }

    public boolean isLastChild(int[] index) {
        return index[1] == getOneGroupCount(index[0]) - 1;
    }

    public boolean isFirstRawChild(int position, int spanCount) {
        int[] index = getItemIndex(position);
        return index[1] >= 0 && index[1] < spanCount;
    }


    public boolean isLastRawChild(int[] index, int spanCount) {
        int count = getOneGroupCount(index[0]);
        return index[1] < count && index[1] >= count - count % spanCount;
    }

    public int getGroupCount() {
        return lattices.size();
    }

    public Lattice getLattice(int groupIndex) {
        return lattices.get(groupIndex);
    }

    public void onItemSelected(RecyclerView.ViewHolder viewHolder, View v) {
        int position = viewHolder.getAdapterPosition();
        notifyItemChanged(position);
        int[] index = getItemIndex(position);
        if (onMenuSelectedListener != null)
            onMenuSelectedListener.onMenuSelected(v, index[0], index[1]);
    }

    /*public void onItemDelete(RecyclerView.ViewHolder viewHolder, View v) {
        int position = viewHolder.getAdapterPosition();
        notifyItemChanged(position);
        int[] index = getItemIndex(position);
        Log.i(TAG, "position: " + position + lattices.get(index[0]).getCloth(index[1]).getFilename());
        if (onMenuSelectedListener != null)
            onMenuSelectedListener.onMenuSelected(v, index[0], index[1]);
    }

    public void onItemShare(RecyclerView.ViewHolder viewHolder, View v) {
        int position = viewHolder.getAdapterPosition();
        notifyItemChanged(position);
        int[] index = getItemIndex(position);
        if (onMenuSelectedListener != null)
            onMenuSelectedListener.onMenuSelected(v, index[0], index[1]);
    }

    public void onItemEdit(RecyclerView.ViewHolder viewHolder, View v) {
        int position = viewHolder.getAdapterPosition();
        notifyItemChanged(position);
        int[] index = getItemIndex(position);
        if (onMenuSelectedListener != null)
            onMenuSelectedListener.onMenuSelected(v, index[0], index[1]);
    }*/

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        int[] fromIndex = getItemIndex(fromPosition);
        int[] toIndex = getItemIndex(toPosition);
        Log.i(TAG, "From: " + fromIndex[0] + "  " + fromIndex[1]);
        Log.i(TAG, "To: " + toIndex[0] + "  " + toIndex[1]);

        if (fromIndex[0] == toIndex[0] && fromIndex[1] == toIndex[1])
            return;
        else {
            Cloth cloth = lattices.get(fromIndex[0]).removeCloth(fromIndex[1]);
            lattices.get(toIndex[0]).addCloth(toIndex[1], cloth);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        Log.i(TAG, "onItemDismiss");
    }

    public class MyViewHolder extends UltimateRecyclerviewViewHolder {
//        public RippleView rv;
        public ImageView iv;
        public ImageView delete;
        public ImageView share;
        public ImageView edit;

        public MyViewHolder(View itemView) {
            super(itemView);
//            rv = (RippleView) itemView.findViewById(R.id.rv_image_cloth);
            iv = (ImageView) itemView.findViewById(R.id.iv_image_cloth);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            share = (ImageView) itemView.findViewById(R.id.iv_share);
            edit = (ImageView) itemView.findViewById(R.id.iv_edit);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String filename);
    }
}
