package com.ccl.perfectisshit.practicethree.eventbusdemo.bean;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by ccl on 2018/1/16.
 */

public class Message {

    public int hashCode;
    public int messageType;
    public SparseArrayCompat<Object> data;

    public Message(int hashCode, int messageType, SparseArrayCompat<Object> data) {
        this.hashCode = hashCode;
        messageType = messageType;
        this.data = data;
    }
}
