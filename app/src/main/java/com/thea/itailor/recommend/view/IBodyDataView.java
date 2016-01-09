package com.thea.itailor.recommend.view;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public interface IBodyDataView {
    int getGender();

    int getAge();

    int getFaceShape();

    int getSkinColor();

    int getHairColor();

    int getWeight();

    int getLength();

    int getChest();

    int getWaistLine();

    int getHipLine();

    int getShoulder();

    int getArmLength();

    int getLegLength();

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

    void setLegLength(int legLength);
}
