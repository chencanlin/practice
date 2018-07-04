package com.ccl.perfectisshit.practicethree.eventbusdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.CustomMessageBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ccl on 2018/1/23.
 */

public class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsgMainThread(CustomMessageBean customMessage){
        onNotifyMainThread(customMessage);
    }

    protected void onNotifyMainThread(CustomMessageBean customMessage){

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyMsgMainThread(CustomMessageBean customMessage){
        onNotifyStickyMsgMainThread(customMessage);
    }

    protected void onNotifyStickyMsgMainThread(CustomMessageBean customMessage){

    }

    /**
     * 是否需要注册事件监听（可以在内部）
     * @author ccl
     * @date 2018/1/23 11:38
     */
    protected boolean isRegisterEventBus(){
        return false;
    }
}
