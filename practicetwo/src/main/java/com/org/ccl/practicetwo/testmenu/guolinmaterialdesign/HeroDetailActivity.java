package com.org.ccl.practicetwo.testmenu.guolinmaterialdesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.util.ImageUtils;

/**
 * Created by ccl on 2017/11/2.
 */

public class HeroDetailActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_HERO_NAME = "extra_key_hero_name";
    public static final String EXTRA_KEY_HERO_IMAGE_ID = "extra_key_hero_image_id";


    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mHeroImageView;
    private TextView mTvContent;
    private String mHeroName;
    private StringBuilder mContent = new StringBuilder();
    private int mHeroImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mHeroName = intent.getStringExtra(EXTRA_KEY_HERO_NAME);
        mHeroImage = intent.getIntExtra(EXTRA_KEY_HERO_IMAGE_ID, -1);
        generateContent(mHeroName);
    }

    private void generateContent(String heroName) {
        for (int i = 0; i < 500; i++) {
            mContent.append(heroName);
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mHeroImageView = findViewById(R.id.hero_image_view);
        mTvContent = findViewById(R.id.tv_content_detail);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mHeroImageView.setImageResource(mHeroImage);
        mTvContent.setText(mContent);
        mCollapsingToolbarLayout.setTitle(mHeroName);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap drawingCache = decorView.getDrawingCache();
        Bitmap bitmap = ImageUtils.gaussianBlur(this, 25, drawingCache);
        getWindow().setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
