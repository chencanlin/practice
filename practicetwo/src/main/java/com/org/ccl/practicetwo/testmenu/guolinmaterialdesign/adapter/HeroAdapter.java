package com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.bean.Hero;
import com.org.ccl.practicetwo.util.DensityUtils;

import java.util.Random;

/**
 * Created by ccl on 2017/11/2.
 */


public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {


    private final Context mContext;
    private final SparseArrayCompat<Hero> mHeroArray;
    private final Random mRandom;
    private final int mScreenWidth;
    private OnItemClickListener mListener;

    public HeroAdapter(Context context, SparseArrayCompat<Hero> heroArray) {
        mContext = context;
        mHeroArray = heroArray;
        mRandom = new Random();
        mScreenWidth = getScreenSize(mContext, 0);
    }

    private int getScreenSize(Context context, int type){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if(type == 0){
            return displayMetrics.widthPixels;
        }else{
            return displayMetrics.heightPixels;
        }
    }

    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_item, parent, false);
        return new HeroViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HeroViewHolder holder, final int position) {
        Hero hero = mHeroArray.get(position);
        holder.mIvHeroImage.setImageResource(hero.getImageID());
        holder.mTvHeroName.setText(hero.getName());
//        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
//        holder.mRoot.setBackgroundColor(color);
        ViewGroup.LayoutParams layoutParams = holder.mIvHeroImage.getLayoutParams();
        layoutParams.width = (mScreenWidth- DensityUtils.dp2px(mContext, 20))/2;
        layoutParams.height = layoutParams.width * 570 /522;
        holder.mIvHeroImage.setLayoutParams(layoutParams);
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    v.setTag(position);
                    mListener.onItemClick(v);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mHeroArray == null ? 0 : mHeroArray.size();
    }


    public class HeroViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mIvHeroImage;
        private final TextView mTvHeroName;
        private final View mRoot;

        public HeroViewHolder(View itemView) {
            super(itemView);
            mIvHeroImage = itemView.findViewById(R.id.iv_hero_image);
            mTvHeroName = itemView.findViewById(R.id.tv_hero_name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }
}
