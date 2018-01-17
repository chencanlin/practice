package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ccl on 2018/1/16.
 */

public class EventBusTestActivity extends AppCompatActivity {
    private static final String TAG = "EventBusTestActivity";

    private EventBus mEventBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        init();
    }

    private void init() {
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
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
                        EventBus.getDefault().postSticky(new Message(EventBusTestActivity.this.hashCode(), 8, objectSparseArrayCompat));
                    }
                },2000);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleEvent(Message message) {
        Log.d(TAG, "handleEvent: " + toString()+hashCode() + "---" + message.hashCode + "---" + message.messageType + "---" + message.data.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }
}
