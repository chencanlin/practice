package com.org.ccl.practice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.org.ccl.practice.R;

import static android.R.attr.bitmap;

/**
 * Created by ccl on 2017/5/25.
 */

public class RoundImageView extends ImageView {
    private Paint mPaint;
    private Paint mTransparentPaint;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        mTransparentPaint = new Paint();
        mTransparentPaint.setColor(Color.TRANSPARENT);
        mTransparentPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTransparentPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.guide1);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        Rect bitmap1DstRect = getRect(bitmap.getWidth(), bitmap.getHeight());
        Rect screenRect = getRect(getWidth(), getHeight());
//        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawARGB(0,0,0,0);
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, bitmap1DstRect, screenRect, mPaint);
//        canvas.restoreToCount(i);
    }

    private Rect getRect(int width, int height) {
        int left = width > height ? (width - height) / 2 : 0;
        int top = width > height ? 0 : (height - width) / 2;
        int right = width > height ? left + height : left + width;
        int bottom = width > height ? top + height : top + width;
        return new Rect(left,top,right,bottom);
    }
}
