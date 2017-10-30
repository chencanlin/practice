package com.org.ccl.practicetwo.materialdesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/19.
 */

public class DrawableSetBoundsTest extends View {
    private static final String TAG = DrawableSetBoundsTest.class.getName();
    private Paint mPaint;

    public DrawableSetBoundsTest(Context context) {
        this(context, null);
    }

    public DrawableSetBoundsTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableSetBoundsTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /*BitmapDrawable drawable1 = (BitmapDrawable) drawable;
        Bitmap bitmap = drawable1.getBitmap();
        canvas.drawBitmap(bitmap, 0, 0, mPaint);*/
        drawable.setBounds(0,0,getMeasuredWidth()/4,getMeasuredHeight()/4);
        drawable.draw(canvas);
        Rect bounds = drawable.getBounds();
        Rect rect = drawable.copyBounds();
        Rect copyRect = new Rect();
        drawable.copyBounds(copyRect);
        Log.i(TAG, "onDraw: ");
    }
}
