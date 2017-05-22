package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ccl on 2017/5/17.
 */

public class MyRelativeLayout extends RelativeLayout implements View.OnTouchListener {
    public MyRelativeLayout(Context context) {
        this(context, null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyRelativeLayout------------------------dispatchTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyRelativeLayout------------------------dispatchTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyRelativeLayout------------------------dispatchTouchEvent-------------Up");
                break;
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyRelativeLayout------------------------onInterceptTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyRelativeLayout------------------------onInterceptTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyRelativeLayout------------------------onInterceptTouchEvent-------------Up");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyRelativeLayout------------------------onTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyRelativeLayout------------------------onTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyRelativeLayout------------------------onTouchEvent-------------Up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyRelativeLayout------------------------onTouch-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyRelativeLayout------------------------onTouch-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyRelativeLayout------------------------onTouch-------------Up");
                break;
        }
        return false;
    }
}
