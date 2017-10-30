package com.org.ccl.practicetwo.testmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/10/18.
 */

public class ToolBarChildActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar_child);
        init();
    }

    private void init() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setNavigationIcon(R.drawable.back);
        toolBar.setTitle("ToolBarChildren");
        toolBar.setLogo(R.drawable.home);
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.test_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/
}
