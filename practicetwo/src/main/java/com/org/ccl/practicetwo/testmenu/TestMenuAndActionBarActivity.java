package com.org.ccl.practicetwo.testmenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;

import java.io.File;

/**
 * Created by ccl on 2017/10/18.
 */

public class TestMenuAndActionBarActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("title");
//        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setSubtitle("detail");
        toolbar.setLogo(R.drawable.home);
//        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


//        ((ImageView) findViewById(R.id.iv)).setImageURI(Uri.fromFile(new File("sdcard/QQBrowser/data/sd.png")));
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /*Intent intent = new Intent(TestMenuAndActionBarActivity.this, ToolBarChildActivity.class);
               startActivity(intent);*/
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("image/png");
               intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("sdcard/QQBrowser/data/sd.png")));
               startActivity(intent);
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.test_menu, menu);
        MenuItem item = menu.findItem(R.id.one);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
//        ActionProvider actionProvider = MenuItemCompat.getActionProvider(item);
        MenuItem item1 = menu.findItem(R.id.two);
        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item1);
        actionProvider.setShareIntent(getDefaultIntent());
        MenuItem item2 = menu.findItem(R.id.three);
        MenuItem item3 = menu.findItem(R.id.four);
        MenuItem item4 = menu.findItem(R.id.five);

        return true;
    }


    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.three:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.four:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.five:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
