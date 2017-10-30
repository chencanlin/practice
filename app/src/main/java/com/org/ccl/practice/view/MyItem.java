package com.org.ccl.practice.view;

/**
 * Created by ccl on 2017/6/14.
 */

public class MyItem implements ImageAutoRollLayout.IShowItem {
    @Override
    public String getImageUrl() {
        return "http://7xo7qb.com2.z0.glb.qiniucdn.com/120.png";
    }

    @Override
    public String getTitle() {
        return "title";
    }
}
