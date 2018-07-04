package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.CustomMessageBean;

/**
 * Created by ccl on 2018/1/16.
 */

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";
    private EventBusUtil mEventBusUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, EventBusTestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtil.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusUtil.unRegister(this);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onNotifyStickyMsgMainThread(CustomMessageBean customMessage) {
        super.onNotifyStickyMsgMainThread(customMessage);
        Log.d(TAG, "onNotifyStickyMsgMainThread: " + customMessage.getHashCode() + "---" +customMessage.getMessage() + "---");
        Toast.makeText(this, "onNotifyStickyMsgMainThread: " + customMessage.getHashCode() + "---" +customMessage.getMessage() + "---", Toast.LENGTH_SHORT).show();
    }
}
