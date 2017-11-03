package com.org.ccl.practicetwo.downloaddemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.downloaddemo.DownloadActivity;
import com.org.ccl.practicetwo.downloaddemo.DownloadTask;
import com.org.ccl.practicetwo.downloaddemo.interf.DownloadListener;

import java.io.File;

/**
 * Created by ccl on 2017/10/27.
 */

public class DownloadService extends Service {

    private static final int REQUEST_CODE_CLICK_NOTIFICATION = 111;
    private static final int NOTIFICATION_ID = 1;

    private DownloadTask mDownloadTask;
    private String downloadUrl;
    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            NotificationManager notificationManager = getNotificationManager();
            notificationManager.notify(NOTIFICATION_ID, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            // 下载成功时关闭前台通知，并创建一个下载成功通知
            stopForeground(true);
            getNotificationManager().notify(NOTIFICATION_ID, getNotification("Download Success", 100));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            // 下载失败时关闭前台通知，并创建一个下载失败通知
            stopForeground(true);
            getNotificationManager().notify(NOTIFICATION_ID, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_CLICK_NOTIFICATION, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent);
        if (progress >= 0) {
            builder.setContentText(progress + "%")
                    .setProgress(100, progress, false);
        }
        return builder.build();
    }

    private NotificationManager getNotificationManager() {
        return ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
    }

    public class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloadUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(downloadUrl);
                startForeground(NOTIFICATION_ID, getNotification("Downloading...", 0));
//                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pausedDownload();
            }
        }

        public void canceledDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.canceledDownload();
            } else {
                // 取消下载时需将文件删除，并关闭通知
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory, fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(NOTIFICATION_ID);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private DownloadBinder mDownloadBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }
}
