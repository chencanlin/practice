package com.ccl.perfectisshit.practicethree.eventbusdemo.bean;

import android.support.v4.util.SparseArrayCompat;

import com.ccl.perfectisshit.practicethree.eventbusdemo.IMessage;

import java.lang.reflect.Constructor;

/**
 * Created by ccl on 2018/1/19.
 */

public class BaseMessage implements IMessage {

    public int hashCode;
    public int messageType;
    public SparseArrayCompat<Object> data;
    public String returnMessage;

    @Override
    public <T extends BaseMessage> T generateMessage(Class<T> iMessage) {
        BaseMessage baseMessage;
        try {
            Constructor<T> declaredConstructor = iMessage.getDeclaredConstructor();
            baseMessage = declaredConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (T) baseMessage;
    }
}
