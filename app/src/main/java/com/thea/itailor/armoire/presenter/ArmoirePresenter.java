package com.thea.itailor.armoire.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thea.itailor.R;
import com.thea.itailor.armoire.adapters.ClothAdapter;
import com.thea.itailor.armoire.adapters.MyExpandableAdapter;
import com.thea.itailor.armoire.app.ScanActivity;
import com.thea.itailor.armoire.app.ShowBodyActivity;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.model.ClothModel;
import com.thea.itailor.armoire.model.ClothSQLiteOpenHelper;
import com.thea.itailor.armoire.model.IClothModel;
import com.thea.itailor.armoire.model.ILatticeModel;
import com.thea.itailor.armoire.model.LatticeModel;
import com.thea.itailor.armoire.model.LatticeSQLiteOpenHelper;
import com.thea.itailor.armoire.view.IArmoireView;
import com.thea.itailor.armoire.view.OnMenuSelectedListener;
import com.thea.itailor.armoire.widget.GroupHeaderDecoration;
import com.thea.itailor.armoire.widget.MyItemTouchCallback;
import com.thea.itailor.armoire.widget.SimpleSpanSizeLookup;
import com.thea.itailor.community.app.ShareActivity;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ExtraName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/17 0017.
 */
public class ArmoirePresenter {
    private static final String TAG = "ArmoirePresenter";

    public static final int IMAGE_PATTERN = 22;
    public static final int SMALL_IMAGE_PATTERN = 44;
    public static final int LIST_PATTERN = 33;

    private Context context;
    private Fragment fragment;

    private ILatticeModel mLatticeModel;
    private IClothModel mClothModel;
    private IArmoireView mArmoireView;

    private ClothAdapter mAdapter;
    private MyExpandableAdapter mExpandableAdapter;
    private List<Lattice> mLattices = new ArrayList<>();

    public ArmoirePresenter(Context context, Fragment fragment, IArmoireView mArmoireView) {
        this.context = context;
        this.fragment = fragment;
        this.mArmoireView = mArmoireView;
        mLatticeModel = new LatticeModel(new LatticeSQLiteOpenHelper(context));
        mClothModel = new ClothModel(new ClothSQLiteOpenHelper(context));
    }

    public void checkEmptyView() {
        if (mClothModel.count() == 0 && mArmoireView.getEmptyViewVisibility() != View.VISIBLE) {
            mArmoireView.setEmptyViewVisibility(View.VISIBLE);
        }
        else if (mClothModel.count() != 0 && mArmoireView.getEmptyViewVisibility() == View.VISIBLE) {
            mArmoireView.setEmptyViewVisibility(View.INVISIBLE);
        }
    }

    public void loadArmoire() {
        initData();
        mArmoireView.setAdapter(mAdapter);
        mArmoireView.setLayoutManager(createLayoutManager(2));
        mArmoireView.setCurrentPattern(IMAGE_PATTERN);
        mArmoireView.addItemDecoration(new GroupHeaderDecoration(mAdapter));
//        mArmoireView.addItemDecoration(new ItemDividerDecoration(
//                context, R.drawable.item_divider, ItemDividerDecoration.BOTH_DIVIDER));
//        mArmoireView.addItemDecoration(new SelectedDecoration(context));
        mArmoireView.attachItemTouchHelper(new ItemTouchHelper(new MyItemTouchCallback(mAdapter)));

        mArmoireView.setListAdapter(mExpandableAdapter);
        mArmoireView.setOnChildClickListener(mChildClickListener);
        mArmoireView.setOnItemLongClickListener(mItemLongClickListener);
        expandListGroup();
    }

    public void useImagePattern() {
        int currentPattern = mArmoireView.getCurrentPattern();
        if (currentPattern != IMAGE_PATTERN) {
            if (currentPattern == LIST_PATTERN)
                mArmoireView.setListViewVisibility(View.INVISIBLE);
            mArmoireView.setLayoutManager(createLayoutManager(2));
            mArmoireView.setCurrentPattern(IMAGE_PATTERN);
        }
    }

