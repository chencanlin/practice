package com.org.ccl.practicetwo.styletest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.MaterialDesignActivity;

import java.io.InputStream;

/**
 * Created by ccl on 2017/11/29.
 */

public class StyleTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_test);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StyleTestActivity.this, MaterialDesignActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AssetManager assets = getResources().getAssets();
        try {
            InputStream open = assets.open("ss.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_MENU:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
