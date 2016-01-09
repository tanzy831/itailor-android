package com.thea.itailor.recommend.presenter;

import android.content.Context;

import com.thea.itailor.recommend.bean.Body;
import com.thea.itailor.recommend.model.BodyDataModel;
import com.thea.itailor.recommend.model.IBodyDataModel;
import com.thea.itailor.recommend.view.IBodyDataView;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class BodyDataPresenter {
    private IBodyDataView mBodyDataView;
    private IBodyDataModel mBodyDataModel;

    public BodyDataPresenter(Context context, IBodyDataView mBodyDataView) {
        this.mBodyDataView = mBodyDataView;
        mBodyDataModel = new BodyDataModel(context);
    }

    public void loadBodyData() {
        Body body = mBodyDataModel.getBodyData();
        mBodyDataView.setGender(body.getGender());
        mBodyDataView.setAge(body.getAge());
        mBodyDataView.setFaceShape(body.getBodyFaceShape());
        mBodyDataView.setSkinColor(body.getSkinColor());
        mBodyDataView.setHairColor(body.getHairColor());
        mBodyDataView.setWeight(body.getWeight());
        mBodyDataView.setLength(body.getHeight());
        mBodyDataView.setChest(body.getBust());
        mBodyDataView.setWaistLine(body.getWaist());
        mBodyDataView.setHipLine(body.getHips());
        mBodyDataView.setShoulder(body.getCrossShoulder());
        mBodyDataView.setArmLength(body.getArmLength());
        mBodyDataView.setLegLength(body.getLegLength());
    }

    public void saveBodyData() {
        Body body = new Body(mBodyDataView.getGender(), mBodyDataView.getAge(),
                mBodyDataView.getFaceShape(), mBodyDataView.getSkinColor(), mBodyDataView.getHairColor(),
                mBodyDataView.getWeight(), mBodyDataView.getLength(), mBodyDataView.getChest(),
                mBodyDataView.getWaistLine(),mBodyDataView.getHipLine(), mBodyDataView.getShoulder(),
                mBodyDataView.getArmLength(), mBodyDataView.getLegLength());
        mBodyDataModel.setBodyData(body);
    }
}
