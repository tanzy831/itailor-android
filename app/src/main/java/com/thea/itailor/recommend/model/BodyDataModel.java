package com.thea.itailor.recommend.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.thea.itailor.R;
import com.thea.itailor.recommend.bean.Body;
import com.thea.itailor.recommend.bean.BodyData;

/**
 * Created by Thea on 2015/8/16 0016.
 */
public class BodyDataModel implements IBodyDataModel {
    public static final String SP_BODY_DATA = "body_data";

    public static final String GENDER = "gender";
    public static final String GENDER2 = "gender2";
    public static final String AGE = "age";
    public static final String FACE_SHAPE = "face_shape";
    public static final String FACE_SHAPE2 = "face_shape2";
    public static final String SKIN_COLOR = "skin_color";
    public static final String SKIN_COLOR2 = "skin_color2";
    public static final String HAIR_COLOR = "hair_color";
    public static final String HAIR_COLOR2 = "hair_color2";
    public static final String WEIGHT = "weight";
    public static final String BODY_LENGTH = "body_length";
    public static final String CHEST = "chest";
    public static final String WAIST_LINE = "waist_line";
    public static final String HIP_LINE = "hip_line";
    public static final String SHOULDER = "shoulder";
    public static final String ARM_LENGTH = "arm_length";
    public static final String LEG_LENGTH = "leg_length";

    private Context context;
    private SharedPreferences sp;

    public BodyDataModel(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_BODY_DATA, Context.MODE_PRIVATE);
    }

    @Override
    public BodyData getBodyData2() {
        int gender = sp.getInt(GENDER, 1);
        int age = sp.getInt(AGE, 10) + 15;
        int faceShape = sp.getInt(FACE_SHAPE, 0);
        int skinColor = sp.getInt(SKIN_COLOR, 4);
        int hairColor = sp.getInt(HAIR_COLOR, 5);
        int weight = sp.getInt(WEIGHT, 20) + 25;
        int length = sp.getInt(BODY_LENGTH, 30) + 140;
        int chest = sp.getInt(CHEST, 20) + 70;
        int waistLine = sp.getInt(WAIST_LINE, 15) + 45;
        int hipLine = sp.getInt(HIP_LINE, 30) + 60;
        int shoulder = sp.getInt(SHOULDER, 15) + 30;
        int armLength = sp.getInt(ARM_LENGTH, 30) + 40;
        int legLength = sp.getInt(LEG_LENGTH, 60) + 60;

        String gender2 = context.getResources().getStringArray(R.array.gender)[gender];
        String faceShape2 = context.getResources().getStringArray(R.array.face_shape)[faceShape];
        String skinColor2 = context.getResources().getStringArray(R.array.skin_color)[skinColor];
        String hairColor2 = context.getResources().getStringArray(R.array.hair_color)[hairColor];
        return new BodyData(age, weight, length, chest,
                waistLine, hipLine, shoulder, armLength, legLength,
                gender2, faceShape2, skinColor2, hairColor2);
    }

    @Override
    public Body getBodyData() {
        int gender = sp.getInt(GENDER, 1);
        int age = sp.getInt(AGE, 10);
        int faceShape = sp.getInt(FACE_SHAPE, 0);
        int skinColor = sp.getInt(SKIN_COLOR, 4);
        int hairColor = sp.getInt(HAIR_COLOR, 5);
        int weight = sp.getInt(WEIGHT, 20);
        int length = sp.getInt(BODY_LENGTH, 30);
        int chest = sp.getInt(CHEST, 20);
        int waistLine = sp.getInt(WAIST_LINE, 15);
        int hipLine = sp.getInt(HIP_LINE, 30);
        int shoulder = sp.getInt(SHOULDER, 15);
        int armLength = sp.getInt(ARM_LENGTH, 30);
        int legLength = sp.getInt(LEG_LENGTH, 60);

        String gender2 = context.getResources().getStringArray(R.array.gender)[gender];
        String faceShape2 = context.getResources().getStringArray(R.array.face_shape)[faceShape];
        String skinColor2 = context.getResources().getStringArray(R.array.skin_color)[skinColor];
        String hairColor2 = context.getResources().getStringArray(R.array.hair_color)[hairColor];
        return new Body(gender, age, faceShape, skinColor, hairColor, weight, length, chest,
                waistLine, hipLine, shoulder, armLength, legLength,
                gender2, faceShape2, skinColor2, hairColor2);
    }

    @Override
    public void setBodyData(Body body) {
        setGender(body.getGender());
        setAge(body.getAge());
        setFaceShape(body.getBodyFaceShape());
        setSkinColor(body.getSkinColor());
        setHairColor(body.getHairColor());
        setWeight(body.getWeight());
        setLength(body.getHeight());
        setChest(body.getBust());
        setWaistLine(body.getWaist());
        setHipLine(body.getHips());
        setShoulder(body.getCrossShoulder());
        setArmLength(body.getArmLength());
        setLegLength(body.getLegLength());
    }

    @Override
    public void setGender(int gender) {
        sp.edit().putInt(GENDER, gender).apply();
    }

    @Override
    public void setAge(int age) {
        sp.edit().putInt(AGE, age).apply();
    }

    @Override
    public void setFaceShape(int shape) {
        sp.edit().putInt(FACE_SHAPE, shape).apply();
    }

    @Override
    public void setSkinColor(int color) {
        sp.edit().putInt(SKIN_COLOR, color).apply();
    }

    @Override
    public void setHairColor(int color) {
        sp.edit().putInt(HAIR_COLOR, color).apply();
    }

    @Override
    public void setWeight(int weight) {
        sp.edit().putInt(WEIGHT, weight).apply();
    }

    @Override
    public void setLength(int length) {
        sp.edit().putInt(BODY_LENGTH, length).apply();
    }

    @Override
    public void setChest(int chest) {
        sp.edit().putInt(CHEST, chest).apply();
    }

    @Override
    public void setWaistLine(int waistLine) {
        sp.edit().putInt(WAIST_LINE, waistLine).apply();
    }

    @Override
    public void setHipLine(int hipLine) {
        sp.edit().putInt(HIP_LINE, hipLine).apply();
    }

    @Override
    public void setShoulder(int shoulder) {
        sp.edit().putInt(SHOULDER, shoulder).apply();
    }

    @Override
    public void setArmLength(int armLength) {
        sp.edit().putInt(ARM_LENGTH, armLength).apply();
    }

    @Override
    public void setLegLength(int length) {
        sp.edit().putInt(LEG_LENGTH, length).apply();
    }
}
