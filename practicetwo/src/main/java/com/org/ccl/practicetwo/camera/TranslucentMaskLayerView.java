package com.org.ccl.practicetwo.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/7/19.
 */

public class TranslucentMaskLayerView extends View {

    private static final int LINE_LENGTH = 120;
    private static final int PADDING_LEFT_RIGHT = 240;
    private static final int PADDING_TOP_BOTTOM = 150;

    private Bitmap mSrcBitmap;
    private Rect mSrcRect;
    private Bitmap mDstBitmap;
    private Rect bgRect;
    private Paint mPaint;
    public Rect lightAreaRect;

    public int mPaddingLeftRight;
    public int mPaddingTopBottom;

    public TranslucentMaskLayerView(Context context) {
        this(context, null);
    }

    public TranslucentMaskLayerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TranslucentMaskLayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TakePictureLayout);
        mPaddingLeftRight = typedArray.getDimensionPixelSize(R.styleable.TakePictureLayout_padding_left_right, PADDING_LEFT_RIGHT);
        mPaddingTopBottom = typedArray.getDimensionPixelSize(R.styleable.TakePictureLayout_padding_top_bottom, PADDING_TOP_BOTTOM);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lightAreaRect == null) {
            lightAreaRect = new Rect(mPaddingLeftRight, mPaddingTopBottom, getMeasuredWidth() - mPaddingLeftRight, getMeasuredHeight() - mPaddingTopBottom);
        }
        if (mDstBitmap == null) {
            mDstBitmap = makeDst();
        }
        if (mSrcBitmap == null) {
            mSrcBitmap = makeSrc();
        }
        int i = canvas.saveLayer(getLeft(), getTop(), getRight(), getBottom(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mDstBitmap, bgRect, bgRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSrcBitmap, mSrcRect, lightAreaRect, mPaint);
        canvas.restoreToCount(i);
        mPaint.setXfermode(null);
    }

    private Bitmap makeDst() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xaa000000);
        Bitmap dstBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        bgRect = new Rect(0, 0, dstBitmap.getWidth(), dstBitmap.getHeight());
        return dstBitmap;
    }

    private Bitmap makeSrc() {
        Bitmap srcBitmap = Bitmap.createBitmap(lightAreaRect.width(), lightAreaRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(15);

        canvas.drawLine(0, 0, LINE_LENGTH, 0, paint);
        canvas.drawLine(0, 0, 0, LINE_LENGTH, paint);
        canvas.drawLine(lightAreaRect.width(), 0, lightAreaRect.width() - LINE_LENGTH, 0, paint);
        canvas.drawLine(lightAreaRect.width(), 0, lightAreaRect.width(), LINE_LENGTH, paint);
        canvas.drawLine(0, lightAreaRect.height(), 0, lightAreaRect.height() - LINE_LENGTH, paint);
        canvas.drawLine(0, lightAreaRect.height(), LINE_LENGTH, lightAreaRect.height(), paint);
        canvas.drawLine(lightAreaRect.width(), lightAreaRect.height(), lightAreaRect.width(), lightAreaRect.height() - LINE_LENGTH, paint);
        canvas.drawLine(lightAreaRect.width(), lightAreaRect.height(), lightAreaRect.width() - LINE_LENGTH, lightAreaRect.height(), paint);
        mSrcRect = new Rect(0, 0, lightAreaRect.width(), lightAreaRect.height());
        return srcBitmap;
    }

    public void releaseBitmap() {
        if (mSrcBitmap != null) {
            mSrcBitmap.recycle();
            mSrcBitmap = null;
        }
        if (mDstBitmap != null) {
            mDstBitmap.recycle();
            mDstBitmap = null;
        }
    }
}
