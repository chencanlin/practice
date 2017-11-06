package com.org.ccl.practicetwo.alarmpractice.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ccl on 2017/11/3.
 */

public class LongRunnableService extends Service {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(LongRunnableService.this, "闹铃响了", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private static final int REQUEST_CODE = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long time = elapsedRealtime + 20*1000;
        Intent startServiceIntent = new Intent(this, LongRunnableService.class);
        PendingIntent service = PendingIntent.getService(this, REQUEST_CODE, startServiceIntent, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, service);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
