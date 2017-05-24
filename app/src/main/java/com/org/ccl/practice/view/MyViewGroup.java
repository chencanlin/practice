package com.org.ccl.practice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.org.ccl.practice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2017/5/15.
 */

public class MyViewGroup extends ViewGroup {
    private static final int mCircleRadiu = 70;
    private static final int mCircleMargin = 50;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mMarginLeft;
    private int downX = 0;
    private int downY = 0;
    private int moveX = 0;
    private int moveY = 0;

    private List<Circle> circleList = new ArrayList<>();
    private int mMarginTop;

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        mScreenWidth = defaultDisplay.getWidth();
        mScreenHeight = defaultDisplay.getHeight();
        mMarginLeft = (mScreenWidth - mCircleRadiu * 2 * 3 - mCircleMargin * 2) / 2;
        mMarginTop = (mScreenHeight - mCircleRadiu * 2 * 3 - mCircleMargin * 2) / 2;
        initCircle();
    }

    private void initCircle() {
        Circle circle1 = new Circle(mMarginLeft + mCircleRadiu, mMarginTop + mCircleRadiu, Color.BLUE);
        Circle circle2 = new Circle(circle1.getX() + mCircleRadiu * 3, circle1.getY(), Color.BLUE);
        Circle circle3 = new Circle(circle2.getX() + mCircleRadiu * 3, circle2.getY(), Color.BLUE);
        Circle circle4 = new Circle(circle1.getX(), circle1.getY() + mCircleRadiu * 3, Color.BLUE);
        Circle circle5 = new Circle(circle4.getX() + mCircleRadiu * 3, circle4.getY(), Color.BLUE);
        Circle circle6 = new Circle(circle5.getX() + mCircleRadiu * 3, circle5.getY(), Color.BLUE);
        Circle circle7 = new Circle(circle4.getX(), circle4.getY() + mCircleRadiu * 3, Color.BLUE);
        Circle circle8 = new Circle(circle7.getX() + mCircleRadiu * 3, circle7.getY(), Color.BLUE);
        Circle circle9 = new Circle(circle8.getX() + mCircleRadiu * 3, circle8.getY(), Color.BLUE);
        circleList.add(circle1);
        circleList.add(circle2);
        circleList.add(circle3);
        circleList.add(circle4);
        circleList.add(circle5);
        circleList.add(circle6);
        circleList.add(circle7);
        circleList.add(circle8);
        circleList.add(circle9);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        /*if (childCount > 0) {
            View childAtZero = getChildAt(0);
            measureChild(childAtZero, widthMeasureSpec, heightMeasureSpec);
        }*/
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            View childAtZero = getChildAt(0);
            childAtZero.layout(0, 0, childAtZero.getMeasuredWidth(), childAtZero.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                moveX = 0;
                moveY = 0;
                break;

        }
        Circle currentTouchPoint = getCurrentTouchPoint(event.getX(), event.getY());
        postInvalidate();
        return true;
    }

    private Circle getCurrentTouchPoint(float ex, float ey) {
        for (int i = 0; i < circleList.size(); i++) {
            Circle item = circleList.get(i);
            boolean inRound = isInRound(item.getX(), item.getY(), mCircleRadiu, ex, ey);
            if (inRound) {
                item.setColor(Color.RED);
                return item;
            }
        }
        return null;
    }

    public static boolean isInRound(int x1, int y1, int r, float x2, float y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) < r * r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        for (Circle item : circleList) {
            paint.setColor(item.getColor());
            canvas.drawCircle(item.getX(), item.getY(), mCircleRadiu, paint);
        }
        canvas.drawLine(downX, downY, moveX, moveY, paint);*/

        /*Paint circlePaint = new Paint();
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 2, circlePaint);
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                circlePaint.setStrokeWidth(5);
                circlePaint.setTextSize(30);
                canvas.drawLine(mScreenWidth / 2, mScreenHeight / 2 - mScreenWidth / 2, mScreenWidth / 2, mScreenHeight / 2 - mScreenWidth / 2 + 60,circlePaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mScreenWidth / 2 - circlePaint.measureText(degree) / 2,
                        mScreenHeight / 2 - mScreenWidth / 2 + 90, circlePaint);
            }else{
                circlePaint.setStrokeWidth(3);
                circlePaint.setTextSize(15);
                canvas.drawLine(mScreenWidth / 2, mScreenHeight / 2 - mScreenWidth / 2, mScreenWidth / 2, mScreenHeight / 2 - mScreenWidth / 2 + 30,circlePaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mScreenWidth / 2 - circlePaint.measureText(degree) / 2,
                        mScreenHeight / 2 - mScreenWidth / 2 + 60, circlePaint);
            }
            canvas.rotate(15,mScreenWidth / 2, mScreenHeight / 2 );
        }
        canvas.save();
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mScreenWidth / 2, mScreenHeight / 2);
        canvas.drawLine(0, 0, 100, 100, paintHour);
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        canvas.restore();*/


        /*paint.setColor(Color.YELLOW);
        canvas.drawRect(100,500,600,1000,paint);
        paint.setColor(Color.GREEN);
        canvas.drawText("Hello World", 350,750,paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(540,960,100,paint);*/

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_arrow_top_new);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect rect1 = new Rect(500, 600, 500 + bitmap.getWidth(), 600 + bitmap.getHeight());
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawBitmap(bitmap,rect,rect1,paint);
        RectF rectF = new RectF(100, 100, 800, 600);
        canvas.drawOval(rectF,paint);
        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(500,100);
        path.lineTo(500,500);
        path.lineTo(100,500);
        path.close();
        paint.setTextSize(15);
        paint.setUnderlineText(true);
        canvas.drawPath(path,paint);
        canvas.drawTextOnPath("Hello World!", path, 200,300,paint);
    }
}
