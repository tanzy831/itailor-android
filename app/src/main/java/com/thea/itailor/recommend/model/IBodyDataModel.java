package com.thea.itailor.recommend.model;

import com.thea.itailor.recommend.bean.Body;
import com.thea.itailor.recommend.bean.BodyData;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface IBodyDataModel {
    BodyData getBodyData2();

    Body getBodyData();

    void setBodyData(Body body);

    void setGender(int gender);

    void setAge(int age);

    void setFaceShape(int shape);

    void setSkinColor(int color);

    void setHairColor(int color);

    void setWeight(int weight);

    void setLength(int length);

    void setChest(int chest);

    void setWaistLine(int waistLine);

    void setHipLine(int hipLine);

    void setShoulder(int shoulder);

    void setArmLength(int armLength);

    void setLegLength(int length);
}
