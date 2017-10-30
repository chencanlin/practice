package com.org.ccl.practicetwo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ccl on 2017/9/21.
 */

public class EasyBehavior extends CoordinatorLayout.Behavior<View> {

    public EasyBehavior() {
    }

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setX(dependency.getX() +200);
        child.setY(dependency.getY()+200);
        ((TextView) child).setText(child.getX()+"---"+child.getY());
        return true;
    }
}
