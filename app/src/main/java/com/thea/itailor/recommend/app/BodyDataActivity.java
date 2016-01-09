package com.thea.itailor.recommend.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thea.itailor.R;
import com.thea.itailor.recommend.presenter.BodyDataPresenter;
import com.thea.itailor.recommend.view.IBodyDataView;
import com.thea.itailor.services.BodyDataService;

public class BodyDataActivity extends AppCompatActivity implements IBodyDataView {
    private BodyDataPresenter presenter;

    private SeekBar genderSeekBar;
    private TextView genderText;

    private SeekBar ageSeekBar;
    private TextView ageText;

    private SeekBar fsSeekBar;
    private TextView faceShapeText;

    private SeekBar scSeekBar;
    private TextView skinColorText;

    private SeekBar hcSeekBar;
    private TextView hairColorText;

    private SeekBar weightSeekBar;
    private TextView weightText;

    private SeekBar blSeekBar;
    private TextView bodyLengthText;

    private SeekBar chestSeekBar;
    private TextView chestText;

    private SeekBar wlSeekBar;
    private TextView waistLineText;

    private SeekBar hlSeekBar;
    private TextView hipLineText;

    private SeekBar shoulderSeekBar;
    private TextView shoulderText;

    private SeekBar alSeekBar;
    private TextView armLengthText;

    private SeekBar llSeekBar;
    private TextView legLengthText;

