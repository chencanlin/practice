package com.org.ccl.practice.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.org.ccl.practice.R;


public class MainActivity extends Activity {

    private ServiceConnection mServiceConnection;
    private Messenger mMessenger;
    private ServiceConnection mServiceConnection2;
    private IMyAidlInterface mIMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initService();
    }

    private void initService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMessenger = null;
            }
        };
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        mServiceConnection2 = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIMyAidlInterface = null;
            }
        };
        Intent intent1 = new Intent("com.org.ccl.aidl.myservice");
        bindService(intent1, mServiceConnection2,BIND_AUTO_CREATE);
    }

    private void initView() {
        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mMessenger.send(Message.obtain(null, MessageService.MY_MESSAGE, null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    Book name = mIMyAidlInterface.getBook();
                    Toast.makeText(MainActivity.this, name.name, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
            mServiceConnection = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Runtime.getRuntime().exit(0);
//        System.exit(0);
//        ActivityManager systemService = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        systemService.killBackgroundProcesses(getPackageName());
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
