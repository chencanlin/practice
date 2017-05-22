package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ccl on 2017/5/17.
 */

public class MyLinearlayout extends LinearLayout implements View.OnTouchListener {
    public MyLinearlayout(Context context) {
        this(context, null);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyLinearlayout------------------------dispatchTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyLinearlayout------------------------dispatchTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyLinearlayout------------------------dispatchTouchEvent-------------Up");
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyLinearlayout------------------------onInterceptTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyLinearlayout------------------------onInterceptTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyLinearlayout------------------------onInterceptTouchEvent-------------Up");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyLinearlayout------------------------onTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyLinearlayout------------------------onTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyLinearlayout------------------------onTouchEvent-------------Up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyLinearlayout------------------------onTouch-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyLinearlayout------------------------onTouch-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyLinearlayout------------------------onTouch-------------Up");
                break;
        }
        return false;
    }
}