    private boolean backing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_data);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new BodyDataPresenter(this, this);

        genderSeekBar = (SeekBar) findViewById(R.id.sb_gender);
        genderText = (TextView) findViewById(R.id.tv_gender);
        genderSeekBar.setOnSeekBarChangeListener(mChangeListener);

        ageSeekBar = (SeekBar) findViewById(R.id.sb_age);
        ageText = (TextView) findViewById(R.id.tv_age);
        ageSeekBar.setOnSeekBarChangeListener(mChangeListener);

        fsSeekBar = (SeekBar) findViewById(R.id.sb_face_shape);
        faceShapeText = (TextView) findViewById(R.id.tv_face_shape);
        fsSeekBar.setOnSeekBarChangeListener(mChangeListener);

        scSeekBar = (SeekBar) findViewById(R.id.sb_skin_color);
        skinColorText = (TextView) findViewById(R.id.tv_skin_color);
        scSeekBar.setOnSeekBarChangeListener(mChangeListener);

        hcSeekBar = (SeekBar) findViewById(R.id.sb_hair_color);
        hairColorText = (TextView) findViewById(R.id.tv_hair_color);
        hcSeekBar.setOnSeekBarChangeListener(mChangeListener);

        weightSeekBar = (SeekBar) findViewById(R.id.sb_weight);
        weightText = (TextView) findViewById(R.id.tv_weight);
        weightSeekBar.setOnSeekBarChangeListener(mChangeListener);

        blSeekBar = (SeekBar) findViewById(R.id.sb_body_length);
        bodyLengthText = (TextView) findViewById(R.id.tv_body_length);
        blSeekBar.setOnSeekBarChangeListener(mChangeListener);

        chestSeekBar = (SeekBar) findViewById(R.id.sb_chest);
        chestText = (TextView) findViewById(R.id.tv_chest);
        chestSeekBar.setOnSeekBarChangeListener(mChangeListener);

        wlSeekBar = (SeekBar) findViewById(R.id.sb_waist_line);
        waistLineText = (TextView) findViewById(R.id.tv_waist_line);
        wlSeekBar.setOnSeekBarChangeListener(mChangeListener);

        hlSeekBar = (SeekBar) findViewById(R.id.sb_hip_line);
        hipLineText = (TextView) findViewById(R.id.tv_hip_line);
        hlSeekBar.setOnSeekBarChangeListener(mChangeListener);

        shoulderSeekBar = (SeekBar) findViewById(R.id.sb_shoulder);
        shoulderText = (TextView) findViewById(R.id.tv_shoulder);
        shoulderSeekBar.setOnSeekBarChangeListener(mChangeListener);

        alSeekBar = (SeekBar) findViewById(R.id.sb_arm_length);
        armLengthText = (TextView) findViewById(R.id.tv_arm_length);
        alSeekBar.setOnSeekBarChangeListener(mChangeListener);

        llSeekBar = (SeekBar) findViewById(R.id.sb_leg_length);
        legLengthText = (TextView) findViewById(R.id.tv_leg_length);
        llSeekBar.setOnSeekBarChangeListener(mChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadBodyData();
//        startService(new Intent(this, BodyDataService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.saveBodyData();
//        startService(new Intent(this, BodyDataService.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!backing) {
                    SpannableString str = new SpannableString("精确的数据会有更惊喜的结果哦！"); //#bbdefb
                    ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#42a5f5"));
                    str.setSpan(span, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Snackbar.make(findViewById(R.id.rl_root_view), str, Snackbar.LENGTH_LONG).show();
                    startService(new Intent(this, BodyDataService.class));
                    new Handler().postDelayed(() -> onBackPressed(), 1500);
                    backing = true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private SeekBar.OnSeekBarChangeListener mChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.sb_gender:
                    genderText.setText(getResources().getStringArray(R.array.gender)[progress]);
                    break;
                case R.id.sb_age:
                    ageText.setText(progress+15+"岁");
                    break;
                case R.id.sb_face_shape:
                    faceShapeText.setText(getResources().getStringArray(R.array.face_shape)[progress]);
                    break;
                case R.id.sb_skin_color:
                    skinColorText.setText(getResources().getStringArray(R.array.skin_color)[progress]);
                    break;
                case R.id.sb_hair_color:
                    hairColorText.setText(getResources().getStringArray(R.array.hair_color)[progress]);
                    break;
                case R.id.sb_weight:
                    weightText.setText(progress+25+"kg");
                    break;
                case R.id.sb_body_length:
                    bodyLengthText.setText(progress+140+"cm");
                    break;
                case R.id.sb_chest:
                    chestText.setText(progress+70+"cm");
                    break;
                case R.id.sb_waist_line:
                    waistLineText.setText(progress+45+"cm");
                    break;
                case R.id.sb_hip_line:
                    hipLineText.setText(progress+60+"cm");
                    break;
                case R.id.sb_shoulder:
                    shoulderText.setText(progress+30+"cm");
                    break;
                case R.id.sb_arm_length:
                    armLengthText.setText(progress+40+"cm");
                    break;
                case R.id.sb_leg_length:
                    legLengthText.setText(progress+60+"cm");
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    public int getGender() {
        return genderSeekBar.getProgress();
    }

    @Override
    public int getAge() {
        return ageSeekBar.getProgress();
    }

    @Override
    public int getFaceShape() {
        return fsSeekBar.getProgress();
    }

    @Override
    public int getSkinColor() {
        return scSeekBar.getProgress();
    }

    @Override
    public int getHairColor() {
        return hcSeekBar.getProgress();
    }

    @Override
    public int getWeight() {
        return weightSeekBar.getProgress();
    }

    @Override
    public int getLength() {
        return blSeekBar.getProgress();
    }

    @Override
    public int getChest() {
        return chestSeekBar.getProgress();
    }

    @Override
    public int getWaistLine() {
        return wlSeekBar.getProgress();
    }

    @Override
    public int getHipLine() {
        return hlSeekBar.getProgress();
    }

    @Override
    public int getShoulder() {
        return shoulderSeekBar.getProgress();
    }

    @Override
    public int getArmLength() {
        return alSeekBar.getProgress();
    }

    @Override
    public int getLegLength() {
        return llSeekBar.getProgress();
    }

    @Override
    public void setGender(int gender) {
        genderSeekBar.setProgress(gender);
        genderText.setText(getResources().getStringArray(R.array.gender)[gender]);
    }

    @Override
    public void setAge(int age) {
        ageSeekBar.setProgress(age);
        ageText.setText(age+15+"岁");
    }

    @Override
    public void setFaceShape(int shape) {
        fsSeekBar.setProgress(shape);
        faceShapeText.setText(getResources().getStringArray(R.array.face_shape)[shape]);
    }

    @Override
    public void setSkinColor(int color) {
        scSeekBar.setProgress(color);
        skinColorText.setText(getResources().getStringArray(R.array.skin_color)[color]);
    }

    @Override
    public void setHairColor(int color) {
        hcSeekBar.setProgress(color);
        hairColorText.setText(getResources().getStringArray(R.array.hair_color)[color]);
    }

    @Override
    public void setWeight(int weight) {
        weightSeekBar.setProgress(weight);
        weightText.setText(weight + 25 + "kg");
    }

    @Override
    public void setLength(int length) {
        blSeekBar.setProgress(length);
        bodyLengthText.setText(length + 140 + "cm");
    }

    @Override
    public void setChest(int chest) {
        chestSeekBar.setProgress(chest);
        chestText.setText(chest + 70+"cm");
    }

    @Override
    public void setWaistLine(int waistLine) {
        wlSeekBar.setProgress(waistLine);
        waistLineText.setText(waistLine+45+"cm");
    }

    @Override
    public void setHipLine(int hipLine) {
        hlSeekBar.setProgress(hipLine);
        hipLineText.setText(hipLine+60+"cm");
    }

    @Override
    public void setShoulder(int shoulder) {
        shoulderSeekBar.setProgress(shoulder);
        shoulderText.setText(shoulder+30+"cm");
    }

    @Override
    public void setArmLength(int armLength) {
        alSeekBar.setProgress(armLength);
        armLengthText.setText(armLength+40+"cm");
    }

    @Override
    public void setLegLength(int legLength) {
        llSeekBar.setProgress(legLength);
        legLengthText.setText(legLength+60+"cm");
    }
}
