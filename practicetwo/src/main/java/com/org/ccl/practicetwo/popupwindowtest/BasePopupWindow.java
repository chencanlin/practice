package com.org.ccl.practicetwo.popupwindowtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * Created by ccl on 2017/9/12.
 */

public class BasePopupWindow extends PopupWindow {
    private static final int KEY_GET_WIDTH = 0;

    private static final int KEY_GET_HEIGHT = 1;

    public BasePopupWindow(Context context) {
        super(context);
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BasePopupWindow() {
    }

    public BasePopupWindow(View contentView) {
        super(contentView);
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        calculateHeight(anchor);
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        calculateHeight(anchor);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        calculateHeight(anchor);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    private void calculateHeight(final View anchor) {
        int screenHeight = getScreenSize(anchor.getContext(), KEY_GET_HEIGHT);
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        final int windowMaxHeight = screenHeight - location[1] - anchor.getMeasuredHeight();
        int height = getHeight();
        if (height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            final View contentView = getContentView();
            contentView.post(new Runnable() {
                @Override
                public void run() {
                    int measuredHeight = contentView.getMeasuredHeight();
                    if (measuredHeight > windowMaxHeight) {
                        setHeight(windowMaxHeight);
                    }
                    update();
                }
            });
        } else {
            if (height == ViewGroup.LayoutParams.MATCH_PARENT || height > windowMaxHeight) {
                setHeight(windowMaxHeight);
            }
        }
    }

    private static int getScreenSize(Context context, int type) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return type == KEY_GET_WIDTH ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }
}
