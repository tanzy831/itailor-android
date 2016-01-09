package com.thea.itailor.armoire.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thea.itailor.R;
import com.thea.itailor.armoire.app.ArmoireFragment;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;
import com.thea.itailor.armoire.model.ClothModel;
import com.thea.itailor.armoire.model.ClothSQLiteOpenHelper;
import com.thea.itailor.armoire.model.LatticeModel;
import com.thea.itailor.armoire.model.LatticeSQLiteOpenHelper;
import com.thea.itailor.armoire.view.IStoreClothView;
import com.thea.itailor.config.Constant;
import com.thea.itailor.config.ExtraName;
import com.thea.itailor.util.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/17 0017.
 */
public class ScanPresenter {
    private static final String TAG = "ScanPresenter";

    private Activity context;

    private IStoreClothView mStoreClothView;
    private LatticeModel mLatticeModel;
    private ClothModel mClothModel;

    private List<Lattice> lattices = new ArrayList<>();

    public ScanPresenter(Activity context, IStoreClothView mStoreClothView) {
        this.context = context;
        this.mStoreClothView = mStoreClothView;
        mLatticeModel = new LatticeModel(new LatticeSQLiteOpenHelper(context));
        mClothModel = new ClothModel(new ClothSQLiteOpenHelper(context));
    }

    public void loadLattices() {
        lattices = mLatticeModel.findAll();
        ArrayAdapter<Lattice> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, lattices);
        mStoreClothView.setAdapter(adapter);
    }

    public void loadClothImage(Cloth cloth) {
        mStoreClothView.setName(cloth.getName());
        mStoreClothView.setStory(cloth.getDescription());
        mStoreClothView.setTime(cloth.getCreatedTime().toString());
        mStoreClothView.setClothImage(ImageHelper.getImageFromStore(
                Constant.DIRECTORY_ARMOIRE, "/" + cloth.getFilename()));
    }

    public void storeCloth() {
        Lattice lattice = (Lattice) mStoreClothView.getSelectedLattice();
        if (mStoreClothView.getClothId() == -1) {
            mClothModel.add(mStoreClothView.getName(), mStoreClothView.getFilename(),
                    mStoreClothView.getStory(), lattice.getId());
            Toast.makeText(context, R.string.successful_save, Toast.LENGTH_SHORT).show();
        }
        else {
            mClothModel.update(mStoreClothView.getClothId(), mStoreClothView.getName(),
                    mStoreClothView.getStory(), lattice.getId());
            Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(context, ArmoireFragment.class);
        intent.putExtra(ExtraName.GROUP_POSITION, mStoreClothView.getSelectedLatticePosition());
        intent.putExtra(ExtraName.CLOTH_NAME, mStoreClothView.getName());
        intent.putExtra(ExtraName.CLOTH_STORY, mStoreClothView.getStory());
        intent.putExtra(ExtraName.CHILD_POSITION, mStoreClothView.getChildPosition());
        intent.putExtra(ExtraName.OLD_GROUP_POSITION, mStoreClothView.getOldGroupPosition());
        context.setResult(Activity.RESULT_OK, intent);
        context.finish();
    }
}
