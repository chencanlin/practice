package com.org.ccl.practicetwo.alarmpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.alarmpractice.service.LongRunnableService;

/**
 * Created by ccl on 2017/11/3.
 */

public class AlarmActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_practice);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_start_alarm_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, LongRunnableService.class);
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
