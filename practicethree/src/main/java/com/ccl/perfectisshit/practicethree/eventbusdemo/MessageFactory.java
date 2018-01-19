package com.ccl.perfectisshit.practicethree.eventbusdemo;

/*
 * Created by ccl on 2018/1/19.
 */

public class MessageFactory implements IFactory{

    @Override
    public <T extends IFactory> T getFactory(Class<T> iFactory) {
        return null;
    }
}
