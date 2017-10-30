package com.org.ccl.practicetwo.drawablecustom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/19.
 */

public class DrawableCustomActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_custom);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.iv);
        RoundRectDrawable roundRectDrawable = new RoundRectDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.image));
        roundRectDrawable.setShowView(imageView);
        imageView.setBackground(roundRectDrawable);

        Uri parse = Uri.parse("ccl://com.org.ccl?name=chencanlin");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(parse);
        startActivity(intent);
    }
}
