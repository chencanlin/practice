package com.ccl.perfectisshit.practicethree;

import android.app.Application;

import com.bumptech.glide.GlideBuilder;

/**
 * Created by ccl on 2018/2/8.
 */

public class PracticeThreeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        initGlide();
    }

    private void initGlide() {
        GlideBuilder glideBuilder = new GlideBuilder();
    }
}
