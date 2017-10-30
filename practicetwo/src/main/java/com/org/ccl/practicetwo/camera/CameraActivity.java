package com.org.ccl.practicetwo.camera;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/7/19.
 */

public class CameraActivity extends Activity {


    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null == savedInstanceState) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, Camera2BasicFragment.newInstance())
                        .commit();
            }
        } else {
            TakePictureLayout takePictureLayout = new TakePictureLayout(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((FrameLayout) findViewById(R.id.container)).addView(takePictureLayout, layoutParams);
        }
    }
}
