package com.org.ccl.practicetwo.behavior;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/21.
 */

public class BehaviorTestActivity extends Activity {

    private View mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_test);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mBtn = findViewById(R.id.btn);
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX(motionEvent.getRawX()-view.getWidth()/2);
                        view.setY(motionEvent.getRawY()-view.getHeight()/2);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
    }
}
