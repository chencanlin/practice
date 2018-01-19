package com.ccl.perfectisshit.practicethree.eventbusdemo.interf;

import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.BaseMessage;

/**
 * Created by ccl on 2018/1/19.
 */

public interface IMessageNotify {

    void onNotify(BaseMessage message);
}
