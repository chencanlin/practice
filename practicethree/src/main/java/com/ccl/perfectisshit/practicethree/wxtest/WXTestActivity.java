package com.ccl.perfectisshit.practicethree.wxtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.wxtest.constant.WXConstant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ccl on 2018/2/28.
 */

public class WXTestActivity extends AppCompatActivity {

    private IWXAPI mWXAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxtest);
        init();
    }

    private void init() {
        initView();
        initWX();
    }

    private void initWX() {
        mWXAPI = WXAPIFactory.createWXAPI(this, WXConstant.APP_ID, true);
        mWXAPI.registerApp(WXConstant.APP_ID);
    }

    private void initView() {
        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
