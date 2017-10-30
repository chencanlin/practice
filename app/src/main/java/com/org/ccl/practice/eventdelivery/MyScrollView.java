package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.org.ccl.practice.R;


/**
 * Created by ccl on 2016/8/19.
 */
public class MyScrollView extends ScrollView implements Pullable{
    private View view;
    private ScrollViewListener listener;
    private boolean isOver = true;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChangeView(View view){
        this.view = view;
        changeViewBg();
    }
    public void setOnScrollChangeListener(ScrollViewListener listener){
        this.listener = listener;
    }
    public void isAnimationFinished(boolean isOver){
        this.isOver = isOver;
        changeViewBg();
    }
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(listener!=null){
            listener.ScrollChangeScrollView(this,scrollX,scrollY,clampedX,clampedY);
        }
        // 车辆信息页滑动scrollview会开始一个高度缩放动画，为了兼容)
        if (view != null) {
            if (!isOver) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeViewBg();
                        isOver = true;
                    }
                }, 250);
            } else {
                changeViewBg();
            }
        }
    }
    public void changeViewBg(){
        final View child = getChildAt(0);
        post(new Runnable() {
            @Override
            public void run() {
                if(child.getMeasuredHeight()-(getHeight()+getScrollY())<=0){
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                }else{
                    int argb = Color.argb(178, 255, 255, 255);
                    view.setBackgroundColor(argb);
                }
            }
        });
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }
}
