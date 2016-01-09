package com.thea.itailor.armoire.app;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thea.itailor.R;
import com.thea.itailor.armoire.presenter.ArmoirePresenter;
import com.thea.itailor.armoire.adapters.MyExpandableAdapter;
import com.thea.itailor.armoire.view.IArmoireView;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.util.ImageHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ArmoireFragment extends Fragment implements IArmoireView {
    private static final String TAG = "ArmoireFragment";

    private ArmoirePresenter presenter;

    private View mView;
    private View emptyView;
    private UltimateRecyclerView mRecyclerView;
    private ExpandableListView listView;

    private int currentPattern;
    private String filename;

    public static ArmoireFragment newInstance() {
        return new ArmoireFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        presenter = new ArmoirePresenter(activity, this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_armoire, container, false);
        setActionBar((Toolbar) mView.findViewById(R.id.toolbar));

        emptyView = mView.findViewById(R.id.rl_empty_armoire);

        mRecyclerView = (UltimateRecyclerView) mView.findViewById(R.id.rv_armoire);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.mRecyclerView.setItemViewCacheSize(30);

        listView = (ExpandableListView) mView.findViewById(R.id.elv_armoire);

        presenter.loadArmoire();
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.checkEmptyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            intent.putExtra(ExtraName.IMAGE_NAME, filename);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivityForResult(intent, Constant.REQUEST_SCAN_ACTIVITY);
        }
        else if (requestCode == Constant.REQUEST_SCAN_ACTIVITY && resultCode == Activity.RESULT_OK) {
            int groupIndex = data.getIntExtra(ExtraName.GROUP_POSITION, 0);
            String name = data.getStringExtra(ExtraName.CLOTH_NAME);
            presenter.insertIntoAdapter(groupIndex, name, filename);
        }
        else if (requestCode == Constant.REQUEST_UPDATE_CLOTH && resultCode == Activity.RESULT_OK) {
            int groupIndex = data.getIntExtra(ExtraName.GROUP_POSITION, 0);
            int oldGroupIndex = data.getIntExtra(ExtraName.OLD_GROUP_POSITION, -1);
            int childIndex = data.getIntExtra(ExtraName.CHILD_POSITION, -1);
            String name = data.getStringExtra(ExtraName.CLOTH_NAME);
            String story = data.getStringExtra(ExtraName.CLOTH_STORY);
            if (groupIndex == oldGroupIndex)
                presenter.updateAdapter(groupIndex, childIndex, name, story);
            else
                presenter.updateAndMove(oldGroupIndex, childIndex, groupIndex, name, story);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_armoire, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_as_grid:
            presenter.useImagePattern();
            return true;
        case R.id.action_as_small_grid:
            presenter.useSmallImagePattern();
            return true;
        case R.id.action_as_list:
            presenter.useListPattern();
            return true;
        case R.id.action_camera:
            scan();
            return true;
        case R.id.action_new_group:
            presenter.addLattice();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBar(Toolbar toolbar) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.my_armoire);
    }

    public void scan() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
        filename = "C" + timeFormat.format(new Date()) + ".jpg";
        File file = ImageHelper.createNewFile(Constant.DIRECTORY_ARMOIRE, "/" + filename);
        Uri uri = Uri.fromFile(file);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(imageCaptureIntent, Constant.REQUEST_CAMERA);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setAdapter(UltimateViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setListViewVisibility(int visibility) {
        listView.setVisibility(visibility);
    }

    @Override
    public void setListAdapter(MyExpandableAdapter expandableAdapter) {
        listView.setAdapter(expandableAdapter);
    }

    @Override
    public void expandListGroup(int groupPos) {
        listView.expandGroup(groupPos, true);
    }

    @Override
    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        listView.setOnChildClickListener(onChildClickListener);
    }

    @Override
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        listView.setOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public int getCurrentPattern() {
        return currentPattern;
    }

    @Override
    public void setCurrentPattern(int pattern) {
        this.currentPattern = pattern;
    }

    @Override
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void attachItemTouchHelper(ItemTouchHelper touchHelper) {
        touchHelper.attachToRecyclerView(mRecyclerView.mRecyclerView);
    }

    @Override
    public int getEmptyViewVisibility() {
        return emptyView.getVisibility();
    }

    @Override
    public void setEmptyViewVisibility(int visibility) {
        emptyView.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            emptyView.findViewById(R.id.btn_go_add).setOnClickListener(v -> scan());
        }
    }
}
