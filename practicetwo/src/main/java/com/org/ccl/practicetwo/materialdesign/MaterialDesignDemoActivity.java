package com.org.ccl.practicetwo.materialdesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;

import static com.org.ccl.practicetwo.camera.CameraActivity.TAG;

/**
 * Created by ccl on 2017/9/13.
 */

public class MaterialDesignDemoActivity extends Activity {

    private static final String TAG = MaterialDesignDemoActivity.class.getName();

    private Button mBtn;
    private TextView mTvGone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mBtn = findViewById(R.id.btn);
        mTvGone = findViewById(R.id.tv_gone);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            TypedValue typedValue = new TypedValue();
            boolean b = getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int resourceId = typedValue.resourceId;
//            mBtn.setBackgroundResource(resourceId);
            mBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light,getTheme()));
        }else{
            mBtn.setBackground(null);
        }

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int left = mTvGone.getLeft();
                int right = mTvGone.getRight();
                int top = mTvGone.getTop();
                int bottom = mTvGone.getBottom();
                int centerX = (left + right)/2;
                int centerY = (top + bottom)/2;
                int radius = Math.max(mTvGone.getWidth(), mTvGone.getHeight());
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(mTvGone, centerX, centerY, 0, radius);
                mTvGone.setVisibility(View.VISIBLE);
                circularReveal.setDuration(3000);
                circularReveal.start();
            }
        });

        mTvGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int left = mBtn.getLeft();
                int right = mBtn.getRight();
                int top = mBtn.getTop();
                int bottom = mBtn.getBottom();
                int centerX = (left + right)/2;
                int centerY = (top + bottom)/2;
                int initialWidth = mBtn.getWidth();
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(mBtn, centerX, centerY, initialWidth, 0);
                circularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mBtn.setVisibility(View.INVISIBLE);
                    }
                });
                circularReveal.setDuration(3000);
                circularReveal.start();

                Intent intent = new Intent(MaterialDesignDemoActivity.this, MaterialDesignSecondActivity.class);
                ActivityOptions second = ActivityOptions.makeSceneTransitionAnimation(MaterialDesignDemoActivity.this, mTvGone, "second");
                startActivity(intent,second.toBundle());
            }
        });
        DisplayManager systemService = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = systemService.getDisplays();
        for (Display item :displays) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            item.getMetrics(displayMetrics);
            DisplayMetrics displayMetrics1 = new DisplayMetrics();
            item.getRealMetrics(displayMetrics1);
            Log.i(TAG, displayMetrics.widthPixels + "-------"
            +displayMetrics.heightPixels + "--------"+displayMetrics1.widthPixels + "------"
            +displayMetrics1.heightPixels);
        }
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        Log.i(TAG, rect.left + " ---" + rect.top + "----"
        +rect.right+"-----"+rect.bottom);
    }
}
