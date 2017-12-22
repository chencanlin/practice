package com.org.ccl.practicetwo.colorgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ccl on 2017/11/10.
 */

public class ColorCustomView extends View {
    private static final String TAG = "ColorCustomView";

    private int[] colors;
    private Paint mPaint;

    public ColorCustomView(Context context) {
        this(context, null);
    }

    public ColorCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }


    public void setColors(int[] colors) {
        if (colors == null || colors.length != 2) {
            throw new IllegalArgumentException();
        }
        this.colors = colors;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (colors == null || colors.length != 2) {
            return;
        }
        LinearGradient linearGradient = new LinearGradient(0, 0,
                getMeasuredWidth(), getMeasuredHeight(), colors[0], colors[1], Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }
}
