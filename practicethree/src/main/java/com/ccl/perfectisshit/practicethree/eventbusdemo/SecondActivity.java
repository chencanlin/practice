package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.eventbusdemo.interf.IMessageNotify;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ccl on 2018/1/16.
 */

public class SecondActivity extends AppCompatActivity implements IMessageNotify {
    private static final String TAG = "SecondActivity";
    private EventBusUtil mEventBusUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        mEventBusUtil = EventBusUtil.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mEventBusUtil.isRegistered(this)){
            mEventBusUtil.register(this);
        }
        mEventBusUtil.setOnMessageNotify(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mEventBusUtil.isRegistered(this)){
            mEventBusUtil.unregister(this);
        }
    }

    private void init() {
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventBusTestActivity.class);
                startActivity(intent);
            }
        });
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Message message) {
        Log.d(TAG, "handleEvent: " + hashCode() + "---" + +message.hashCode + "---" + message.messageType + "---" + message.data.toString());
        Toast.makeText(this, "SecondActivity" + message.messageType, Toast.LENGTH_SHORT).show();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNotify(Message message) {
        Log.d(TAG, "handleEvent: " + hashCode() + "---" + +message.hashCode + "---" + message.messageType + "---" + message.data.toString());
        Toast.makeText(this, "SecondActivity" + message.messageType, Toast.LENGTH_SHORT).show();
    }
}
