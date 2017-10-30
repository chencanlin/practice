package com.org.ccl.practicetwo.nestedscrolling.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/8/30.
 */

public class NestedScrollingPracticeActivity extends FragmentActivity {

    private ViewPagerIndicator mViewPagerIndicator;
    private ViewPager mViewPager;
    private SparseArrayCompat<Fragment> mFragments = new SparseArrayCompat<>();
    private MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling_practice);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        mFragments.put(0, TabFragment.newInstance(getString(R.string.tab_fragment_qa)));
        mFragments.put(1, TabFragment.newInstance(getString(R.string.tab_fragment_circle)));
        mFragments.put(2, TabFragment.newInstance(getString(R.string.tab_fragment_information)));
    }

    private int getScreenWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private void initView() {
        int imgWidth = 720;
        int imgHeight = 400;
        int screenWidth = getScreenWidth();
        int topViewHeight = screenWidth * imgHeight / imgWidth;
        View topView = findViewById(R.id.id_nested_scrolling_top_view);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, topViewHeight);
        topView.setLayoutParams(layoutParams);

        mViewPagerIndicator = findViewById(R.id.vpi);
        mViewPager = findViewById(R.id.vp);
        mMyViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mMyViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPagerIndicator.setViewPager(mViewPager);
        mViewPager.setPageMargin(60);
        mViewPager.setPageTransformer(false, new MyScaleTransformer());
    }

    private class MyRotateTransformer implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {
            if(position<-1){
                page.setRotation(0);
            }else if(position <=1){
                if(position<0){
                    page.setPivotX(page.getMeasuredWidth()/2);
                    page.setPivotY(page.getMeasuredHeight());
                    page.setRotation(20 * position);
                }else{
                    page.setPivotX(page.getMeasuredWidth()/2);
                    page.setPivotY(page.getMeasuredHeight());
                    page.setRotation(20 * position);
                }
            }else{
                page.setRotation(0);
            }
        }
    }

    private class MyScaleTransformer implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {
            if(position < -1){
                page.setScaleX(0.8f);
                page.setScaleY(0.8f);
            }else if(position <= 1){
                if(position < 0){
                    page.setPivotX(page.getMeasuredWidth()/2);
                    page.setPivotY(page.getMeasuredHeight()/2- TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 39, getResources().getDisplayMetrics()));
                    page.setScaleX((float) (1-Math.abs(position)*0.5));
                    page.setScaleY((float) (1-Math.abs(position)*0.5));
                }else{
                    page.setPivotX(page.getMeasuredWidth()/2);
                    page.setPivotY(page.getMeasuredHeight()/2-TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 39, getResources().getDisplayMetrics()));
                    page.setScaleX((float) (1-Math.abs(position)*0.5));
                    page.setScaleY((float) (1-Math.abs(position)*0.5));
                }
            }else{
                page.setScaleX(0.8f);
                page.setScaleY(0.8f);
            }
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter{

        private SparseArrayCompat<Fragment> mFragments;

        MyViewPagerAdapter(FragmentManager fm, SparseArrayCompat<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(mFragments != null){
                fragment = mFragments.get(position);
            }else{
                mFragments = new SparseArrayCompat<>();
            }
            if(fragment == null){
                switch (position){
                    case 0:
                        fragment = TabFragment.newInstance(getString(R.string.tab_fragment_qa));
                        break;
                    case 1:
                        fragment = TabFragment.newInstance(getString(R.string.tab_fragment_circle));
                        break;
                    case 2:
                        fragment = TabFragment.newInstance(getString(R.string.tab_fragment_information));
                        break;
                }
            }
            mFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((TabFragment) mFragments.get(position)).getPagerTitle();
        }
    }
}
