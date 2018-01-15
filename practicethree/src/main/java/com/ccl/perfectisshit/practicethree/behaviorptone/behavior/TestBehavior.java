package com.ccl.perfectisshit.practicethree.behaviorptone.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ccl.perfectisshit.practicethree.R;

/**
 * Created by ccl on 2018/1/12.
 */








public class TestBehavior extends CoordinatorLayout.Behavior<View> {
    public TestBehavior() {
    }

    public TestBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.dependency;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY() - child.getHeight());
        child.setX( dependency.getX());
        return true;
    }
}
