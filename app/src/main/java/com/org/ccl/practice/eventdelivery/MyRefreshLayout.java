package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.org.ccl.practice.R;

/**
 * Created by ccl on 2017/7/11.
 */

public class MyRefreshLayout extends ViewGroup {
    private static final int DP_VIEW_FLIPPER_WIDTH = 65;
    private static final int DP_VIEW_FLIPPER_HEIGHT = 66;
    private static final int DP_VIEW_FOOTER_IMAGE_WIDTH = 188;
    private static final int DP_VIEW_FOOTER_IMAGE_HEIGHT = 33;
    private static final int REFRESHING = 0;
    private static final int COMPLETED = 1;

    private View mHeader;
    private View mFooter;
    private View mPullableView;

    private boolean isInit = false;
    private ViewDragHelper mViewDragHelper;

    private int mTop;
    private View mPullUpView;
    private View mViewFlipper;
    private int mHeaderMeasuredHeight;
    private int mFooterMeasuredHeight;

    private Handler mHandler;
    private MyRunnble mMyRunnble;

    private int mRefreshStatus = COMPLETED;
    private View hideTitle;
    private OnRefreshListener mListener;

    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.layout_scrollview_head, this, false);
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.layout_scrollview_bottom, this, false);
        mViewFlipper = mHeader.findViewById(R.id.loading_view_flipper);
        mPullUpView = mFooter.findViewById(R.id.pullup_icon);
        addView(mHeader);
        addView(mFooter);
        MyViewDragHelperCallBack myViewDragHelperCallBack = new MyViewDragHelperCallBack();
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, myViewDragHelperCallBack);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isInit) {
            mHeaderMeasuredHeight = mHeader.getMeasuredHeight();
            mFooterMeasuredHeight = mFooter.getMeasuredHeight();
            mHeader.layout(0, -mHeaderMeasuredHeight, getMeasuredWidth(), 0);
            mFooter.layout(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight() + mFooterMeasuredHeight);
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child instanceof Pullable) {
                    mPullableView = child;
                    mPullableView.layout(0, 0, mPullableView.getMeasuredWidth(), mPullableView.getMeasuredHeight());
                    isInit = true;
                    return;
                }
            }
        } else {
            mTop = mPullableView.getTop();
            mHeader.layout(0, -mHeaderMeasuredHeight + mPullableView.getTop(), getMeasuredWidth(), mHeaderMeasuredHeight + mPullableView.getTop() + mHeaderMeasuredHeight);
            mFooter.layout(0, getMeasuredHeight() + mPullableView.getTop(), getMeasuredWidth(), getMeasuredHeight() + mPullableView.getTop() + mFooterMeasuredHeight);
            float mFooterScaleX = DP_VIEW_FOOTER_IMAGE_WIDTH * Math.abs((float) mTop / (float) mFooterMeasuredHeight);
            float mFooterScaleY = DP_VIEW_FOOTER_IMAGE_HEIGHT * Math.abs((float) mTop / (float) mFooterMeasuredHeight);
            mPullUpView.setScaleX(mFooterScaleX);
            mPullUpView.setScaleY(mFooterScaleY);
            mPullUpView.setTranslationY(Math.abs(mFooterScaleY + mTop) / 2);
            mPullUpView.setAlpha(mFooterScaleY / 33);

            float tempTop = (float) (mTop > mHeaderMeasuredHeight ? mHeaderMeasuredHeight : mTop);
            float mHeaderScaleX = DP_VIEW_FLIPPER_WIDTH * Math.abs( tempTop/ (float) mHeaderMeasuredHeight);
            float mHeaderScaleY = DP_VIEW_FLIPPER_HEIGHT * Math.abs(tempTop / (float) mHeaderMeasuredHeight);
            mViewFlipper.setScaleX(mHeaderScaleX);
            mViewFlipper.setScaleY(mHeaderScaleY);
            mViewFlipper.setTranslationY(Math.abs(mHeaderMeasuredHeight - tempTop / 2));
            mViewFlipper.setAlpha(mHeaderScaleY / DP_VIEW_FLIPPER_HEIGHT);
            if (hideTitle != null) {
                hideTitle.setVisibility(mTop == 0 ? View.VISIBLE : View.GONE);
            }
        }
    }

    private class MyViewDragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == mPullableView;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (mTop < mHeader.getMeasuredHeight() / 2) {
                mViewDragHelper.settleCapturedViewAt(0, 0);
            } else if (mTop >= mHeader.getMeasuredHeight() / 2) {
                mViewDragHelper.settleCapturedViewAt(0, mHeader.getMeasuredHeight());
            }
            invalidate();
            requestLayout();
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
            mViewDragHelper.captureChildView(mPullableView, pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (child == null)
                return 0;
            return child == mPullableView ? mPullableView.getHeight() : 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if ((top > 0 && ((Pullable) mPullableView).canPullDown()) || (top <= 0 && ((Pullable) mPullableView).canPullUp())) {
                if (top >= -mFooter.getMeasuredHeight() && top <= mHeader.getMeasuredHeight() * 2) {
                    mTop = top;
                } else if (top < -mFooter.getMeasuredHeight()) {
                    mTop = -mFooter.getMeasuredHeight();
                } else {
                    mTop = mHeader.getMeasuredHeight() * 2;
                }
                requestLayout();
                return mTop;
            } else {
                return super.clampViewPositionVertical(child, top, dy);
            }
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
        if (mViewDragHelper.continueSettling(true)) {
            requestLayout();
            invalidate();
        }else{
            if(mRefreshStatus == COMPLETED){
                clear();
            }
        }
    }
    /**
     * 刷新加载回调接口
     */
    public interface OnRefreshListener {
        /**
         * 刷新操作
         */
        void onRefresh(MyRefreshLayout refreshLayout);
    }

    public void setHideTitle(View view) {
        hideTitle = view;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public void setRefreshStatus(int status){
        if(status == COMPLETED && mTop > 0 ){
            initHandler();
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(mMyRunnble, 800);
        }
    }

    private void initHandler() {
        if(mHandler == null){
            mHandler = new Handler();
        }
        if(mMyRunnble == null){
            mMyRunnble = new MyRunnble();
        }
    }

    private class MyRunnble implements Runnable{

        @Override
        public void run() {
            mViewDragHelper.settleCapturedViewAt(0, 0);
            invalidate();
            requestLayout();
        }
    }

    public void clear(){
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if(mMyRunnble != null){
            mMyRunnble = null;
        }
    }
}
