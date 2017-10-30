package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by ccl on 2017/5/17.
 */

public class MyTextView extends TextView {
    private Scroller mScroller;
    private float mStartY;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public void smoothScrollTo(int dX, int dY) {
        int x = dX - mScroller.getFinalX();
        int y = dY - mScroller.getFinalY();
        smoothScrollBy(x, y);
    }

    public void smoothScrollBy(int dX, int dY) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dX, dY, 0);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getY();
                smoothScrollBy(0, -((int) (endY - mStartY)));
                mStartY = endY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
