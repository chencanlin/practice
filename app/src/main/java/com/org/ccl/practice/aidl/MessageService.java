package com.org.ccl.practice.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ccl on 2017/5/15.
 */

public class MessageService extends Service {
    public static final int MY_MESSAGE = 102;
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MY_MESSAGE:
                    Toast.makeText(getApplicationContext(),"delivery message", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MyHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "bind service", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
