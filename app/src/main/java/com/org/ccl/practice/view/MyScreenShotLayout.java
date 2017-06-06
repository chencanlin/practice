package com.org.ccl.practice.view;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.org.ccl.practice.R;

/**
 * Created by ccl on 2017/6/6.
 */

public class MyScreenShotLayout extends RelativeLayout {
    private static final int EDGE_TOUCH_RANGE = 80;
    private static final int MIN_SCREEN_SHOT_WIDTH = 100;
    private static final int MIN_SCREEN_SHOT_HEIGHT = 100;

    private boolean initPoint = true;
    private Paint mPaint;
    public RectF lightAreaRect;
    private Rect sourceBitmapSrcRect;
    private Rect bgSrcRect;
    private RectF bgDstRect;
    private int touchOnLineNumber = 0;
    private boolean isTouchOnLine = false;
    public ZoomImageView mImageView;
    private float mX1;
    private float mY1;
    private Bitmap mDstBitmap;
    private boolean isScreenShot = false;
    private int mEdgeTouchRange;
    private int mMinScreenShotWidth;
    private int mMinScreenShotHeight;

    private static class TouchPosition {
        private static final int TOUCH_ON_LEFT_TOP = 0;
        private static final int TOUCH_ON_LEFT_BOTTOM = 1;
        private static final int TOUCH_ON_RIGHT_BOTTOM = 2;
        private static final int TOUCH_ON_RIGHT_TOP = 3;
        private static final int TOUCH_ON_CENTER = 4;
        private static final int TOUCH_ON_LEFT = 5;
        private static final int TOUCH_ON_BOTTOM = 6;
        private static final int TOUCH_ON_RIGHT = 7;
        private static final int TOUCH_ON_TOP = 8;
    }

    public MyScreenShotLayout(Context context) {
        this(context, null);
    }

