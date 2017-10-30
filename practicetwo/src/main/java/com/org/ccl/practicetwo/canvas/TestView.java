package com.org.ccl.practicetwo.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.util.DensityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2017/9/7.
 */

public class TestView extends ViewGroup {
    private Paint mPaint;
    private float mX;
    private float mY;
    private float mEndX;
    private float mEndY;
    private Path mPath;
    private List<Path> pathArray = new ArrayList<>();
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        View firstChild = getChildAt(0);
        firstChild.layout(0, getScreenSize(1) - firstChild.getMeasuredHeight(), getScreenSize(0), getScreenSize(1));
    }

    private int getScreenSize(int type) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return type == 0 ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }

    private void init() {
        setWillNotDraw(false);
        TextView textView = new TextView(getContext());
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmap();
            }
        });
        textView.setTextColor(0xffddffcc);
        textView.setTextSize(13);
        textView.setText("save");
        textView.setGravity(Gravity.TOP);
        textView.setPadding(DensityUtils.dp2px(getContext(), 10), DensityUtils.dp2px(getContext(), 10), DensityUtils.dp2px(getContext(), 10), DensityUtils.dp2px(getContext(), 10));
        textView.setBackgroundColor(0xffffffff);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(textView, layoutParams);

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        LinearGradient linearGradient = new LinearGradient(0, 0, 200, 200, 0xff00ff00, 0xff0000ff, Shader.TileMode.MIRROR);
//        mPaint.setShader(linearGradient);
        mCanvas = new Canvas();
        mCanvas.drawColor(getContext().getResources().getColor(android.R.color.holo_orange_light));
    }

    private void saveBitmap() {
        try {
            View decorView = ((Activity) getContext()).getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap drawingCache = decorView.getDrawingCache();
            String path = Environment.getExternalStorageDirectory() + "/bitmap/";
            File pathFile = new File(path);
            if (!pathFile.exists() || pathFile.isFile()) {
                pathFile.mkdirs();
            }
            File file = new File(path, "ss.png");
            if (!file.exists() || file.isDirectory()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.PNG, 100 ,fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                mPath = new Path();
                mPath.moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                mEndX = event.getX();
                mEndY = event.getY();
                mPath.lineTo(mEndX, mEndY);
                pathArray.add(mPath);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                saveBitmap();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(mCanvas);
        mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_red_light));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.background);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mPaint.setColor(getContext().getResources().getColor(android.R.color.white));
        for (Path item : pathArray) {
            canvas.drawPath(item, mPaint);
        }
    }
}
