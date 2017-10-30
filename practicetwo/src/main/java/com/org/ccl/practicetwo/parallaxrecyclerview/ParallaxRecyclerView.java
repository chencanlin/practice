package com.org.ccl.practicetwo.parallaxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ccl on 2017/9/22.
 */

public class ParallaxRecyclerView extends RecyclerView {
    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);

        addItemDecoration(new ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                int lastItemPosition = state.getItemCount() - 1;
                int currentLayoutPosition = parent.getChildLayoutPosition(view);
                if (currentLayoutPosition != lastItemPosition) {
                    outRect.bottom = -dp2Px(getContext(), 5);
                }
            }
        });

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int visibleCount = lastPosition - firstPosition;
                int elevation = 1;
                for (int i = firstPosition; i <= firstPosition + visibleCount; i++) {
                    View view = layoutManager.findViewByPosition(i);
                    if (view != null) {
                        if (view instanceof CardView) {
                            ((CardView) view).setCardElevation(dp2Px(getContext(), elevation));
                            elevation += 5;
                        }
                        float translationY = view.getTranslationY();
                        if (i > firstPosition && translationY != 0) {
                            view.setTranslationY(0);
                        }
                    }
                }

                View firstView = layoutManager.findViewByPosition(firstPosition);
                float firstViewTop = firstView.getTop();
                firstView.setTranslationY(-firstViewTop / 2.0f);
            }
        });
    }

    public int dp2Px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
