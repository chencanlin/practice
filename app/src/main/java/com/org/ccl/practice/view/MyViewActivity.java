package com.org.ccl.practice.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.org.ccl.practice.R;
import com.org.ccl.practice.eventdelivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2017/5/15.
 */

public class MyViewActivity extends Activity {


    private static final String TAG = "MyViewActivity";
    private RelativeLayout mRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentView = ((FrameLayout) findViewById(android.R.id.content));
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_myviewactivity, contentView, false);
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
        contentView.addView(inflate,layoutParams);
//        setContentView(R.layout.activity_myviewactivity);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        MyView mv = (MyView) findViewById(R.id.mv);
        RelativeLayout.LayoutParams mvLayoutParams = (RelativeLayout.LayoutParams) mv.getLayoutParams();
        mvLayoutParams.height = defaultDisplay.getHeight()/2;
        mv.setLayoutParams(mvLayoutParams);

        MyDeliveryInfoView one = (MyDeliveryInfoView) findViewById(R.id.mdiv_one);
        final MyDeliveryInfoView two = (MyDeliveryInfoView) findViewById(R.id.mdiv_two);
        MyDeliveryInfoView three = (MyDeliveryInfoView) findViewById(R.id.mdiv_three);
        final MyDeliveryInfoView four = (MyDeliveryInfoView) findViewById(R.id.mdiv_four);
        ArrayList<MyDeliveryInfo> oneData = new ArrayList<>();
        ArrayList<MyDeliveryInfo> twoData = new ArrayList<>();
        ArrayList<MyDeliveryInfo> threeData = new ArrayList<>();
        ArrayList<MyDeliveryInfo> fourData = new ArrayList<>();
        oneData.add(new MyDeliveryInfo("接单",MyDeliveryInfo.Delivery.COMPLETED));
        oneData.add(new MyDeliveryInfo("打包",MyDeliveryInfo.Delivery.COMPLETING));
        oneData.add(new MyDeliveryInfo("出发",MyDeliveryInfo.Delivery.UNCOMPLETED));

        twoData.add(new MyDeliveryInfo("接单",MyDeliveryInfo.Delivery.COMPLETED));
        twoData.add(new MyDeliveryInfo("打包",MyDeliveryInfo.Delivery.COMPLETED));
        twoData.add(new MyDeliveryInfo("出发",MyDeliveryInfo.Delivery.COMPLETING));
        twoData.add(new MyDeliveryInfo("送单",MyDeliveryInfo.Delivery.UNCOMPLETED));

        threeData.add(new MyDeliveryInfo("接单",MyDeliveryInfo.Delivery.COMPLETED));
        threeData.add(new MyDeliveryInfo("打包",MyDeliveryInfo.Delivery.COMPLETED));
        threeData.add(new MyDeliveryInfo("出发",MyDeliveryInfo.Delivery.COMPLETED));
        threeData.add(new MyDeliveryInfo("送单",MyDeliveryInfo.Delivery.COMPLETING));
        threeData.add(new MyDeliveryInfo("完成",MyDeliveryInfo.Delivery.UNCOMPLETED));

        fourData.add(new MyDeliveryInfo("接单",MyDeliveryInfo.Delivery.COMPLETED));
        fourData.add(new MyDeliveryInfo("打包",MyDeliveryInfo.Delivery.COMPLETED));
        fourData.add(new MyDeliveryInfo("出发",MyDeliveryInfo.Delivery.COMPLETED));
        fourData.add(new MyDeliveryInfo("送单",MyDeliveryInfo.Delivery.COMPLETED));
        fourData.add(new MyDeliveryInfo("完成",MyDeliveryInfo.Delivery.COMPLETING));
        fourData.add(new MyDeliveryInfo("支付",MyDeliveryInfo.Delivery.UNCOMPLETED));
        one.setData(oneData);
        two.setData(twoData);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyViewActivity.this, DeliveryActivity.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MyViewActivity.this,two,"container").toBundle());
            }
        });
        three.setData(threeData);
        four.setData(fourData);
        four.animate().x(100).start();
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startService(new Intent(MyViewActivity.this,MyService.class));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("ccl://com.org.ccl?name=chencanlin"));
                startActivity(intent);*/
                Uri uri = Uri.parse("content://com.org.ccl.practicetwo.contentprovider/table1");
                Cursor query = getContentResolver().query(uri, null, null, null, null);
                if(query.moveToFirst()){
                    String name = query.getString(query.getColumnIndex("name"));
                    String author = query.getString(query.getColumnIndex("author"));
                    String price = query.getString(query.getColumnIndex("price"));
                    String pages = query.getString(query.getColumnIndex("pages"));
                    Log.i(TAG, "name:"+name +"author:"+author+ "price:" +price +"pages:"+pages);
                }
            }
        });
        mRl = ((RelativeLayout) findViewById(R.id.rl));
//        final ImageView viewById = (ImageView) findViewById(R.id.iv_image);
//        final MyScreenShotLayout viewById1 = (MyScreenShotLayout) findViewById(R.id.rl_image);
//        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewById1.screenShot(true);
//                View decorView = getWindow().getDecorView();
//                decorView.setDrawingCacheEnabled(true);
//                decorView.buildDrawingCache();
//                Bitmap drawingCache = decorView.getDrawingCache();
//                Rect rect = new Rect();
//                getWindow().getDecorView().getWindowVisibleDisplayFrame( rect);
//                Bitmap bitmap = Bitmap.createBitmap(drawingCache, (int) viewById1.lightAreaRect.left, (int) viewById1.lightAreaRect.top+1+rect.top, (int) viewById1.lightAreaRect.width(), (int) viewById1.lightAreaRect.height());
//                viewById1.setVisibility(View.GONE);
//                viewById.setImageBitmap(bitmap);
//            }
//        });
//        findViewById(R.id.tv_click_two).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewById1.mImageView.reduction();
//            }
//        });
//        findViewById(R.id.tv_click_three).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewById1.mImageView.rotate();
//            }
//        });

        ImageAutoRollLayout viewById = (ImageAutoRollLayout) findViewById(R.id.iarl);
        List<MyItem> data = new ArrayList<>();
        for (int i = 0;i<3;i++){
            data.add(new MyItem());
        }
        viewById.setData(data,R.drawable.main_price_banner_default_bg);

        SharedPreferences practice_shared_preference = getSharedPreferences("practice_shared_preference", MODE_PRIVATE);
        SharedPreferences.Editor edit = practice_shared_preference.edit();
        edit.putString("practice_test", "practice");
        edit.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
