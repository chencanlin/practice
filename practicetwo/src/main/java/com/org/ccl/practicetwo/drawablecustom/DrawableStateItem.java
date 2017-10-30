package com.org.ccl.practicetwo.drawablecustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.org.ccl.practicetwo.R;

/**
 * Created by ccl on 2017/9/19.
 */

public class DrawableStateItem extends RelativeLayout {

    private static final int [] STATE_READED = {
            R.attr.readed
    };

    private boolean mReaded = false;

    public DrawableStateItem(Context context) {
        this(context, null);
    }

    public DrawableStateItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableStateItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableStateItem);
        boolean aBoolean = typedArray.getBoolean(R.styleable.DrawableStateItem_readed, false);
        setReaded(aBoolean);
    }

    public void setReaded(boolean readed){
        if(mReaded != readed) {
            mReaded = readed;
            refreshDrawableState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if(mReaded){
            int[] state = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(state, STATE_READED);
            return state;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
