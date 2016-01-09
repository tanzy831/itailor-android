package com.thea.itailor.armoire.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.view.AnimateFirstDisplayListener;
import com.thea.itailor.armoire.view.OnMenuSelectedListener;
import com.thea.itailor.config.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/7/23.
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "MyExpandableAdapter";
    private Context context;
    private ImageLoader imageLoader;

    private final List<Lattice> groups;

    private OnMenuSelectedListener onMenuSelectedListener;

    private DisplayImageOptions options;
    private ImageLoadingListener imageLoadingListener = new AnimateFirstDisplayListener();

    public MyExpandableAdapter(Context context, List<Lattice> groups, ImageLoader imageLoader) {
        this.context = context;
        this.groups = groups;
        this.imageLoader = imageLoader;

//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.loading_image)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).size();
    }

    @Override
    public Lattice getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Cloth getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getCloth(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*100 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_group, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.tv_group_name);
        name.setText(getGroup(groupPosition).getName());
        TextView count = (TextView) convertView.findViewById(R.id.tv_group_count);
        count.setText(getChildrenCount(groupPosition) + "");
        convertView.setTag(R.string.group_position, groupPosition);
        convertView.setTag(R.string.child_position, -1);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_child, parent, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.iv_cloth);
            holder.tv = (TextView) view.findViewById(R.id.tv_cloth_name);
            holder.delete = view.findViewById(R.id.iv_delete);
            holder.share = view.findViewById(R.id.iv_share);
            holder.move = view.findViewById(R.id.iv_move);
            holder.rename = view.findViewById(R.id.iv_rename);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        Cloth cloth = getChild(groupPosition, childPosition);
        holder.tv.setText(cloth.getName());
        String uri = "file://" + Constant.PATH + Constant.DIRECTORY_ARMOIRE + "/" + cloth.getFilename();
        imageLoader.displayImage(uri, holder.iv, options, imageLoadingListener);
        view.setTag(R.string.group_position, groupPosition);
        view.setTag(R.string.child_position, childPosition);
        holder.delete.setOnClickListener(v ->
                onMenuSelectedListener.onMenuSelected(v, groupPosition, childPosition));
        holder.share.setOnClickListener(v ->
                onMenuSelectedListener.onMenuSelected(v, groupPosition, childPosition));
        holder.move.setOnClickListener(v ->
                onMenuSelectedListener.onMenuSelected(v, groupPosition, childPosition));
        holder.rename.setOnClickListener(v ->
                onMenuSelectedListener.onMenuSelected(v, groupPosition, childPosition));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        List<Lattice> lattices = new ArrayList<>();
        lattices.addAll(groups);
        groups.clear();
        groups.addAll(lattices);
        super.notifyDataSetChanged();
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv;

        View share;
        View delete;
        View move;
        View rename;
    }
}
