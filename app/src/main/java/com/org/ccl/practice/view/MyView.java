package com.org.ccl.practice.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.org.ccl.practice.R;

/**
 * Created by ccl on 2017/5/23.
 */

public class MyView extends View {
    // type_one
    private PointF testOneLeft = new PointF(0, 0);
    private PointF testOneRight = new PointF(0, 0);
    private PointF center = new PointF(0, 0);
    private Paint mPaint;
    private int mTestType;
    private Paint mPaint1;
    private int mCenterX;
    private int mCenterY;


    // type_two
    private PointF left = new PointF(0, 0);
    private PointF leftTopOne = new PointF(0, 0);
    private PointF leftTopTwo = new PointF(0, 0);
    private PointF top = new PointF(0, 0);
    private PointF rightTopOne = new PointF(0, 0);
    private PointF rightTopTwo = new PointF(0, 0);
    private PointF right = new PointF(0, 0);
    private PointF rightBottomOne = new PointF(0, 0);
    private PointF rightBottomTwo = new PointF(0, 0);
    private PointF down = new PointF(0, 0);
    private PointF leftBottomOne = new PointF(0, 0);
    private PointF leftBottomTwo = new PointF(0, 0);
    private int mScreenHeigt;
    private int mScreenWidth;

    // type_three
    private PointF start = new PointF(0,0);
    private PointF one = new PointF(0, 0);
    private PointF two = new PointF(0, 0);
    private PointF three = new PointF(0, 0);
    private PointF four = new PointF(0, 0);
    private PointF five = new PointF(0, 0);
    private PointF six = new PointF(0, 0);
    private PointF seven = new PointF(0, 0);
    private PointF eight = new PointF(0, 0);
    private PointF nine = new PointF(0, 0);
    private Paint mPaint3;
    private Path mPath3;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        mTestType = typedArray.getInt(R.styleable.MyView_test_type, 1);
        typedArray.recycle();
        init();
        setClickable(true);
    }

    private void init() {
        if (mTestType == 1) {
            mPaint = new Paint();
            mPaint.setStrokeWidth(20);
            mPaint.setColor(Color.GRAY);
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
        }else if(mTestType == 3){
            mPaint3 = new Paint();
            mPaint3.setColor(0xff66C9F7);
            mPaint3.setStyle(Paint.Style.FILL);
            mPaint3.setStrokeWidth(8);
            mPaint3.setAntiAlias(true);
            mPaint3.setDither(true);
            mPath3 = new Path();
        }

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        mScreenHeigt = defaultDisplay.getHeight();
        mScreenWidth = defaultDisplay.getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        if (mTestType == 1) {
            testOneLeft.x = mCenterX - 200;
            testOneLeft.y = mCenterY;
            testOneRight.x = mCenterX + 200;
            testOneRight.y = mCenterY;
            center.x = mCenterX;
            center.y = mCenterY - 300;
        } else if (mTestType == 2) {
            left.x = mCenterX - 200;
            left.y = mCenterY;
            leftTopOne.x = mCenterX - 200;
            leftTopOne.y = mCenterY - 100;
            leftTopTwo.x = mCenterX - 100;
            leftTopTwo.y = mCenterY - 200;
            top.x = mCenterX;
            top.y = mCenterY - 200;
            rightTopOne.x = mCenterX + 100;
            rightTopOne.y = mCenterY - 200;
            rightTopTwo.x = mCenterX + 200;
            rightTopTwo.y = mCenterY - 100;
            right.x = mCenterX + 200;
            right.y = mCenterY;
            rightBottomOne.x = mCenterX + 170;
            rightBottomOne.y = mCenterY + 110;
            rightBottomTwo.x = mCenterX + 100;
            rightBottomTwo.y = mCenterY + 200;
            down.x = mCenterX;
            down.y = mCenterY + 200;
            leftBottomOne.x = mCenterX - 100;
            leftBottomOne.y = mCenterY + 200;
            leftBottomTwo.x = mCenterX - 170;
            leftBottomTwo.y = mCenterY + 110;
        } else {
            start.x = -w;
            start.y = h/2;
            one.x = -w ;
            one.y = h / 2;
            two.x = -3*w / 4;
            two.y = h / 2 - 80;
            three.x = -w / 2;
            three.y = h / 2;
            four.x = -w / 4;
            four.y = h / 2 + 80;
            five.x = 0;
            five.y = h / 2;
            six.x =  w / 4;
            six.y = h / 2 - 80;
            seven.x = w / 2;
            seven.y = h / 2;
            eight.x = 3* w / 4;
            eight.y = h / 2 + 80;
            nine.x = w;
            nine.y = h / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTestType == 1) {
            mPaint.setStrokeWidth(20);
            mPaint.setColor(Color.GRAY);
            canvas.drawPoint(testOneLeft.x, testOneLeft.y, mPaint);
            canvas.drawPoint(testOneRight.x, testOneRight.y, mPaint);
            canvas.drawPoint(center.x, center.y, mPaint);
            mPaint.setStrokeWidth(4);
            canvas.drawLine(testOneLeft.x, testOneLeft.y, center.x, center.y, mPaint);
            canvas.drawLine(testOneRight.x, testOneRight.y, center.x, center.y, mPaint);
            Path path = new Path();
            path.moveTo(testOneLeft.x, testOneLeft.y);
            path.quadTo(center.x, center.y, testOneRight.x, testOneRight.y);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(8);
            mPaint.setColor(Color.RED);
            canvas.drawPath(path, mPaint);
        } else if (mTestType == 2) {
            mPaint1 = new Paint();
            mPaint1.setColor(Color.GRAY);
            mPaint1.setStrokeWidth(4);
            canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint1);
            canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint1);
            mPaint1.setColor(Color.RED);
            mPaint1.setStrokeWidth(8);
            mPaint1.setFlags(Paint.ANTI_ALIAS_FLAG);
            mPaint1.setStyle(Paint.Style.STROKE);
            Path Path = new Path();
            Path.moveTo(left.x, left.y);
            Path.cubicTo(leftTopOne.x, leftTopOne.y, leftTopTwo.x, leftTopTwo.y, top.x, top.y);
