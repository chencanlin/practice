package com.org.ccl.practicetwo.drawablecustom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ccl on 2017/9/20.
 */

public class CustomView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint;
    private Drawable mBackground;

    private boolean isInit = false;
    private Rect mRect;
    private PointF mPoint;
    private int time = 60;
    private boolean isStart = false;
    private int sweepAngle = 0;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                sweepAngle -= 6;
                postInvalidate();
                if (time == 0) {
                    timer.cancel();
                }
            }
        };
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStart) {
                    timer.schedule(timerTask, 1000, 1000);
                    isStart = true;
                }
            }
        });
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
        mPaint.setStrokeWidth(10);
//        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
//        mPaint.setMaskFilter(blurMaskFilter);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    @Override
    public void setBackground(Drawable background) {
        mBackground = background;
        super.setBackground(background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Bitmap shadowBitmap = bitmap.extractAlpha();
        canvas.drawBitmap(shadowBitmap, 100, 100, mPaint);
        canvas.drawBitmap(bitmap,100,100,null);*/
        /*Rect rect = new Rect(0,0,400,400);
        canvas.drawRect(rect,mPaint);*/
        if (!isInit) {
            mPoint = new PointF();
            mPoint.x = 350;
            mPoint.y = 350;
            mRect = new Rect();
            drawText(canvas, "开始");
            isInit = true;
        }
        if (isStart) {
            drawText(canvas, String.valueOf(time));
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
        canvas.drawCircle(mPoint.x, mPoint.y, 150, mPaint);
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        canvas.drawArc(new RectF(200, 200, 500, 500), -90, sweepAngle, false, mPaint);
    }

    private void drawText(Canvas canvas, String text) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(45);
        mPaint.setColor(getResources().getColor(android.R.color.black));
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        canvas.drawText(text, mPoint.x - mRect.width() / 2, mPoint.y + mRect.height() / 2, mPaint);
    }
}
