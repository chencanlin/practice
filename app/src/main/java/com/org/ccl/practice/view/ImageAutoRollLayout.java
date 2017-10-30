package com.org.ccl.practice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.org.ccl.practice.R;

import java.util.List;

/**
 * Created by ccl on 2017/6/14.
 */

public class ImageAutoRollLayout extends FrameLayout {
    private static final int DEFAULT_DOT_RADIU = 18;
    private static final int DEFAULT_DOT_MARGIN = 18;

    private DisplayImageOptions mOptions;
    private int defaultImgId;
    private ViewPager mVp;
    private LinearLayout mLlContainer;
    private GestureDetector mGestureDetector;
    private OnItemClickListener mItemClickListener;

    private SparseArrayCompat<IShowItem> mData = new SparseArrayCompat<>();
    private SparseArrayCompat<ImageView> mImageView = new SparseArrayCompat<>();
    private boolean isAutoRoll = true;
    private int mType;
    private MyPagerAdapter mMyPagerAdapter;
    private int mDotRadiu;
    private int mDotMargin;

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
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());
        ImageLoader.getInstance().init(configuration);
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_image_autoroll, this, true);
        mVp = ((ViewPager) findViewById(R.id.vp));
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        MyGestureDetectorListener mMyGestureDetectorListener = new MyGestureDetectorListener();
        mGestureDetector = new GestureDetector(mMyGestureDetectorListener);
        MyViewPagerTouchListener myViewPagerTouchListener = new MyViewPagerTouchListener();
        mVp.setOnTouchListener(myViewPagerTouchListener);
        MyPagerChangeListener myPagerChangeListener = new MyPagerChangeListener();
        mVp.addOnPageChangeListener(myPagerChangeListener);
        mMyPagerAdapter = new MyPagerAdapter();
        mVp.setAdapter(mMyPagerAdapter);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public void setData(List<? extends IShowItem> data) {
        setData(data, 0);
    }

    public void setData(List<? extends IShowItem> data, int defaultImgId) {
        mData.clear();
        mImageView.clear();
        mLlContainer.removeAllViews();
        mMyPagerAdapter.notifyDataSetChanged();
        if (data != null && data.size() != 0) {
            LinearLayout.LayoutParams dotLayoutParams = new LinearLayout.LayoutParams(mDotRadiu,mDotRadiu);
            dotLayoutParams.rightMargin = mDotMargin;
            for (int i = 0; i < data.size(); i++) {
                mData.put(i, data.get(i));
                ImageView imageView = new ImageView(getContext());
                ImageLoader.getInstance().displayImage(data.get(i).getImageUrl(),imageView, mOptions);
                mImageView.put(i, imageView);
                View view = new View(getContext());
                view.setBackgroundResource(R.drawable.dot);
                mLlContainer.addView(view,dotLayoutParams);
            }
        }
        if(defaultImgId != 0) {
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(defaultImgId)
                    .showImageOnFail(defaultImgId)
                    .showImageForEmptyUri(defaultImgId)
                    .cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            mVp.setBackgroundResource(defaultImgId);
        }
        mMyPagerAdapter.notifyDataSetChanged();
        if(data != null && data.size() != 0) {
            int i = Integer.MAX_VALUE / 2;
            int i1 = innerPosition(i);
            if (i1 != 0) {
                i = i+mData.size()-i1;
            }
            int firstPage = Integer.MAX_VALUE / 2 / mData.size() * mData.size();
            mVp.setCurrentItem(i,false);
        }
    }

    public void setAutoRoll(boolean flag) {
        isAutoRoll = flag;
    }

    private class MyViewPagerTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mData == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = innerPosition(position);
            ImageView imageView = mImageView.get(i);
            if(imageView != null) {
                if(imageView.getParent() != null){
                    ((ViewGroup) imageView.getParent()).removeView(imageView);
                }
                container.addView(imageView);
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
        }

    }

    private int innerPosition(int position) {
        if(mData == null || mData.size() == 0){
            return 0;
        }
        return position % mData.size();
    }

    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int i = innerPosition(position);
            for (int j = 0; j < mData.size(); j++) {
                mLlContainer.getChildAt(j).setEnabled(i != j);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyGestureDetectorListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(innerPosition(mVp.getCurrentItem()));
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    public interface IShowItem {
        String getImageUrl();

        String getTitle();
    }

    public interface MyType {
        int DEFAULT = 0;
    }
}
