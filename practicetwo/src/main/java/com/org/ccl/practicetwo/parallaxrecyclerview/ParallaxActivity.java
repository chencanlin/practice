package com.org.ccl.practicetwo.parallaxrecyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.org.ccl.practicetwo.R;

import java.util.Random;

/**
 * Created by ccl on 2017/9/22.
 */

public class ParallaxActivity extends Activity {


    private ParallaxRecyclerView mRv;

    private SparseArrayCompat<String> data = new SparseArrayCompat<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        init();
    }

    private void init() {
        initView();
        initData();
        setView();
    }

    private void setView() {

        ParallaxAdapter parallaxAdapter = new ParallaxAdapter(data);
        mRv.setAdapter(parallaxAdapter);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            data.put(i, String.valueOf(i));
        }
    }

    private void initView() {
        mRv = findViewById(R.id.rv);
    }


    private static class ParallaxAdapter extends RecyclerView.Adapter<ParallaxAdapter.ViewHolder>{

        private final SparseArrayCompat<String> mData;
        private final Random mRandom;
        private LayoutInflater mLayoutInflater;

        ParallaxAdapter(SparseArrayCompat<String> data){
            mData = data;
            mRandom = new Random();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(mLayoutInflater == null) {
                mLayoutInflater = LayoutInflater.from(parent.getContext());
            }
            View inflate = mLayoutInflater.inflate(R.layout.layout_parallax_rv_item, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int argb = Color.argb(255, mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
            holder.mCardView.setCardBackgroundColor(argb);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private final CardView mCardView;

            ViewHolder(View itemView) {
                super(itemView);
                mCardView = itemView.findViewById(R.id.card_view);
            }
        }
    }
}


