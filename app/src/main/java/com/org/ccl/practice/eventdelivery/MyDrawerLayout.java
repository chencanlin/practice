package com.org.ccl.practice.eventdelivery;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by ccl on 2017/5/19.
 */

public class MyDrawerLayout extends RelativeLayout implements View.OnTouchListener {
    private int mRightBUttonWidth;
    private View mContentView;
    private View mRightButtonOne;
    private View mRightButtonTwo;
    private ViewDragHelper mViewDragHelper;
    private int mCurrentLeft = 0;
    private boolean isOpen = false;
    private MySliderListener mListener;
    private int mPosition;

    public MyDrawerLayout(Context context) {
        this(context, null);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(this);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        int screenWidth = defaultDisplay.getWidth();
        int screenHeight = defaultDisplay.getHeight();
        int percent = screenWidth / 9;
        int leftContentViewWidth = percent * 5;
        mRightBUttonWidth = percent * 2;

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new MyCallBack());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        needCloseOtherItem();
        return false;
    }

    private class MyCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        MyCallBack() {
            super();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                isOpen = mContentView.getLeft() != 0;
                if (mListener != null) {
                    mListener.isOpenAnother(mPosition, isOpen);
                }
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (left >= 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                if (Math.abs(left) < 50) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else
                    getParent().requestDisallowInterceptTouchEvent(true);
            }
            mCurrentLeft = left;
            requestLayout();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mContentView) {
                int left = Math.abs(mContentView.getLeft()) > mRightBUttonWidth ? -mRightBUttonWidth * 2 : 0;
                mViewDragHelper.settleCapturedViewAt(left, 0);
                postInvalidate();
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
            mViewDragHelper.captureChildView(mContentView, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (needCloseOtherItem()) return 0;
            if (child == mContentView) {
                if (left >= -mRightBUttonWidth * 2 && left <= 0) {
                    return left;
                } else if (left < -mRightBUttonWidth * 2) {
                    return (-mRightBUttonWidth * 2);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
    }

    private boolean needCloseOtherItem() {
        return mListener != null && mListener.needCloseOther();
    }

    public void closeItem() {
        if (isOpen) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(mContentView.getLeft(), 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentLeft = (Integer) animation.getAnimatedValue();
                    requestLayout();
                }
            });
            valueAnimator.setDuration(200);
            valueAnimator.start();
            mListener.isOpenAnother(mPosition, false);
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
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mRightButtonOne = getChildAt(1);
        mRightButtonTwo = getChildAt(2);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LayoutParams rightOneLayoutParams = (LayoutParams) mRightButtonOne.getLayoutParams();
        rightOneLayoutParams.width = mRightBUttonWidth;
        mRightButtonOne.setLayoutParams(rightOneLayoutParams);
        ViewGroup.LayoutParams rightTwoLayoutParams = mRightButtonTwo.getLayoutParams();
        rightTwoLayoutParams.width = mRightBUttonWidth;
        mRightButtonTwo.setLayoutParams(rightTwoLayoutParams);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mContentView.layout(mCurrentLeft, 0, mCurrentLeft + mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        mRightButtonOne.layout(mCurrentLeft + mContentView.getMeasuredWidth(), 0, mCurrentLeft + mContentView.getMeasuredWidth() + mRightButtonOne.getMeasuredWidth(), mRightButtonOne.getMeasuredHeight());
        mRightButtonTwo.layout(mCurrentLeft + mContentView.getMeasuredWidth() + mRightButtonOne.getMeasuredWidth(), 0, mCurrentLeft + mContentView.getMeasuredWidth() + mRightButtonOne.getMeasuredWidth() + mRightButtonTwo.getMeasuredWidth(), mRightButtonTwo.getMeasuredHeight());
    }

    public void setOnSliderListener(int position, MySliderListener listener) {
        mPosition = position;
        mListener = listener;
    }
}
