package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.View;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.CustomMessageBean;

/**
 * Created by ccl on 2018/1/16.
 */

public class EventBusTestActivity extends BaseActivity {
    private static final String TAG = "EventBusTestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        init();
    }

    private void init() {
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SparseArrayCompat<Object> objectSparseArrayCompat = new SparseArrayCompat<>();
                objectSparseArrayCompat.put(0, "hello world");
                findViewById(R.id.tv_send).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CustomMessageBean customMessageBean = new CustomMessageBean();
                        customMessageBean.setHashCode(EventBusTestActivity.this.hashCode());
                        customMessageBean.setMessageCode(180);
                        EventBusUtil.sendStickyEvent(customMessageBean);
                    }
                },2000);
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onNotifyStickyMsgMainThread(CustomMessageBean customMessage) {
        Log.d(TAG, "onNotifyStickyMsgMainThread: " + customMessage.getHashCode() + "---" +customMessage.getMessage() + "---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