    public void useSmallImagePattern() {
        int currentPattern = mArmoireView.getCurrentPattern();
        if (currentPattern != SMALL_IMAGE_PATTERN) {
            if (currentPattern == LIST_PATTERN)
                mArmoireView.setListViewVisibility(View.INVISIBLE);
            mArmoireView.setLayoutManager(createLayoutManager(4));
            mArmoireView.setCurrentPattern(SMALL_IMAGE_PATTERN);
        }
    }

    public void useListPattern() {
        if (mArmoireView.getCurrentPattern() != LIST_PATTERN) {
            mExpandableAdapter.notifyDataSetChanged();
            mArmoireView.setListViewVisibility(View.VISIBLE);
            mArmoireView.setCurrentPattern(LIST_PATTERN);
        }
    }

    private void initData() {
        mLattices = mLatticeModel.findAll();
        Log.i(TAG, mLattices.toString());
        for (Lattice lattice : mLattices) {
            lattice.setClothes(mClothModel.find(lattice.getId()));
        }
        /*try {
            mLattices = XmlUtil.parseArmoire(context);
            *//*for (Lattice lattice : mLattices) {
                mClothes.add(lattice.getClothes());
            }*//*
        } catch (XmlPullParserException | IOException e) {
            Log.i(TAG, "error");
            e.printStackTrace();
        }*/
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        mAdapter = new ClothAdapter(mLattices, imageLoader);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mAdapter.setOnItemClickListener(itemClickListener);
        mAdapter.setOnMenuSelectedListener(mMenuSelectedListener);
//        mAdapter.setOnItemLongClickListener(itemLongClickListener);

        mExpandableAdapter = new MyExpandableAdapter(context, mLattices, imageLoader);
        mExpandableAdapter.setOnMenuSelectedListener(mMenuSelectedListener);
    }

    public void insertIntoAdapter(int groupIndex, String name, String filename) {
        Cloth cloth = new Cloth(name, filename);
        mLattices.get(groupIndex).addCloth(0, cloth);
        mAdapter.notifyItemInserted(mAdapter.getCountBefore(groupIndex));
        mExpandableAdapter.notifyDataSetChanged();
    }

    public void updateAdapter(int groupIndex, int childIndex, String name, String description) {
        mLattices.get(groupIndex).getCloth(childIndex).setName(name);
        mLattices.get(groupIndex).getCloth(childIndex).setDescription(description);
        mExpandableAdapter.notifyDataSetChanged();
    }

