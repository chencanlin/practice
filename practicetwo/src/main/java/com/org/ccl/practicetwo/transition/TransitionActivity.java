package com.org.ccl.practicetwo.transition;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.org.ccl.practicetwo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccl on 2017/9/25.
 */

public class TransitionActivity extends Activity implements View.OnClickListener {


    private FrameLayout mContainer;

    private static final int [] LAYOUT_ID = {R.layout.layout_transition_first, R.layout.layout_transition_second,R.layout.layout_transition_third};

    private List<Scene>sceneList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        for (int item :LAYOUT_ID) {
            Scene sceneForLayout = Scene.getSceneForLayout(mContainer, item, this);
            sceneList.add(sceneForLayout);
        }
    }

    private void initView() {
        mContainer = findViewById(R.id.fl_container);

        findViewById(R.id.tv_first_tab).setOnClickListener(this);
        findViewById(R.id.tv_second_tab).setOnClickListener(this);
        findViewById(R.id.tv_third_tab).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        performTransitionToScene(sceneList.get(Integer.parseInt((String) view.getTag())));
    }

    private void performTransitionToScene(Scene scene) {
        Fade fadeOut = new Fade(Fade.OUT);
        ChangeBounds changeBounds = new ChangeBounds();
        Fade fadeIn = new Fade(Fade.IN);

        fadeOut.setDuration(1000);
        changeBounds.setDuration(1000);
        fadeIn.setDuration(1000);

        changeBounds.setInterpolator(new BounceInterpolator());

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);

        TransitionManager.go(scene, transitionSet);
    }
}
