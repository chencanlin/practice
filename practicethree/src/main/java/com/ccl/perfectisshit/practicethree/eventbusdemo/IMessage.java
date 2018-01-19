package com.ccl.perfectisshit.practicethree.eventbusdemo;

import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.BaseMessage;

/**
 * Created by ccl on 2018/1/19.
 */

public interface IMessage {

    <T extends BaseMessage> T generateMessage(Class<T> iMessage);
}