    public void updateAndMove(int oldGroupIndex, int childIndex, int groupIndex, String name, String description) {
        mLattices.get(groupIndex).getCloth(childIndex).setName(name);
        mLattices.get(groupIndex).getCloth(childIndex).setDescription(description);
        int fromPosition = mAdapter.getCountBefore(oldGroupIndex) + childIndex;
        int toPosition = mAdapter.getCountBefore(groupIndex);
        Cloth cloth = mLattices.get(oldGroupIndex).removeCloth(childIndex);
        mClothModel.update(cloth.getId(), mLattices.get(groupIndex).getId());
        mLattices.get(groupIndex).addCloth(0, cloth);
        mExpandableAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition));
    }

    public void rename(final int groupPosition, final int childPosition) {
        final EditText name = new EditText(context);
        name.setText(getText(groupPosition, childPosition));
        name.setEnabled(true);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        name.setSelection(name.getText().length());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("重命名").setView(name).setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.done, (dialog, which) -> {
                    setText(groupPosition, childPosition, name.getText().toString());
                    mExpandableAdapter.notifyDataSetChanged();
                }).show();
    }

    public void delete(final int groupPosition, final int childPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定删除吗？").setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.affirm, (dialog, which) -> {
                    deleteItem(groupPosition, childPosition);
                    mExpandableAdapter.notifyDataSetChanged();
                }).show();
    }

    public void share(final int groupPosition, final int childPosition) {
        if (childPosition == -1) {
            Intent intent = new Intent(context, ShareActivity.class);
            intent.putStringArrayListExtra(ExtraName.IMAGE_NAMES, getFilenames(mLattices.get(groupPosition)));
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, ShareActivity.class);
            intent.putExtra(ExtraName.IMAGE_NAME,
                    mLattices.get(groupPosition).getCloth(childPosition).getFilename());
            context.startActivity(intent);
        }
    }

    public void addLattice() {
        final EditText name = new EditText(context);
        name.setEnabled(true);
        name.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("新建组名").setView(name).setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.done, (dialog, which) -> {
                    String latticeName = name.getText().toString();
                    Lattice lattice = new Lattice(latticeName, R.mipmap.head_icon);
                    if (mLattices.contains(lattice)) {
                        Toast.makeText(context, R.string.error_same_name, Toast.LENGTH_LONG).show();
                        return;
                    }
                    mLatticeModel.add(latticeName, R.mipmap.head_icon);
                    mLattices.add(0, lattice);
                    mExpandableAdapter.notifyDataSetChanged();
                }).show();
    }

    public void moveTo(final int groupPosition, final int childPosition) {
        String[] items = getAllLatticesName();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, (dialog, which) -> {
            int fromPosition = mAdapter.getCountBefore(groupPosition) + childPosition;
            int toPosition = mAdapter.getCountBefore(which);
            Cloth cloth = mLattices.get(groupPosition).removeCloth(childPosition);
            mClothModel.update(cloth.getId(), mLattices.get(which).getId());
            mLattices.get(which).addCloth(0, cloth);
            mExpandableAdapter.notifyDataSetChanged();
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }).show();
    }

    public void edit(final int groupPosition, final int childPosition) {
        Lattice lattice = mLattices.get(groupPosition);
        Cloth cloth = lattice.getCloth(childPosition);
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra(ExtraName.LATTICE_ID, lattice.getId());
        intent.putExtra(ExtraName.LATTICE_NAME, lattice.getName());
//        intent.putExtra(ExtraName.IMAGE_NAME, cloth.getFilename());
        intent.putExtra(ExtraName.GROUP_POSITION, groupPosition);
        intent.putExtra(ExtraName.CHILD_POSITION, childPosition);
        intent.putExtra(ExtraName.CLOTH, new Gson().toJson(cloth, Cloth.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        fragment.startActivityForResult(intent, Constant.REQUEST_UPDATE_CLOTH);
    }

    public String getText(int groupPosition, int childPosition) {
        return childPosition == -1 ? mLattices.get(groupPosition).getName()
                : mLattices.get(groupPosition).getCloth(childPosition).getName();
    }

    public void setText(int groupPosition, int childPosition, String text) {
        if (childPosition == -1) {
            Lattice lattice = new Lattice(text, R.mipmap.head_icon);
            if (mLattices.contains(lattice)) {
                Toast.makeText(context, R.string.error_same_name, Toast.LENGTH_LONG).show();
                return;
            }
            mLatticeModel.update(lattice.getId(), text);
            mLattices.get(groupPosition).setName(text);
        }
        else {
            for (int i = 0; i < mLattices.size(); i++) {
                if (mLattices.get(i).contains(new Cloth(text))) {
                    Toast.makeText(context, R.string.error_same_name, Toast.LENGTH_LONG).show();
                    return;
                }
            }
            Cloth cloth = mLattices.get(groupPosition).getCloth(childPosition);
            mClothModel.update(cloth.getId(), text);
            cloth.setName(text);
        }
    }

    public void deleteItem(int groupPosition, int childPosition) {
        if (childPosition == -1) {
            Lattice lattice = mLattices.remove(groupPosition);
            mLatticeModel.delete(lattice.getId());
            mLattices.remove(groupPosition);
            mAdapter.notifyItemRangeRemoved(mAdapter.getCountBefore(groupPosition - 1),
                    mAdapter.getCountBefore(groupPosition));
        }
        else {
            Cloth cloth = mLattices.get(groupPosition).removeCloth(childPosition);
            mClothModel.delete(cloth.getId());
            mAdapter.notifyItemRemoved(mAdapter.getCountBefore(groupPosition) + childPosition);
        }
    }

    private ArrayList<String> getFilenames(Lattice lattice) {
        ArrayList<String> filenames = new ArrayList<>();
        for (int i = 0; i < lattice.size(); i++) {
            filenames.add(lattice.getCloth(i).getFilename());
        }
        return filenames;
    }

    /*private ArrayList<String> getImagenames(int accountID, Lattice lattice) {
        ArrayList<String> imageNames = new ArrayList<>();
        for (int i = 0; i < lattice.size(); i++) {
            imageNames.add("account" + accountID + "_" + lattice.getCloth(i).getFilename());
        }
        return imageNames;
    }*/

    private GridLayoutManager createLayoutManager(int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        SimpleSpanSizeLookup spanSizeLookup = new SimpleSpanSizeLookup(mAdapter, spanCount);
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        return layoutManager;
    }

    private void expandListGroup() {
        int count = 0;
        for (int i = 0; i < mExpandableAdapter.getGroupCount(); i++) {
            mArmoireView.expandListGroup(i);
            count += mExpandableAdapter.getChildrenCount(i);
            if (count >= 4)
                break;
        }
    }

    private String[] getAllLatticesName() {
        String[] names = new String[mLattices.size()];
        for (int i = 0; i < mLattices.size(); i++) {
            names[i] = mLattices.get(i).getName();
        }
        return names;
    }

    private ClothAdapter.OnItemClickListener itemClickListener = (view, filename) -> {
        Intent intent = new Intent(context, ShowBodyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(ExtraName.IMAGE_NAME, filename);
        context.startActivity(intent);
    };

    /*private ClothAdapter.OnItemLongClickListener itemLongClickListener = view -> {
//        mArmoireView.showShareView();
//        view.setSelected(true);
    };*/

    private ExpandableListView.OnChildClickListener mChildClickListener =
            (parent, v, groupPosition, childPosition, id) -> {
//            ObjLoaderService.startService(getActivity(), "");
        Intent intent = new Intent(context, ShowBodyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(ExtraName.IMAGE_NAME, mLattices.get(groupPosition).getCloth(childPosition).getFilename());
        context.startActivity(intent);
        return false;
    };

    private OnMenuSelectedListener mMenuSelectedListener =
            (view, groupPosition, childPosition) -> {
        switch (view.getId()) {
            case R.id.iv_share:
                share(groupPosition, childPosition);
                break;
            case R.id.iv_delete:
                delete(groupPosition, childPosition);
                break;
            case R.id.iv_edit:
                edit(groupPosition, childPosition);
                break;
            case R.id.iv_move:
                moveTo(groupPosition, childPosition);
                break;
            case R.id.iv_rename:
                rename(groupPosition, childPosition);
                break;
        }
    };

    private AdapterView.OnItemLongClickListener mItemLongClickListener =
    (parent, view, position, id) -> {
        final int groupPosition = (int) view.getTag(R.string.group_position);
        final int childPosition = (int) view.getTag(R.string.child_position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] dialogMenu = childPosition == -1
                ? context.getResources().getTextArray(R.array.group_edit)
                : context.getResources().getTextArray(R.array.child_edit);
        builder.setItems(dialogMenu, new MyDialogOnClickListener(groupPosition, childPosition)).show();
        return true;
    };

    public class MyDialogOnClickListener implements DialogInterface.OnClickListener {
        private int groupPosition;
        private int childPosition;

        public MyDialogOnClickListener(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == 0)
                rename(groupPosition, childPosition);
            else if (which == 2)
                delete(groupPosition, childPosition);
            else if (which == 3)
                share(groupPosition, childPosition);
            else if (childPosition == -1 && which == 1)
                addLattice();
            else if (childPosition != -1 && which == 1)
                moveTo(groupPosition, childPosition);
        }
    }
}
