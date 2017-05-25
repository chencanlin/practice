package com.org.ccl.practice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by chencanlin on 2017/5/25.
 */

public class MyRoundImageView extends ImageView {

    private Paint paint;

    public MyRoundImageView(Context context) {
        this(context, null);
    }

    public MyRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap, 14);
            int bitMapWidth = b.getWidth();
            int bitMapHeight = b.getHeight();
            int left = bitMapWidth > bitMapHeight ? (bitMapWidth - bitMapHeight) / 2 : 0;
            int top = bitMapWidth > bitMapHeight ? 0 : (bitMapHeight - bitMapWidth) / 2;
            int right = bitMapWidth > bitMapHeight ? left + bitMapHeight : left + bitMapWidth;
            int bottom = bitMapWidth > bitMapHeight ? top + bitMapHeight : top + bitMapWidth;
            final Rect rectSrc = new Rect(left,top,right,bottom);
            final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//            paint.reset();
            paint.setColor(Color.GREEN);
            canvas.drawARGB(0,0,0,0);
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
//        canvas.drawARGB(200, 255, 255, 255);
        paint.setColor(color);
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        canvas.drawCircle(x / 2, y / 2, x > y ? y / 2 : x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }
}
