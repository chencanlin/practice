package com.org.ccl.practicetwo.colorgradient;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.org.ccl.practicetwo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ccl on 2017/11/10.
 */

public class TestColorGradientActivity extends Activity {

    private int[] mInts;
    private ImageAutoRollLayout mImageAutoRollLayout;
    private ColorCustomView mColorCustomView;

    private List<MyBannerItem> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_color_gradient);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
//        mInts = new int[]{0xffff0000, 0xffff7f27, 0xffd5e61d, 0xff39e61d, 0xff1ddfe6, 0xff1d64e6};
        Random random = new Random();
        mInts = new int[]{Color.argb
                (255,random.nextInt(256),random.nextInt(256)
                        ,random.nextInt(256)),
                Color.argb
                        (255,random.nextInt(256),random.nextInt(256)
                                ,random.nextInt(256))};
        datas.add(new MyBannerItem("https://res-secure.tpt.zuihuibao.com/image1505957801.png", "one"));
        datas.add(new MyBannerItem("https://res-secure.tpt.zuihuibao.com/image1505957801.png", "two"));
        datas.add(new MyBannerItem("https://res-secure.tpt.zuihuibao.com/image1505957801.png", "three"));
    }

    private void initView() {
        mImageAutoRollLayout = findViewById(R.id.iarl);
        mColorCustomView = findViewById(R.id.ccv);
        mColorCustomView.setColors(mInts);

        mImageAutoRollLayout.setView(mColorCustomView);
        mImageAutoRollLayout.setData(datas, 0);
        mImageAutoRollLayout.setAutoRoll(true);

    }

    private class MyBannerItem implements ImageAutoRollLayout.ImageAutoRollItem {
        private String imageUrl;
        private String title;

        public MyBannerItem(String imageUrl, String title) {
            this.imageUrl = imageUrl;
            this.title = title;
        }

        @Override
        public String getImageUrl() {
            return imageUrl;
        }

        @Override
        public String getTitle() {
            return title;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
