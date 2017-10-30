package com.org.ccl.practicetwo.downloaddemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.downloaddemo.service.DownloadService;

/**
 * Created by ccl on 2017/10/27.
 */

public class DownloadActivity extends Activity {

    private DownloadService.DownloadBinder mBinder;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_demo);
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
                mBinder = ((DownloadService.DownloadBinder) service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private void initView() {
        findViewById(R.id.start_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinder == null){
                    return;
                }
                String downloadUrl = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
//                        "http://192.168.10.132:8080/HelloWorld/zuihuibao-tencent--2.5.1-1011.apk";
                mBinder.startDownload(downloadUrl);
            }
        });
        findViewById(R.id.paused_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinder == null){
                    return;
                }
                mBinder.pauseDownload();
            }
        });
        findViewById(R.id.canceled_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinder == null){
                    return;
                }
                mBinder.canceledDownload();
            }
        });
    }
}
