package com.ccl.perfectisshit.practicethree.behaviorptone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccl.perfectisshit.practicethree.R;

import java.util.List;

/**
 * Created by ccl on 2018/1/10.
 */




public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private Context mContext;
    private List mData;

    public RvAdapter(Context context) {
        mContext = context;
    }

    public RvAdapter(Context context, List data){
        mContext = context;
        mData = data;
    }

    public void setData(List data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rv_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
