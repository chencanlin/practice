package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.BaseMessage;
import com.ccl.perfectisshit.practicethree.eventbusdemo.interf.IMessageNotify;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ccl on 2018/1/16.
 */

public class EventBusTestActivity extends AppCompatActivity implements IMessageNotify {
    private static final String TAG = "EventBusTestActivity";
    private EventBusUtil mEventBusUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        init();
    }

    private void init() {
        mEventBusUtil = EventBusUtil.getInstance();
        if(!mEventBusUtil.isRegistered(this)) {
            mEventBusUtil.register(this);
        }
        mEventBusUtil.setOnMessageNotify(this);
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        BitmapFactory bitmapFactory = new BitmapFactory();
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SparseArrayCompat<Object> objectSparseArrayCompat = new SparseArrayCompat<>();
                objectSparseArrayCompat.put(0, "hello world");
                findViewById(R.id.tv_send).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                },2000);
            }
        });
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleEvent(Message message) {
        Log.d(TAG, "handleEvent: " + toString()+hashCode() + "---" + message.hashCode + "---" + message.messageType + "---" + message.data.toString());
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mEventBusUtil.isRegistered(this)) {
            mEventBusUtil.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    @Override
    public void onNotify(BaseMessage message) {
        Log.d(TAG, "handleEvent: " + toString()+hashCode() + "---" + message.hashCode + "---" + message.messageType + "---" + message.data.toString());
    }
}
