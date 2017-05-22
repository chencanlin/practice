package com.org.ccl.practice.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.org.ccl.practice.R;

/**
 * Created by ccl on 2017/5/15.
 */

public class MyViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentView = ((FrameLayout) findViewById(android.R.id.content));
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_myviewactivity, contentView, false);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        contentView.addView(inflate,layoutParams);
//        setContentView(R.layout.activity_myviewactivity);
    }
}
