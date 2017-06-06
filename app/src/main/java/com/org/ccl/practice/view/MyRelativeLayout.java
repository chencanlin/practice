package com.org.ccl.practice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ccl on 2017/6/6.
 */

public class MyRelativeLayout extends RelativeLayout {
    private boolean initPoint = true;
    private Paint mPaint;
    public RectF lightAreaRect;
    private Rect sourceBitmapSrcRect;
    private Rect bgSrcRect;
    private RectF bgDstRect;
    private int touchOnLineNumber = 0;
    private boolean isTouchOnLine = false;
    private ZoomImageView mImageView;
    private float mX1;
    private float mY1;
    private Bitmap mDstBitmap;
    private boolean isScreenShot = false;

    public MyRelativeLayout(Context context) {
        this(context, null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                // 第二个手指按下事件
                float oriDis = distance(ev);
                if (oriDis > 10f) {
                    isTouchOnLine = false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                isTouchOnLine = false;
                mX1 = ev.getX();
                mY1 = ev.getY();
                if (lightAreaRect.contains(mX1,mY1)) {
                    isTouchOnLine = true;
                    touchOnLineNumber = 0;
                }
                if (mX1 >= lightAreaRect.left- 30 && mX1 <= lightAreaRect.right + 30 && mY1 >= lightAreaRect.top - 30 && mY1 <= lightAreaRect.top + 30) {
                    isTouchOnLine = true;
                    touchOnLineNumber = 4;
                }
                if (!isTouchOnLine) {
                    if (mX1 >= lightAreaRect.left - 30 && mX1 <= lightAreaRect.left + 30 && mY1 >= lightAreaRect.top- 30 && mY1 <= lightAreaRect.bottom+ 30) {
                        isTouchOnLine = true;
                        touchOnLineNumber = 5;
                    }
                }
                if (!isTouchOnLine) {
                    if (mX1 >= lightAreaRect.left - 30 && mX1 <= lightAreaRect.right + 30 && mY1 >= lightAreaRect.bottom- 30 && mY1 <= lightAreaRect.bottom + 30) {
                        isTouchOnLine = true;
                        touchOnLineNumber = 6;
                    }
                }
                if (!isTouchOnLine) {
                    if (mX1 >= lightAreaRect.right - 30 && mX1 <= lightAreaRect.right + 30 && mY1 >= lightAreaRect.top - 30 && mY1 <= lightAreaRect.bottom + 30) {
                        isTouchOnLine = true;
                        touchOnLineNumber = 7;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchOnLine) {
                    RectF matrixRectF = mImageView.getMatrixRectF();
                    float vX = ev.getX();
                    float vY = ev.getY();
                    if (touchOnLineNumber == 0) {
                        float left = lightAreaRect.left - mX1 + vX;
                        float top = lightAreaRect.top - mY1 + vY;
                        float minLeftX = matrixRectF.left >= 0 ? matrixRectF.left : 0;
                        float maxLeftX = matrixRectF.right >= getWidth() ? getWidth() - lightAreaRect.width() : matrixRectF.right - lightAreaRect.width();
                        float minTopY = matrixRectF.top >= 0 ? matrixRectF.top : 0;
                        float maxTopY = matrixRectF.bottom >= getHeight() ? getHeight() - lightAreaRect.height() : matrixRectF.bottom - lightAreaRect.height();
                        if (left > minLeftX && left <= maxLeftX && top >= minTopY && top <= maxTopY) {
                            float right = left + lightAreaRect.width();
                            float bottom = top + lightAreaRect.height();
                            lightAreaRect.left = left;
                            lightAreaRect.right = right;
                            lightAreaRect.top = top;
                            lightAreaRect.bottom = bottom;
                        }

                    }
                    if (touchOnLineNumber == 4) {
                        if (lightAreaRect.bottom - vY <= 100) {
                            vY = lightAreaRect.bottom  - 100;
                        }
                        if (vY <= matrixRectF.top) {
                            vY = matrixRectF.top;
                        }
                        lightAreaRect.top = vY;
                    } else if (touchOnLineNumber == 5) {
                        if (lightAreaRect.right - vX <= 100) {
                            vX = lightAreaRect.right - 100;
                        }
                        if (vX <= matrixRectF.left) {
                            vX = matrixRectF.left;
                        }
                        lightAreaRect.left = vX;
                    } else if (touchOnLineNumber == 6) {
                        if (vY - lightAreaRect.top <= 100) {
                            vY = 100 + lightAreaRect.top;
                        }
                        if (vY >= matrixRectF.bottom) {
                            vY = matrixRectF.bottom;
                        }
                        lightAreaRect.bottom = vY;
                    } else if (touchOnLineNumber == 7) {
                        if (vX - lightAreaRect.top <= 100) {
                            vX = 100 + lightAreaRect.top;
                        }
                        if (vX >= matrixRectF.right) {
                            vX = matrixRectF.right;
                        }
                       lightAreaRect.right = vX;
                    }
                    invalidate();
                    mX1 = vX;
                    mY1 = vY;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouchOnLine = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        int i1 = canvas.saveLayer(getLeft(), getTop(), getRight(), getBottom(), mPaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        if (initPoint) {
            mImageView = ((ZoomImageView) getChildAt(0));
            RectF matrixRectF = mImageView.getMatrixRectF();
            lightAreaRect = matrixRectF;
            mDstBitmap = makeDst();
            initPoint = false;
        }
        int i = canvas.saveLayer(getLeft(), getTop(), getRight(), getBottom(), mPaint, Canvas.ALL_SAVE_FLAG);
        Bitmap srcBitmap = makeSrc();
        canvas.drawBitmap(mDstBitmap, bgSrcRect, bgDstRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, sourceBitmapSrcRect, lightAreaRect, mPaint);
        canvas.restoreToCount(i);
        mPaint.setXfermode(null);
        srcBitmap.recycle();
    }

    private Bitmap makeDst() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xaa000000);
        Bitmap dstBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBitmap);
//        canvas.drawARGB(255, 255, 255, 255);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        bgSrcRect = new Rect(0, 0, getWidth(), getHeight());
        bgDstRect = new RectF(0, 0, getWidth(), getHeight());
        return dstBitmap;
    }

    private Bitmap makeSrc() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        if(isScreenShot){
            paint.setColor(Color.TRANSPARENT);
        }else {
            paint.setColor(Color.WHITE);
        }
        Bitmap srcBitmap = Bitmap.createBitmap((int) lightAreaRect.width(), (int) lightAreaRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        paint.setStrokeWidth(15);
        canvas.drawRect(0, 0, (int)lightAreaRect.width(), (int)lightAreaRect.height(), paint);
        for (int i = 0; i < 4; i++) {
            if (i != 0 && i != 3) {
                paint.setStrokeWidth(3);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawLine(0, i * lightAreaRect.height() / 3, lightAreaRect.width(), i * lightAreaRect.height() / 3, paint);
                canvas.drawLine(i * lightAreaRect.width() / 3, 0, i * lightAreaRect.width() / 3, lightAreaRect.height(), paint);
            }
        }
        sourceBitmapSrcRect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        return srcBitmap;
    }

    // 计算两个触摸点之间的距离
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    public void scaleLightArea(){
        RectF matrixRectF = mImageView.getMatrixRectF();
        if(matrixRectF.left>lightAreaRect.left){
            lightAreaRect.left = matrixRectF.left;
        }
        if(matrixRectF.top>lightAreaRect.top){
            lightAreaRect.top = matrixRectF.top;
        }
        if(matrixRectF.right <lightAreaRect.right){
            lightAreaRect.right = matrixRectF.right;
        }
        if(matrixRectF.bottom<lightAreaRect.bottom){
            lightAreaRect.bottom = matrixRectF.bottom;
        }
        invalidate();
    }

    public void screenShot(boolean flag){
        isScreenShot = flag;
        invalidate();
    }
}
