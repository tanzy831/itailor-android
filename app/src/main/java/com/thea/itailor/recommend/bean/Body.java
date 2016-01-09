package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Body {
    private int gender;
    private int age;
    private int bodyFaceShape;
    private int skinColor;
    private int hairColor;
    private int weight;
    private int height;
    private int bust;
    private int waist;
    private int hips;
    private int crossShoulder;
    private int armLength;
    private int legLength;

    private String gender2;
    private String bodyFaceShape2;
    private String skinColor2;
    private String bodyHairColor2;

    public Body(int gender, int age, int bodyFaceShape, int skinColor, int hairColor, int weight,
                int height, int bust, int waist, int hips, int crossShoulder, int armLength, int legLength) {
        this.gender = gender;
        this.age = age;
        this.bodyFaceShape = bodyFaceShape;
        this.skinColor = skinColor;
        this.hairColor = hairColor;
        this.weight = weight;
        this.height = height;
        this.bust = bust;
        this.waist = waist;
        this.hips = hips;
        this.crossShoulder = crossShoulder;
        this.armLength = armLength;
        this.legLength = legLength;
    }

    public Body(int gender, int age, int bodyFaceShape, int skinColor, int hairColor, int weight,
                int height, int bust, int waist, int hips, int crossShoulder, int armLength, int legLength, String gender2, String bodyFaceShape2, String skinColor2, String bodyHairColor2) {
        this.gender = gender;
        this.age = age;
        this.bodyFaceShape = bodyFaceShape;
        this.skinColor = skinColor;
        this.hairColor = hairColor;
        this.weight = weight;
        this.height = height;
        this.bust = bust;
        this.waist = waist;
        this.hips = hips;
        this.crossShoulder = crossShoulder;
        this.armLength = armLength;
        this.legLength = legLength;
        this.gender2 = gender2;
        this.bodyFaceShape2 = bodyFaceShape2;
        this.skinColor2 = skinColor2;
        this.bodyHairColor2 = bodyHairColor2;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBodyFaceShape() {
        return bodyFaceShape;
    }

    public void setBodyFaceShape(int bodyFaceShape) {
        this.bodyFaceShape = bodyFaceShape;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getHips() {
        return hips;
    }

    public void setHips(int hips) {
        this.hips = hips;
    }

    public int getCrossShoulder() {
        return crossShoulder;
    }

    public void setCrossShoulder(int crossShoulder) {
        this.crossShoulder = crossShoulder;
    }

    public int getArmLength() {
        return armLength;
    }

    public void setArmLength(int armLength) {
        this.armLength = armLength;
    }

    public int getLegLength() {
        return legLength;
    }

    public void setLegLength(int legLength) {
        this.legLength = legLength;
    }

    public String getGender2() {
        return gender2;
    }

    public String getBodyFaceShape2() {
        return bodyFaceShape2;
    }

    public String getSkinColor2() {
        return skinColor2;
    }

    public String getBodyHairColor2() {
        return bodyHairColor2;
    }
}
