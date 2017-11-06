package com.org.ccl.practicetwo.testmenu.guolinmaterialdesign;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.drawablecustom.RoundRectDrawable;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.bean.Hero;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.transformer.ZoomOutPageTransformer;
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

    private SparseArrayCompat<Hero> heroArray = new SparseArrayCompat<>();
    private Dialog mDialog;

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
        heroArray = ImageUtils.getHeroArray();
    }

    private String generateContent(String heroName) {
        for (int i = 0; i < 500; i++) {
            mContent.append(heroName);
        }
        return mContent.toString();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mHeroImageView = findViewById(R.id.hero_image_view);
        mTvContent = findViewById(R.id.tv_content_detail);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        refreshView();

        findViewById(R.id.floating_action_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflate = LayoutInflater.from(HeroDetailActivity.this).inflate(R.layout.layout_viewpager, null);
                mDialog = new Dialog(HeroDetailActivity.this, R.style.dialog_translucent);
                mDialog.setContentView(inflate);
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.show();
                /*int margin = DensityUtils.dp2px(HeroDetailActivity.this, 150);
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                mDialog.getWindow().setLayout(displayMetrics.widthPixels - margin
                        , displayMetrics.widthPixels * 570 / 522);*/
                ViewPager viewPager = inflate.findViewById(R.id.viewpager);
//                final View coordinatorLayout = inflate.findViewById(R.id.coordinatorlayout);
//                coordinatorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                HeroPagerAdapter heroPagerAdapter = new HeroPagerAdapter(HeroDetailActivity.this);
                viewPager.setAdapter(heroPagerAdapter);
                viewPager.setOffscreenPageLimit(3);
                viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        /*Bitmap bitmap = ImageUtils.gaussianBlur(HeroDetailActivity.this, 25,
                                BitmapFactory.decodeResource(getResources(),
                                        heroImageIds.get(position)));
                        coordinatorLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));*/
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                for (int i = 0; i < heroArray.size(); i++) {
                    if (heroArray.get(i).getImageID() == mHeroImage) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    private void refreshView() {
        mHeroImageView.setImageResource(mHeroImage);
        mTvContent.setText(mContent);
        mCollapsingToolbarLayout.setTitle(mHeroName);
    }

    private class HeroPagerAdapter extends PagerAdapter {

        private final Context mContext;

        HeroPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return heroArray == null ? 0 : heroArray.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            RoundRectDrawable roundRectDrawable = new RoundRectDrawable
                    (BitmapFactory.decodeResource(getResources(),
                            heroArray.get(position).getImageID()));
            roundRectDrawable.setShowView(imageView);
            imageView.setImageDrawable(roundRectDrawable);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    mHeroImage = heroArray.get(position).getImageID();
                    mHeroName = heroArray.get(position).getName();
                    mContent = new StringBuilder();
                    generateContent(heroArray.get(position).getName());
                    refreshView();
                }
            });
            return imageView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
