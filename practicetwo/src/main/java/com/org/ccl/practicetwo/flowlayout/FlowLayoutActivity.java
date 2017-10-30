package com.org.ccl.practicetwo.flowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ccl on 2017/9/5.
 */

public class FlowLayoutActivity extends Activity {
    private List<String> data = new ArrayList<>();
    private String [] textRandomArray = {"Android ", "Welcome ", "Hello World ", "Perfect ", "Hi ", "Come On ", "Good Game "};
    private FlowLayout mFL;
    private TextView mTvNotify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        init();
    }

    private void init() {
        initView();
        initData();
        setView();
    }

    private void setView() {
        mFL.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends FlowLayout.BaseAdapter<String>{
        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public View getView(int position, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(DensityUtils.dp2px(FlowLayoutActivity.this, 5), DensityUtils.dp2px(FlowLayoutActivity.this, 5), DensityUtils.dp2px(FlowLayoutActivity.this, 5), DensityUtils.dp2px(FlowLayoutActivity.this, 5));
            textView.setBackground(getResources().getDrawable(R.drawable.selector_flow_layout_tv_bg));
            textView.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            textView.setTextSize(13);
            textView.setText(data.get(position));
            return textView;
        }
    }

    private void initView() {
        mFL = findViewById(R.id.fl);
        mTvNotify = findViewById(R.id.tv_notify);

        mFL.setOnClickListener(new OnFlowLayoutClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FlowLayoutActivity.this, ((TextView) v).getText().toString()+ "------" + v.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        mTvNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                data.clear();
                for (int i = 0; i < 1000; i++) {
                    int i1 = random.nextInt(textRandomArray.length - 1);
                    data.add(textRandomArray[i1] + i);
                }
                mFL.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int i1 = random.nextInt(textRandomArray.length - 1);
            data.add(textRandomArray[i1] + i);
        }
    }
}
