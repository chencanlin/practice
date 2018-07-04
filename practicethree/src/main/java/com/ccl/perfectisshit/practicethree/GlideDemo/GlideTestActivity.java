package com.ccl.perfectisshit.practicethree.GlideDemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ccl.perfectisshit.practicethree.R;

/**
 * Created by ccl on 2018/2/8.
 */

public class GlideTestActivity extends AppCompatActivity {


    private ImageView mIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mIv = findViewById(R.id.iv);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.sf, options);
        options.inSampleSize = calculateSampleSize(options, 1080, 360);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sf, options);
        /*ViewTarget<ImageView, Drawable> into = GlideApp.with(this)
                .load("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg")
                .into(mIv);*/
//        mIv.setImageBitmap(bitmap);
//        GlideApp.with(this).asBitmap().transition(BitmapTransitionOptions.withCrossFade()).load("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg").into(mIv);
        /*GlideApp.with(this).asBitmap().transition(BitmapTransitionOptions.withCrossFade()).load("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg")
                .into(new BitmapImageViewTarget(mIv));*/
        Bitmap waterMaskCenter = ImageUtil.createWaterMaskCenter(bitmap, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
        mIv.setImageBitmap(waterMaskCenter);
    }

    private int calculateSampleSize(BitmapFactory.Options options, int width, int height) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int sampleSize;
        sampleSize = Math.max(outWidth / width, outHeight / height);
        if (sampleSize < 1) {
            sampleSize = 1;
        }
        return sampleSize;
    }
}
