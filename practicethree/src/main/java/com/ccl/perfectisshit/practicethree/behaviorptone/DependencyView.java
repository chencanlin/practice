package com.ccl.perfectisshit.practicethree.behaviorptone;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by ccl on 2018/1/12.
 */


public class DependencyView extends TextView {

    private float mStartX;
    private float mStartY;

    public DependencyView(Context context) {
        super(context);
    }

    public DependencyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DependencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getRawX();
                float endY = event.getRawY();
                float moveX = endX - mStartX;
                float moveY = endY - mStartY;
                layoutParams.topMargin = (int) (layoutParams.topMargin + moveY);
                layoutParams.leftMargin = (int) (layoutParams.leftMargin + moveX);
                setLayoutParams(layoutParams);
                requestLayout();
                mStartX = endX;
                mStartY = endY;

//                setTranslationX(endX - mStartX);
//                setTranslationY(endY - mStartY);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
