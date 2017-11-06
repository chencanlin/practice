package com.org.ccl.practicetwo.drawablecustom;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.org.ccl.practicetwo.util.DensityUtils;


/**
 * Created by ccl on 2017/9/19.
 */

public class RoundRectDrawable extends Drawable {

    private final Bitmap mBitmap;
    private final Paint mPaint;
    private final int mBitmapWidth;
    private int mViewMeasuredWidth;
    private int mViewMeasuredHeight;
    private final BitmapShader mBitmapShader;
    private View mView;

    public RoundRectDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
        mBitmapWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
    }

    public void setShowView(final View view) {
        mView = view;
        view.post(new Runnable() {
            @Override
            public void run() {
                mViewMeasuredWidth = view.getMeasuredWidth();
                mViewMeasuredHeight = view.getMeasuredHeight();
                float max = Math.max(mViewMeasuredWidth * 1.0f / mBitmapWidth, mViewMeasuredHeight * 1.0f / mBitmapWidth);
                Matrix matrix = new Matrix();
                matrix.setScale(max, max);
                mBitmapShader.setLocalMatrix(matrix);
                invalidateSelf();
            }
        });
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(new RectF(0, 0,
                mViewMeasuredWidth, mViewMeasuredHeight),
                DensityUtils.dp2px(mView.getContext(), 15),
                DensityUtils.dp2px(mView.getContext(), 15), mPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapWidth;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
