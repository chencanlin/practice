package com.org.ccl.practicetwo.colorgradient;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.org.ccl.practicetwo.R;

import java.util.List;
import java.util.Random;

/**
 * Created by ccl on 2017/6/14.
 */

public class ImageAutoRollLayout extends FrameLayout {
    private static final String TAG = "ImageAutoRollLayout";
    private static final int DEFAULT_DOT_RADIU = 18;
    private static final int DEFAULT_DOT_MARGIN = 18;
    private static final int DELAY_TIME = 3000;

    private ViewPager mVp;
    private LinearLayout mLlContainer;
    private OnItemClickListener mItemClickListener;

    private SparseArrayCompat<ImageAutoRollItem> mData = new SparseArrayCompat<>();
    private SparseArrayCompat<ImageView> mImageView = new SparseArrayCompat<>();
    private boolean isAutoRoll = true;
    private int mType;
    private MyPagerAdapter mMyPagerAdapter;
    private int mDotRadiu;
    private int mDotMargin;
    private DisplayImageOptions mOptions;
    private Handler mHandler;
    private MyRunnable mMyRunnable;
    private MyPagerChangeListener mMyPagerChangeListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private float mDx;
    private float mDy;
    private ColorCustomView mColorCustomView;

    public ImageAutoRollLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImageAutoRollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageAutoRollLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageAutoRollLayout);
        mDotRadiu = typedArray.getDimensionPixelSize(R.styleable.ImageAutoRollLayout_dot_radiu, DEFAULT_DOT_RADIU);
        mDotMargin = typedArray.getDimensionPixelOffset(R.styleable.ImageAutoRollLayout_dot_margin, DEFAULT_DOT_MARGIN);
        mType = typedArray.getInt(R.styleable.ImageAutoRollLayout_layout_type, MyType.DEFAULT);
        typedArray.recycle();
        init();
    }

    private void init() {
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        mHandler = new Handler();
        mMyRunnable = new MyRunnable();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_image_autoroll, this, true);
        mVp = ((ViewPager) findViewById(R.id.vp));
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        mMyPagerChangeListener = new MyPagerChangeListener();
        mVp.addOnPageChangeListener(mMyPagerChangeListener);
        mMyPagerAdapter = new MyPagerAdapter();
        mVp.setAdapter(mMyPagerAdapter);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setData(List<? extends ImageAutoRollItem> data) {
        setData(data, 0);
    }

    public void setData(List<? extends ImageAutoRollItem> data, int defaultImgId) {
        mHandler.removeCallbacks(mMyRunnable);
        if (defaultImgId != 0) {
            mVp.setBackgroundResource(defaultImgId);
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(defaultImgId)
                    .showImageOnFail(defaultImgId)
                    .showImageForEmptyUri(defaultImgId)
                    .cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        mData.clear();
        mImageView.clear();
        mLlContainer.removeAllViews();
        mMyPagerAdapter.notifyDataSetChanged();
        if (data != null && data.size() != 0) {
            LinearLayout.LayoutParams dotLayoutParams = new LinearLayout.LayoutParams(mDotRadiu, mDotRadiu);
            dotLayoutParams.rightMargin = mDotMargin;
            for (int i = 0; i < (data.size() == 2 ? data.size() * 2 : data.size()); i++) {
                int position = i % data.size();
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoader.getInstance().displayImage(data.get(position).getImageUrl(), imageView, mOptions);
                mImageView.put(i, imageView);
                View view = new View(getContext());
                view.setBackgroundResource(R.drawable.dot);
                if ((data.size() == 2 && i < 2) || data.size() > 2) {
                    mLlContainer.addView(view, dotLayoutParams);
                }
                mData.put(position, data.get(position));
                mVp.setAdapter(mMyPagerAdapter);
            }

            // 设置当前索引位置为Integer的中间值，在实际项目中会有anr
            /*int i = Integer.MAX_VALUE / 2;
            int i1 = innerPosition(i);
            if (i1 != 0) {
                i = i+mData.size()-i1;
            }*/
            if(mData.size() > 1) {
                mVp.setCurrentItem(100 * data.size());
                mMyPagerChangeListener.onPageSelected(mVp.getCurrentItem());
                mHandler.postDelayed(mMyRunnable, DELAY_TIME);
            }else{
                mVp.setCurrentItem(0);
            }
        }
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    public void setAutoRoll(boolean flag) {
        if(isAutoRoll == flag){
            return;
        }
        isAutoRoll = flag;
        clearRunnable();
        if(flag){
            startRunnable();
        }

    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            if (mImageView == null || mImageView.size() == 0) {
                return;
            }
            if (!isAutoRoll) {
                return;
            }

            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
            mHandler.postDelayed(mMyRunnable, DELAY_TIME);
        }
    }

    private void clearRunnable(){
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        if(mMyRunnable != null){
            mMyRunnable = null;
        }
    }

    private void startRunnable(){
        if(mHandler == null){
            mHandler = new Handler();
        }
        if(mMyRunnable == null ){
            mMyRunnable = new MyRunnable();
        }
        mHandler.postDelayed(mMyRunnable,DELAY_TIME);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = ev.getX();
                mDy = ev.getY();
                isAutoRoll = false;
                mHandler.removeCallbacks(mMyRunnable);
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mSwipeRefreshLayout != null) {
                    float mMoveX = mDx - ev.getX();
                    float mMoveY = mDy - ev.getY();
                    if (Math.abs(mMoveX) > (Math.abs(mMoveY) / 2)) {
                        mSwipeRefreshLayout.setEnabled(false);
                    } else {
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mItemClickListener != null && Math.abs(ev.getX()-mDx) <10 && Math.abs(ev.getY()-mDy) <10){
                    int i = innerPosition(mVp.getCurrentItem());
                    if(i < mData.size()) {
                        mItemClickListener.onItemClick(i);
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                isAutoRoll = true;
                mHandler.postDelayed(mMyRunnable, DELAY_TIME);
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mData == null || mData.size() == 0 ? 0 : mData.size() == 1? mData.size() :Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = null;
            if(mData != null && mData.size() != 0) {
                int i = mData.size() == 2 ? position % (mData.size()*2):innerPosition(position);
                imageView = mImageView.get(i);
                if (imageView != null) {
                    if (imageView.getParent() != null) {
                        ((ViewGroup) imageView.getParent()).removeView(imageView);
                    }
                }
                container.addView(imageView);
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    private int innerPosition(int position) {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        return position % mData.size();
    }

    public void setView(ColorCustomView colorCustomView){
        mColorCustomView = colorCustomView;
    }

    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.e(TAG, "onPageScrolled: positionoffset---" + positionOffset + "positionoffsetpixels" +positionOffsetPixels );
//            mColorCustomView.scrollBy(positionOffsetPixels, 0);
        }

        @Override
        public void onPageSelected(int position) {
            if(mData != null && mData.size()>1) {
                int i = innerPosition(position);
                for (int j = 0; j < mData.size(); j++) {
                    mLlContainer.getChildAt(j).setEnabled(i != j);
                }
                Random random = new Random();
                mColorCustomView.setColors(new int[]{Color.argb
                        (255,random.nextInt(256),random.nextInt(256)
                        ,random.nextInt(256)),Color.argb
                        (255,random.nextInt(256),random.nextInt(256)
                                ,random.nextInt(256))});
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public interface ImageAutoRollItem {
        String getImageUrl();

        String getTitle();
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    interface MyType {
        int DEFAULT = 0;
    }

    public void clearCache() {
        mVp.removeOnPageChangeListener(mMyPagerChangeListener);
        mMyPagerChangeListener = null;
        mVp.removeAllViews();
        mVp = null;
        mHandler.removeCallbacksAndMessages(null);
        mMyRunnable = null;
        mHandler = null;
        mImageView.clear();
        mData.clear();
//        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
