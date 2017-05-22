package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by ccl on 2017/5/18.
 */

public class MyViewDragerPractice extends LinearLayout {
    private Point mPoint = new Point();
    private View mViewOne;
    private View mViewTwo;
    private View mViewThree;
    private ViewDragHelper mViewDragHelper;
    private WindowManager mWindowManager;

    public MyViewDragerPractice(Context context) {
        this(context, null);
    }

    public MyViewDragerPractice(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewDragerPractice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDragerHelperCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    private class MyViewDragerHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mViewOne || child == mViewTwo;
        }

        public MyViewDragerHelperCallback() {
            super();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(releasedChild == mViewTwo){
                mViewDragHelper.settleCapturedViewAt(mPoint.x,mPoint.y);
                postInvalidate();
            }else {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mViewDragHelper.captureChildView(mViewThree, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return child.getWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return child.getHeight();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left >= 0 && left <= getWidth() - child.getWidth()) {
                return left;
            } else if (left < 0) {
                return 0;
            } else {
                return getWidth() - child.getWidth();
            }
//            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top >= 0 && top <= getHeight() - child.getHeight()) {
                return top;
            } else if (top < 0) {
                return 0;
            } else {
                return getHeight() - child.getHeight();
            }
//            return top;
        }
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPoint.x = mViewTwo.getLeft();
        mPoint.y = mViewTwo.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewOne = getChildAt(0);
        mViewTwo = getChildAt(1);
        mViewThree = getChildAt(2);
    }
}
