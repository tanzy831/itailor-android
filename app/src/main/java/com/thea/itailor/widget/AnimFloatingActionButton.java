package com.thea.itailor.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.thea.itailor.R;

/**
 * Created by Thea on 2015/7/27.
 */
public class AnimFloatingActionButton extends FloatingActionButton {
    private boolean isHide = true;
    private int hideAnimId;
    private int showAnimId;

    private float previousY = -1f;

    public AnimFloatingActionButton(Context context) {
        this(context, null);
    }

    public AnimFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        hideAnimId = R.anim.slide_out_bottom;
        showAnimId = R.anim.slide_in_bottom;
    }

    public void setAnimIds(int hideAnimId, int showAnimId) {
        this.hideAnimId = hideAnimId;
        this.showAnimId = showAnimId;
    }

    public boolean isShowing() {
        return !isHide;
    }

    public void hide() {
        if (!isHide) {
            Log.i("fab", "hide");
            startAnimation(AnimationUtils.loadAnimation(getContext(), hideAnimId));
            isHide = true;
        }
    }

    public void show() {
        if (isHide) {
            startAnimation(AnimationUtils.loadAnimation(getContext(), showAnimId));
            isHide = false;
        }
    }

    public void attachToView(View view) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (previousY < 0)
                        previousY = event.getY();

                    if (event.getY() - previousY > 30)
                        hide();
                    else if (previousY - event.getY() > 30)
                        show();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    previousY = -1f;
                }

                return v.onTouchEvent(event);
            }
        });
    }
}
