package com.ccl.perfectisshit.practicethree;

import android.os.Bundle;
import android.util.Log;

import com.ccl.perfectisshit.practicethree.eventbusdemo.BaseActivity;
import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.CustomMessageBean;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onNotifyStickyMsgMainThread(CustomMessageBean customMessage) {
        super.onNotifyStickyMsgMainThread(customMessage);
        Log.d(TAG, "onNotifyStickyMsgMainThread: " + customMessage.getHashCode() + "---" +customMessage.getMessage() + "---");
    }
}