    public MyScreenShotLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScreenShotLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyScreenShotLayout);
        mEdgeTouchRange = typedArray.getInteger(R.styleable.MyScreenShotLayout_edge_touch_range, EDGE_TOUCH_RANGE);
        mMinScreenShotWidth = typedArray.getDimensionPixelSize(R.styleable.MyScreenShotLayout_min_screen_shot_width, MIN_SCREEN_SHOT_WIDTH);
        mMinScreenShotHeight = typedArray.getDimensionPixelSize(R.styleable.MyScreenShotLayout_min_screen_shot_height, MIN_SCREEN_SHOT_HEIGHT);
        typedArray.recycle();
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

                RectF rectF = new RectF(lightAreaRect.left + mEdgeTouchRange, lightAreaRect.top + mEdgeTouchRange, lightAreaRect.right - mEdgeTouchRange, lightAreaRect.bottom - mEdgeTouchRange);
                if (mX1 >= lightAreaRect.left - mEdgeTouchRange && mX1 <= lightAreaRect.left + mEdgeTouchRange && mY1 >= lightAreaRect.top - mEdgeTouchRange && mY1 <= lightAreaRect.top + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_LEFT_TOP;
                } else if (mX1 >= lightAreaRect.left - mEdgeTouchRange && mX1 <= lightAreaRect.left + mEdgeTouchRange && mY1 >= lightAreaRect.bottom - mEdgeTouchRange && mY1 <= lightAreaRect.bottom + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_LEFT_BOTTOM;
                } else if (mX1 >= lightAreaRect.right - mEdgeTouchRange && mX1 <= lightAreaRect.right + mEdgeTouchRange && mY1 >= lightAreaRect.bottom - mEdgeTouchRange && mY1 <= lightAreaRect.bottom + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_RIGHT_BOTTOM;
                } else if (mX1 >= lightAreaRect.right - mEdgeTouchRange && mX1 <= lightAreaRect.right + mEdgeTouchRange && mY1 >= lightAreaRect.top - mEdgeTouchRange && mY1 <= lightAreaRect.top + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_RIGHT_TOP;
                } else if (rectF.contains(mX1, mY1)) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_CENTER;
                } else if (mX1 >= lightAreaRect.left - mEdgeTouchRange && mX1 <= lightAreaRect.left + mEdgeTouchRange && mY1 >= lightAreaRect.top - mEdgeTouchRange && mY1 <= lightAreaRect.bottom + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_LEFT;
                } else if (mX1 >= lightAreaRect.left - mEdgeTouchRange && mX1 <= lightAreaRect.right + mEdgeTouchRange && mY1 >= lightAreaRect.bottom - mEdgeTouchRange && mY1 <= lightAreaRect.bottom + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_BOTTOM;
                } else if (mX1 >= lightAreaRect.right - mEdgeTouchRange && mX1 <= lightAreaRect.right + mEdgeTouchRange && mY1 >= lightAreaRect.top - mEdgeTouchRange && mY1 <= lightAreaRect.bottom + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_RIGHT;
                } else if (mX1 >= lightAreaRect.left - mEdgeTouchRange && mX1 <= lightAreaRect.right + mEdgeTouchRange && mY1 >= lightAreaRect.top - mEdgeTouchRange && mY1 <= lightAreaRect.top + mEdgeTouchRange) {
                    isTouchOnLine = true;
                    touchOnLineNumber = TouchPosition.TOUCH_ON_TOP;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchOnLine) {
                    RectF matrixRectF = mImageView.getMatrixRectF();
                    float vX = ev.getX();
                    float vY = ev.getY();
                    switch (touchOnLineNumber) {
                        case TouchPosition.TOUCH_ON_LEFT_TOP:
                            touchOnLeftTop(matrixRectF, vX, vY);
                            break;
                        case TouchPosition.TOUCH_ON_LEFT_BOTTOM:
                            touchOnLeftBottom(matrixRectF, vX, vY);
                            break;
                        case TouchPosition.TOUCH_ON_RIGHT_BOTTOM:
                            touchOnRightBottom(matrixRectF, vX, vY);
                            break;
                        case TouchPosition.TOUCH_ON_RIGHT_TOP:
                            touchOnRightTop(matrixRectF, vX, vY);
                            break;
                        case TouchPosition.TOUCH_ON_CENTER:
                            touchOnCenter(matrixRectF, vX, vY);
                            break;
                        case TouchPosition.TOUCH_ON_LEFT:
                            touchOnLeft(matrixRectF, vX);
                            break;
                        case TouchPosition.TOUCH_ON_BOTTOM:
                            touchOnBottom(matrixRectF, vY);
                            break;
                        case TouchPosition.TOUCH_ON_RIGHT:
                            touchOnRight(matrixRectF, vX);
                            break;
                        case TouchPosition.TOUCH_ON_TOP:
                            touchOnTop(matrixRectF, vY);
                            break;
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

    private void touchOnRightTop(RectF matrixRectF, float vX, float vY) {
        float right = lightAreaRect.right - mX1 + vX;
        float top = lightAreaRect.top - mY1 + vY;
        if (right - lightAreaRect.left < mMinScreenShotWidth) {
            right = lightAreaRect.left + mMinScreenShotWidth;
        }
        if (right > matrixRectF.right) {
            right = matrixRectF.right;
        }
        if (lightAreaRect.bottom - top < mMinScreenShotHeight) {
            top = lightAreaRect.top - mMinScreenShotHeight;
        }
        if (top < matrixRectF.top) {
            top = matrixRectF.top;
        }
        lightAreaRect.right = right;
        lightAreaRect.top = top;
    }

    private void touchOnRightBottom(RectF matrixRectF, float vX, float vY) {
        float right = lightAreaRect.right - mX1 + vX;
        float bottom = lightAreaRect.bottom - mY1 + vY;
        if (right - lightAreaRect.left < mMinScreenShotWidth) {
            right = lightAreaRect.left + mMinScreenShotWidth;
        }
        if (right > matrixRectF.right) {
            right = matrixRectF.right;
        }
        if (bottom - lightAreaRect.top < mMinScreenShotHeight) {
            bottom = lightAreaRect.top + mMinScreenShotHeight;
        }
        if (bottom > matrixRectF.bottom) {
            bottom = matrixRectF.bottom;
        }
        lightAreaRect.right = right;
        lightAreaRect.bottom = bottom;
    }

    private void touchOnLeftBottom(RectF matrixRectF, float vX, float vY) {
        float left = lightAreaRect.left - mX1 + vX;
        float bottom = lightAreaRect.bottom - mY1 + vY;
        if (lightAreaRect.right - left < mMinScreenShotWidth) {
            left = lightAreaRect.right - mMinScreenShotWidth;
        }
        if (left < matrixRectF.left) {
            left = matrixRectF.left;
        }
        if (bottom - lightAreaRect.top < mMinScreenShotHeight) {
            bottom = lightAreaRect.top + mMinScreenShotHeight;
        }
        if (bottom > matrixRectF.bottom) {
            bottom = matrixRectF.bottom;
        }
        lightAreaRect.left = left;
        lightAreaRect.bottom = bottom;
    }

    private void touchOnLeftTop(RectF matrixRectF, float vX, float vY) {
        float left = lightAreaRect.left - mX1 + vX;
        float top = lightAreaRect.top - mY1 + vY;
        if (lightAreaRect.right - left < mMinScreenShotWidth) {
            left = lightAreaRect.right - mMinScreenShotWidth;
        }
        if (left < matrixRectF.left) {
            left = matrixRectF.left;
        }
        if (lightAreaRect.bottom - top < mMinScreenShotHeight) {
            top = lightAreaRect.bottom - mMinScreenShotHeight;
        }
        if (top < matrixRectF.top) {
            top = matrixRectF.top;
        }
        lightAreaRect.left = left;
        lightAreaRect.top = top;
    }

    private void touchOnRight(RectF matrixRectF, float vX) {
        if (vX - lightAreaRect.left <= mMinScreenShotWidth) {
            vX = mMinScreenShotWidth + lightAreaRect.left;
        }
        if (vX >= matrixRectF.right) {
            vX = matrixRectF.right;
        }
        lightAreaRect.right = vX;
    }

    private void touchOnBottom(RectF matrixRectF, float vY) {
        if (vY - lightAreaRect.top <= mMinScreenShotHeight) {
            vY = mMinScreenShotHeight + lightAreaRect.top;
        }
        if (vY >= matrixRectF.bottom) {
            vY = matrixRectF.bottom;
        }
        lightAreaRect.bottom = vY;
    }

    private void touchOnLeft(RectF matrixRectF, float vX) {
        if (lightAreaRect.right - vX <= mMinScreenShotWidth) {
            vX = lightAreaRect.right - mMinScreenShotWidth;
        }
        if (vX <= matrixRectF.left) {
            vX = matrixRectF.left;
        }
        lightAreaRect.left = vX;
    }

    private void touchOnTop(RectF matrixRectF, float vY) {
        if (lightAreaRect.bottom - vY <= mMinScreenShotHeight) {
            vY = lightAreaRect.bottom - mMinScreenShotHeight;
        }
        if (vY <= matrixRectF.top) {
            vY = matrixRectF.top;
        }
        lightAreaRect.top = vY;
    }

    private void touchOnCenter(RectF matrixRectF, float vX, float vY) {
        float left = lightAreaRect.left - mX1 + vX;
        float top = lightAreaRect.top - mY1 + vY;
        float minLeftX = matrixRectF.left >= 0 ? matrixRectF.left : 0;
        float maxLeftX = matrixRectF.right >= getWidth() ? getWidth() - lightAreaRect.width() : matrixRectF.right - lightAreaRect.width();
        float minTopY = matrixRectF.top >= 0 ? matrixRectF.top : 0;
        float maxTopY = matrixRectF.bottom >= getHeight() ? getHeight() - lightAreaRect.height() : matrixRectF.bottom - lightAreaRect.height();
        left = left < minLeftX ? minLeftX : left;
        left = left > maxLeftX ? maxLeftX : left;
        top = top < minTopY ? minTopY : top;
        top = top > maxTopY ? maxTopY : top;
        float right = left + lightAreaRect.width();
        float bottom = top + lightAreaRect.height();
        lightAreaRect.left = left;
        lightAreaRect.right = right;
        lightAreaRect.top = top;
        lightAreaRect.bottom = bottom;
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
        if (isScreenShot) {
            paint.setColor(Color.TRANSPARENT);
        } else {
            paint.setColor(Color.WHITE);
        }
        Bitmap srcBitmap = Bitmap.createBitmap((int) lightAreaRect.width(), (int) lightAreaRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        paint.setStrokeWidth(15);
        canvas.drawRect(0, 0, (int) lightAreaRect.width(), (int) lightAreaRect.height(), paint);
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

    public void scaleLightArea() {
        RectF matrixRectF = mImageView.getMatrixRectF();
        if (matrixRectF.left > lightAreaRect.left) {
            lightAreaRect.left = matrixRectF.left;
        }
        if (matrixRectF.top > lightAreaRect.top) {
            lightAreaRect.top = matrixRectF.top;
        }
        if (matrixRectF.right < lightAreaRect.right) {
            lightAreaRect.right = matrixRectF.right;
        }
        if (matrixRectF.bottom < lightAreaRect.bottom) {
            lightAreaRect.bottom = matrixRectF.bottom;
        }
        invalidate();
    }

    public void autoScaleLightArea() {
        RectF matrixRectF = mImageView.getMatrixRectF();
        lightAreaRect.left = matrixRectF.left > 0 ? matrixRectF.left : 0;
        lightAreaRect.top = matrixRectF.top > 0 ? matrixRectF.top : 0;
        lightAreaRect.right = matrixRectF.right < getWidth() ? matrixRectF.right : getWidth();
        lightAreaRect.bottom = matrixRectF.bottom < getHeight() ? matrixRectF.bottom : getHeight();
        invalidate();
    }

    public void screenShot(boolean flag) {
        isScreenShot = flag;
        invalidate();
    }
}
