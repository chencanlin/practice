package com.org.ccl.practice.eventdelivery;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        Uri data = getIntent().getData();
        if(data != null && "ccl".equalsIgnoreCase(data.getScheme()) && "com.org.ccl".equalsIgnoreCase(data.getHost())){
            String name = data.getQueryParameter("name");
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mLl = ((LinearLayout) findViewById(R.id.ll));
        mIv = ((MyTextView) findViewById(R.id.iv));
//        mIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mIv.smoothScrollTo(0,300);
//
//                int[] ints = {0, 1};
//                Rect rect = new Rect();
//                mIv.getLocalVisibleRect(rect);
//                mIv.getLocationOnScreen(ints);
//            }
//        });
        mLv = ((ListView) findViewById(R.id.lv));
        for (int i = 0; i < 100; i++) {
            data.add(i + "");
        }
        mLv.setAdapter(new MyAdapter());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMyRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchCallBack());
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class MyItemTouchCallBack extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            // 设置能够移动拖拽的方向为向上和向下
            int moveFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 设置能够滑动的方向为向左和向右
            int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(moveFlag, swipeFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            MyRecyclerViewAdapter adapter = (MyRecyclerViewAdapter) recyclerView.getAdapter();
            adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mMyRecyclerViewAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewViewHolder> implements IItemHelper {

        @Override
        public MyRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_item, parent, false);
            return new MyRecyclerViewViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewViewHolder holder, final int position) {
            holder.mTv.setText("Hello World" + data.get(position));
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DeliveryActivity.this, data.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
//            String prev = data.remove(fromPosition);
//            data.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
            notifyItemMoved(fromPosition, toPosition);
//            Collections.swap(data,fromPosition,toPosition);
           /* if (toPosition != 0) {
                if (fromPosition < toPosition)
                    //向下拖动
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(data, i, i + 1);
                    }
                else {
                    //向上拖动
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(data, i, i - 1);
                    }
                }
                mMyRecyclerViewAdapter.notifyItemMoved(fromPosition, toPosition);
            }*/
        }

        @Override
        public void onItemDismiss(int position) {
//            data.remove(position);
            notifyItemRemoved(position);
        }

        class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private final TextView mTv;
            private final View mItemView;

            MyRecyclerViewViewHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                mTv = ((TextView) itemView.findViewById(R.id.tv));
            }
        }

    }

    interface IItemHelper {

        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
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
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listview_item, parent, false);
                viewHolder = new MyViewHolder(convertView);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            mConvertViewData.put(position, convertView);
            openData.put(position, false);
            ((MyDrawerLayout) convertView).setOnSliderListener(position, new MySliderListener() {
                @Override
                public void isOpenAnother(int position, boolean flag) {
                    openData.put(position, flag);
                }

                @Override
                public boolean needCloseOther() {
                    Set<Integer> integers = openData.keySet();
                    for (Integer item : integers) {
                        if (openData.get(item) && item != position) {
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

    private class MyViewHolder {
        private final TextView mRightOne;
        private final TextView mRightTwo;

        public MyViewHolder(View convertView) {
            mRightOne = ((TextView) convertView.findViewById(R.id.tv_right_one));
            mRightTwo = ((TextView) convertView.findViewById(R.id.tv_right_two));
            convertView.setTag(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
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
        switch (event.getAction()) {
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
