package com.org.ccl.practicetwo.materialdesign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/13.
 */

public class MaterialDesignSecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design_second);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {

    }
}