//            Path rightTopPath = new Path();
//            rightTopPath.moveTo(top.x, top.y);
            Path.cubicTo(rightTopOne.x, rightTopOne.y, rightTopTwo.x, rightTopTwo.y, right.x, right.y);
//            Path rightBottomPath = new Path();
//            rightBottomPath.moveTo(right.x, right.y);
            Path.cubicTo(rightBottomOne.x, rightBottomOne.y, rightBottomTwo.x, rightBottomTwo.y, down.x, down.y);
//            Path leftBottomPath = new Path();
//            leftBottomPath.moveTo(down.x, down.y);
            Path.cubicTo(leftBottomOne.x, leftBottomOne.y, leftBottomTwo.x, leftBottomTwo.y, left.x, left.y);
//            canvas.drawPath(leftTopPath, mPaint1);
//            canvas.drawPath(rightTopPath, mPaint1);
//            canvas.drawPath(rightBottomPath, mPaint1);
            canvas.drawPath(Path, mPaint1);
        } else {
            mPath3.reset();
            mPath3.moveTo(one.x, one.y);
            mPath3.quadTo(two.x, two.y, three.x, three.y);
            mPath3.quadTo(four.x, four.y, five.x, five.y);
            mPath3.quadTo(six.x, six.y, seven.x, seven.y);
            mPath3.quadTo(eight.x, eight.y, nine.x, nine.y);
            mPath3.lineTo(mCenterX*2,mCenterY*2);
            mPath3.lineTo(0,mCenterY*2);
            mPath3.close();
            canvas.drawPath(mPath3, mPaint3);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (mTestType == 1) {
            center.x = x;
            center.y = y;
            AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                startAnimation(accelerateDecelerateInterpolator, x, y);
            }
        } else if (mTestType == 2) {
            if (y <= mCenterY) {
                top.y = y;
            } else {
                if (x <= mCenterX) {
                    leftBottomOne.x = x;
                    leftBottomOne.y = y;
                } else {
                    rightBottomTwo.x = x;
                    rightBottomTwo.y = y;
                }
            }
        } else {
            startValueAnimation();
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    private void startValueAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start.x, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                one.x = (float) animation.getAnimatedValue();
                two.x = one.x+mCenterX/2;
                three.x = two.x +mCenterX/2;
                four.x = three.x +mCenterX/2;
                five.x = four.x+mCenterX/2;
                six.x = five.x +mCenterX/2;
                seven.x = six.x+mCenterX/2;
                eight.x = seven.x+mCenterX/2;
                nine.x = eight.x+mCenterX/2;
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        valueAnimator.setRepeatCount(100);
        valueAnimator.start();
    }

    private void startAnimation(Interpolator interpolator, float x, float y) {
        float dX = mCenterX - x;
        float dY = mCenterY - y;
        Float[] xValue = new Float[]{mCenterX - dX, mCenterX + dX, mCenterX - (2 * dX / 3),
                mCenterX + (2 * dX / 3), mCenterX - (1 * dX / 2),
                mCenterX + (1 * dX / 2), mCenterX - (1 * dX / 3), mCenterX + (1 * dX / 3), Float.valueOf(mCenterX)};
        Float[] yValue = new Float[]{
                mCenterY - dY, mCenterY + dY, mCenterY - (2 * dY / 3), mCenterY + (2 * dY / 3),
                mCenterY - (1 * dY / 2),
                mCenterY + (1 * dY / 2), mCenterY - (1 * dY / 3), mCenterY + (1 * dY / 3), Float.valueOf(mCenterY)
        };
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(xValue[0], xValue[1], xValue[2], xValue[3], xValue[4], xValue[5], xValue[6], xValue[7], xValue[8]);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                center.x = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(yValue[0], yValue[1], yValue[2], yValue[3], yValue[4], yValue[5], yValue[6], yValue[7], yValue[8]);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                center.y = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(interpolator);
        valueAnimator1.setDuration(2000);
        valueAnimator1.start();
    }
}
