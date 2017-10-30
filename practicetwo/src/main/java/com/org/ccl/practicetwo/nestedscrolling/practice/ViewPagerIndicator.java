package com.org.ccl.practicetwo.nestedscrolling.practice;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ccl on 2017/8/30.
 */

public class ViewPagerIndicator extends LinearLayout {

    private int mScreenWidth;
    private ViewPager mViewPager;
    private static final int[] colors = {0xFFFF7F27, 0xFF3E07D2, 0xFFB61C51};

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        mScreenWidth = getScreenWidth();
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
//        mViewPager.addOnPageChangeListener();
        if (mViewPager != null) {
            int count = mViewPager.getAdapter().getCount();
            if (count != 0) {
                generateTitleView(count);
            }
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void generateTitleView(int count) {
        int tabWidth = mScreenWidth/count;
        for (int i = 0; i < count; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(tabWidth, LayoutParams.MATCH_PARENT);
            linearLayout.setId(i);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            int argb = Color.argb(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            linearLayout.setBackgroundColor(argb);
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(view.getId());
                }
            });
            TextView textView = new TextView(getContext());
            textView.setText("测试");
            textView.setTextSize(13);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            LayoutParams textViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(textViewParams);
            TextView bottomLine = new TextView(getContext());
            LayoutParams bottomLineParams = new LayoutParams((int) (mScreenWidth / count - dpToPixel(20)), 1);
            bottomLineParams.gravity = Gravity.CENTER_HORIZONTAL;
            bottomLine.setBackgroundColor(0xffFF7F27);
            bottomLine.setLayoutParams(bottomLineParams);
            linearLayout.addView(textView);
            linearLayout.addView(bottomLine);
            addView(linearLayout);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMine = MeasureSpec.makeMeasureSpec(mScreenWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpecMine = MeasureSpec.makeMeasureSpec((int) dpToPixel(39), MeasureSpec.EXACTLY);
        measureChildren(widthMeasureSpecMine, heightMeasureSpecMine);
        setMeasuredDimension(mScreenWidth, (int) dpToPixel(39));
    }

    public int getScreenWidth() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public float dpToPixel(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
