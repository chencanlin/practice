package com.org.ccl.practice.eventdelivery;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.org.ccl.practice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeliveryActivity extends Activity {

    private LinearLayout mLl;
    private MyTextView mIv;
    private ListView mLv;
    private List<String> data = new ArrayList<>();
    private Map<Integer, View> mConvertViewData = new HashMap<>();
    private Map<Integer, Boolean> openData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mLl = ((LinearLayout) findViewById(R.id.ll));
        mIv = ((MyTextView) findViewById(R.id.iv));
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mIv.smoothScrollTo(0,300);

                int[] ints = {0,1};
                Rect rect = new Rect();
                mIv.getLocalVisibleRect(rect);
                mIv.getLocationOnScreen(ints);
            }
        });
        mLv = ((ListView) findViewById(R.id.lv));
        for (int i = 0; i < 100; i++) {
            data.add(i+"");
        }
        mLv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data == null ? 0: data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder;
            if(convertView ==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listview_item, parent, false);
                viewHolder = new MyViewHolder(convertView);
            }else{
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            mConvertViewData.put(position,convertView);
            openData.put(position, false);
            ((MyDrawerLayout) convertView).setOnSliderListener(position,new MySliderListener() {
                @Override
                public void isOpenAnother(int position, boolean flag) {
                    openData.put(position,flag);
                }

                @Override
                public boolean needCloseOther() {
                    Set<Integer> integers = openData.keySet();
                    for (Integer item:integers) {
                        if(openData.get(item) && item != position){
                            ((MyDrawerLayout) mConvertViewData.get(item)).closeItem();
                        }
                    }
                    return openData.containsValue(true) && !openData.get(position);
                }
            });
            viewHolder.mRightOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DeliveryActivity.this, "置顶", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.mRightTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DeliveryActivity.this, "删除", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

    private class MyViewHolder{
        private final TextView mRightOne;
        private final TextView mRightTwo;

        public MyViewHolder(View convertView){
            mRightOne = ((TextView) convertView.findViewById(R.id.tv_right_one));
            mRightTwo = ((TextView) convertView.findViewById(R.id.tv_right_two));
            convertView.setTag(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("DeliveryActivity------------------------dispatchTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("DeliveryActivity------------------------dispatchTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("DeliveryActivity------------------------dispatchTouchEvent-------------Up");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("DeliveryActivity------------------------onTouchEvent-------------Down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("DeliveryActivity------------------------onTouchEvent-------------Move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("DeliveryActivity------------------------onTouchEvent-------------Up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
