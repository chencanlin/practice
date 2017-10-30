package com.org.ccl.practicetwo.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.database.litepal.LitePalTestActivity;

/**
 * Created by ccl on 2017/10/25.
 */

public class NotificationTestActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_notification_test);
        
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sf);
                Intent intent = new Intent(NotificationTestActivity.this, LitePalTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent activity = PendingIntent.getActivity(NotificationTestActivity.this, 0, intent, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(NotificationTestActivity.this)
                        // 设置title
                        .setContentTitle("Notification Test")
                        // 设置小图标
                        .setSmallIcon(R.drawable.home)
                        // 设置大图标
                        .setLargeIcon(largeBitmap)
                        // 设置文本内容
                        .setContentText("This is notification test")
                        // 设置通知点击操作 通过
                        .setContentIntent(activity)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{0L,1000L,1000L,1000L})
                        .setLights(Color.GREEN,2000,500)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.sf)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                int notificationId = (int) (System.currentTimeMillis()/1024);
                notificationManager.notify(notificationId, notification);
            }
        });
    }
}
