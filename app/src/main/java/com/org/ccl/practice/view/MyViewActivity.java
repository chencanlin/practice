package com.org.ccl.practice.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.org.ccl.practice.R;

import java.util.ArrayList;

/**
 * Created by ccl on 2017/5/15.
 */

public class MyViewActivity extends Activity {
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
        MyDeliveryInfoView two = (MyDeliveryInfoView) findViewById(R.id.mdiv_two);
        MyDeliveryInfoView three = (MyDeliveryInfoView) findViewById(R.id.mdiv_three);
        MyDeliveryInfoView four = (MyDeliveryInfoView) findViewById(R.id.mdiv_four);
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
        three.setData(threeData);
        four.setData(fourData);
    }
}
