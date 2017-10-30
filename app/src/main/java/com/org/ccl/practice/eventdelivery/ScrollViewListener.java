package com.org.ccl.practice.eventdelivery;

import android.widget.ScrollView;

/**
 * Created by ccl on 2016/4/27.
 */
public interface ScrollViewListener {
    void ScrollChangeScrollView(ScrollView context, int scrollX, int scrollY, boolean oldx, boolean oldy);
}
