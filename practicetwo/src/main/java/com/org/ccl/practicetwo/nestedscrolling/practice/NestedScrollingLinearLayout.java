package com.org.ccl.practicetwo.nestedscrolling.practice;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/8/30.
 */

public class NestedScrollingLinearLayout extends LinearLayout implements NestedScrollingParent{

    private View mTopView;
    private int mTopViewMeasureHeight;


    public NestedScrollingLinearLayout(Context context) {
        this(context, null);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_nested_scrolling_top_view);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewMeasureHeight = mTopView.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + getChildAt(0).getMeasuredHeight() + getChildAt(1).getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    @Override
    public void onStopNestedScroll(View child) {
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int scrollY = getScrollY();
        int consumedY = dy;
        if(dy >0){
            if(scrollY + consumedY >= mTopViewMeasureHeight){
                consumedY = mTopViewMeasureHeight-scrollY;
            }
        }else{

            if(!ViewCompat.canScrollVertically(target, -1)) {
                if (scrollY + consumedY <= 0) {
                    consumedY = -scrollY;
                }
            }else{
                consumedY = 0;
            }
        }

        scrollBy(0 , consumedY);
        consumed[1] = consumedY;

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }
}
