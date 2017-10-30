package com.org.ccl.practicetwo.popupwindowtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/12.
 */

public class PopupWindowTestActivity extends Activity {
    private TextView mTvPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindowtest);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mTvPopupWindow = findViewById(R.id.tv_popup_window);

        mTvPopupWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, getScreenSize(1)- 200);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), Bitmap.createBitmap(getScreenSize(0),getScreenSize(1),Bitmap.Config.ARGB_4444)));

        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        popupWindow.setContentView(inflate);
        int [] location = new int[2];
        mTvPopupWindow.getLocationOnScreen(location);
        popupWindow.setHeight(getScreenSize(1)- location[1]- mTvPopupWindow.getMeasuredHeight());
//        popupWindow.showAsDropDown(mTvPopupWindow);
//        popupWindow.showAtLocation(mTvPopupWindow, Gravity.NO_GRAVITY, 0,500);
    }

    private int getScreenSize(int type){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return type == 0 ? displayMetrics.widthPixels:displayMetrics.heightPixels;
    }
}
