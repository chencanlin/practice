package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by ccl on 2017/5/17.
 */

public class MyTextView extends TextView implements View.OnTouchListener {
    private Scroller mScroller;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        mScroller = new Scroller(context);
    }

    public void  smoothScrollTo(int dX, int dY){
        int x = dX - mScroller.getFinalX();
        int y = dY - mScroller.getFinalY();
        smoothScrollBy(x,y);
    }

    public void smoothScrollBy(int dX, int dY){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dX, dY,3000);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyTextView------------------------onTouch-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyTextView------------------------onTouch-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyTextView------------------------onTouch-------------Up");
                break;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyTextView------------------------dispatchTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyTextView------------------------dispatchTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyTextView------------------------dispatchTouchEvent-------------Up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyTextView------------------------OnTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyTextView------------------------OnTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyTextView------------------------OnTouchEvent-------------Up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
