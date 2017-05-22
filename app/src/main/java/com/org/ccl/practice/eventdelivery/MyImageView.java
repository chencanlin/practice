package com.org.ccl.practice.eventdelivery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ccl on 2017/5/17.
 */

public class MyImageView extends ImageView implements View.OnTouchListener {
    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
//        setClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyImageView------------------------onTouch-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyImageView------------------------onTouch-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyImageView------------------------onTouch-------------Up");
                break;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyImageView------------------------dispatchTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyImageView------------------------dispatchTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyImageView------------------------dispatchTouchEvent-------------Up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("MyImageView------------------------OnTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MyImageView------------------------OnTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MyImageView------------------------OnTouchEvent-------------Up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
