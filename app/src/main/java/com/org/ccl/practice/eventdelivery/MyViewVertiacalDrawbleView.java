package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ccl on 2017/5/18.
 */

public class MyViewVertiacalDrawbleView extends ViewGroup {
    public MyViewVertiacalDrawbleView(Context context) {
        this(context, null);
    }

    public MyViewVertiacalDrawbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewVertiacalDrawbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams contentViewLayoutParams = ((MarginLayoutParams) mContentView.getLayoutParams());
        MarginLayoutParams drawerViewLayoutParams = (MarginLayoutParams) mDrawerView.getLayoutParams();
        mContentView.layout(contentViewLayoutParams.leftMargin,contentViewLayoutParams.topMargin,contentViewLayoutParams.leftMargin+ mContentView.getMeasuredWidth(), contentViewLayoutParams.topMargin+mContentView.getMeasuredHeight());
        mDrawerView.layout(drawerViewLayoutParams.leftMargin,mCurTop+drawerViewLayoutParams.topMargin,drawerViewLayoutParams.leftMargin+ mDrawerView.getMeasuredWidth(), mCurTop+drawerViewLayoutParams.topMargin+mDrawerView.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mDrawerView = getChildAt(1);
        mDrawerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DrawerView", Toast.LENGTH_SHORT).show();
            }
        });
        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ContentView", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ViewDragHelper mTopViewDragHelper;

    private View mContentView;
    private View mDrawerView;

    private int mCurTop = 0;

    private boolean mIsOpen = true;

    private void init() {
//        Step1：使用静态方法构造ViewDragHelper,其中需要传入一个ViewDragHelper.Callback回调对象.
        mTopViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallBack());
        mTopViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
    }

    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDrawerView;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                mIsOpen = (mDrawerView.getTop() == 0);
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //mDrawerView完全挪出屏幕则防止过度绘制
//            mDrawerView.setVisibility((changedView.getHeight()+top == 0)? View.GONE : View.VISIBLE);
//            mCurTop = top;
//            requestLayout();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            float movePrecent = (releasedChild.getHeight() + releasedChild.getTop()) / (float) releasedChild.getHeight();
            int finalTop = (xvel >= 0 && movePrecent > 0.5f) ? 0 : -releasedChild.getHeight();
            mTopViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), finalTop);
            invalidate();
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
            mTopViewDragHelper.captureChildView(mDrawerView, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            if(child == null){
                return 0;
            }
            return child == mDrawerView ? mDrawerView.getWidth():0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if(child == null)
                return 0;
            return child == mDrawerView?mDrawerView.getHeight():0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return Math.max(Math.min(0,top),-mDrawerView.getHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mTopViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    //Step3：重写onTouchEvent回调ViewDragHelper中对应的方法.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTopViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(mTopViewDragHelper.continueSettling(true)){
            postInvalidate();
        }
    }
}
