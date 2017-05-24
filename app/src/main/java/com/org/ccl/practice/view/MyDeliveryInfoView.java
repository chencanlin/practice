package com.org.ccl.practice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.org.ccl.practice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2017/5/24.
 */

public class MyDeliveryInfoView extends View {

    private static final int mR = 35;
    private static final int lineHeight = 120;
    private static final int mDashPadding = 6;
    private static final int dashHeight = 15;

    private Bitmap mIconCheck;
    private Bitmap mIconExclamationMark;
    private Bitmap mIconCircle;
    private Paint mCompletePaint;
    private Paint mUnCompleted;
    private List<MyDeliveryInfo> mData = new ArrayList<>();
    private int mWidth;
    private int mHeight;
    private Rect mCheckRect;
    private Rect mExclamationMarkRect;
    private Rect mCircleRect;
    private Paint mIconPaint;

    public MyDeliveryInfoView(Context context) {
        this(context, null);
    }

    public MyDeliveryInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDeliveryInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mIconCheck = BitmapFactory.decodeResource(getResources(), R.drawable.icon_check);
        mIconExclamationMark = BitmapFactory.decodeResource(getResources(), R.drawable.icon_exclamation_mark);
        mIconCircle = BitmapFactory.decodeResource(getResources(), R.drawable.icon_circle);
        mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIconPaint.setStyle(Paint.Style.FILL);
        mCompletePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCompletePaint.setColor(Color.WHITE);
        mCompletePaint.setStrokeWidth(8);
        mCompletePaint.setTextSize(15);
        mUnCompleted = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnCompleted.setColor(Color.WHITE);
        mUnCompleted.setStrokeWidth(4);

        mCheckRect = new Rect(0, 0, mIconCheck.getWidth(), mIconCheck.getHeight());
        mExclamationMarkRect = new Rect(0, 0, mIconExclamationMark.getWidth(), mIconExclamationMark.getHeight());
        mCircleRect = new Rect(0, 0, mIconCircle.getWidth(), mIconCircle.getHeight());
    }

    public void setData(List<MyDeliveryInfo> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        mData = data;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.size() == 0) {
            return;
        }
        int centerIndex = mData.size() % 2 != 0 ? (int) Math.ceil(mData.size() / 2.0f) - 1 : mData.size() / 2 - 1;
        for (int i = 0; i < mData.size(); i++) {
            MyDeliveryInfo item = mData.get(i);
            int bitmapCenter = mData.size() % 2 != 0 ? mWidth / 2 - (centerIndex - i) * (lineHeight + mR * 2) : (int) (mWidth / 2 - (centerIndex - i + 0.5) * (lineHeight + mR * 2));
            canvas.drawBitmap(item.getStatus() == MyDeliveryInfo.Delivery.COMPLETED ? mIconCheck :
                    item.getStatus() == MyDeliveryInfo.Delivery.COMPLETING ? mIconExclamationMark :
                            mIconCircle, item.getStatus() == MyDeliveryInfo.Delivery.COMPLETED ? mCheckRect :
                    item.getStatus() == MyDeliveryInfo.Delivery.COMPLETING ? mExclamationMarkRect :
                            mCircleRect, new Rect(bitmapCenter - mR, mHeight / 2 - mR, bitmapCenter + mR, mHeight / 2 + mR), mIconPaint);
            float v = mCompletePaint.measureText(item.getName());
            canvas.drawText(item.getName(), bitmapCenter - v / 2, mHeight / 2 + mR * 2, mCompletePaint);
            if (i < mData.size() - 1) {
                int lineLeft = mData.size() % 2 != 0 ? (int) (mWidth / 2 - (centerIndex - i) * lineHeight - (centerIndex - i - 0.5) * mR * 2) : (int) (mWidth / 2 - (centerIndex - i + 0.5) * lineHeight - (centerIndex - i) * mR * 2);
                if (mData.get(i + 1).getStatus() == MyDeliveryInfo.Delivery.UNCOMPLETED) {
                    for (int j = 0; j < 6; j++) {
                        canvas.drawLine(lineLeft + (dashHeight + mDashPadding) * j, mHeight / 2 - 2, lineLeft + dashHeight * (j + 1) + mDashPadding * j, mHeight / 2 - 2, mUnCompleted);
                    }
                } else {
                    canvas.drawLine(lineLeft, mHeight / 2 - 2, lineLeft + lineHeight, mHeight / 2 - 2, mCompletePaint);
                }
            }
        }
    }
}
