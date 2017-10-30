package com.org.ccl.practicetwo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.org.ccl.practicetwo.camera.CameraActivity;
import com.org.ccl.practicetwo.disklrucache.DiskLruCacheDemo;

public class MainActivity extends AppCompatActivity {
//    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        View tvHelloWorld = findViewById(R.id.tv_hello_world);
        tvHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("ccl://com.org.ccl?name=chencanlin"));
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent,123);
            }
        });
        /*ListView listView = (ListView) findViewById(R.id.lv);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i+"");
        }
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,data));
        final CircleRefreshLayout circleRefreshLayout = (CircleRefreshLayout) findViewById(R.id.refresh_layout);
        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circleRefreshLayout.finishRefreshing();
                            }
                        });
                    }
                }).start();
            }
        });*/

        findViewById(R.id.tv_jump_to_disk_lru_cache_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiskLruCacheDemo.class);
                startActivity(intent);
            }
        });
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
            Toast.makeText(this, packageInfo.sharedUserId, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        /*
        * 两个应用之间通过androidmanifest文件中 shareuserid 数据共享测试
        * */
        try {
            Context co = createPackageContext("com.org.ccl.practice", CONTEXT_IGNORE_SECURITY);
            String string = co.getResources().getString(R.string.app_name);
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            SharedPreferences practice_shared_preference = co.getSharedPreferences("practice_shared_preference", MODE_PRIVATE);
            String practice_test = practice_shared_preference.getString("practice_test", "");
            Toast.makeText(co, practice_test, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        * Messenger test
        * */

        Messenger messenger = new Messenger(handler);
        try {
            messenger.send(Message.obtain(handler,10,"nice message"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    Toast.makeText(MainActivity.this, ((String) msg.obj), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK){
            String path = data.getStringExtra("path");
            byte[] bitmaps = data.getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
            ((ImageView) findViewById(R.id.iv)).setImageBitmap(bitmap);
        }
    }
}
