package com.ccl.perfectisshit.practicethree.eventbusdemo;

import java.lang.reflect.Constructor;

/*
 * Created by ccl on 2018/1/19.
 */

public class Factory implements IFactory{
    public <T extends IFactory> T getFactory(Class<T> iFactory) {
        IFactory factory;
        try {
            Constructor<? extends IFactory> declaredConstructor = iFactory.getDeclaredConstructor();
            factory = declaredConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (T) factory;
    }
}
