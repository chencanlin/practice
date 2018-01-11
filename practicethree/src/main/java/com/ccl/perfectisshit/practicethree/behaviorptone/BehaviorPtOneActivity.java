package com.ccl.perfectisshit.practicethree.behaviorptone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ccl.perfectisshit.practicethree.R;
import com.ccl.perfectisshit.practicethree.behaviorptone.adapter.RvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2018/1/10.
 */

public class BehaviorPtOneActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RvAdapter mRvAdapter;

    private List<String> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_pt_one);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            data.add(i+ "");
        }
        mRvAdapter.setData(data);
    }

    private void initView() {
        mCoordinatorLayout = findViewById(R.id.cl);
        mAppBarLayout = findViewById(R.id.abl);
        mToolbar = findViewById(R.id.toolbar);
        mSwipeRefreshLayout = findViewById(R.id.srl);
        mRecyclerView = findViewById(R.id.rv);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRvAdapter = new RvAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRvAdapter);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
