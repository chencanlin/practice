package com.ccl.perfectisshit.practicethree.eventbusdemo;

import com.ccl.perfectisshit.practicethree.eventbusdemo.interf.IMessageNotify;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ccl on 2018/1/19.
 */

public class EventBusUtil extends EventBus {




    public static EventBusUtil getInstance() {
        if (mEventBusUtil == null) {
            synchronized (EventBus.class) {
                if (mEventBusUtil == null) {
                    mEventBusUtil = new EventBusUtil();
                }
            }
        }
        return mEventBusUtil;
    }

    private static volatile EventBusUtil mEventBusUtil;
    private EventBusUtil(){}

    // 网络请求回调函数 （为了规范方法名）方法必须添加EventBus注解
    public void setOnMessageNotify(IMessageNotify iMessageNotify){}
}
