package com.ccl.perfectisshit.practicethree.eventbusdemo;

import java.lang.reflect.Constructor;

/*
 * Created by ccl on 2018/1/19.
 */

public class Factory{
    public static <T extends Factory> T getFactory(Class<T> iFactory) {
        Factory factory;
        try {
            Constructor<? extends Factory> declaredConstructor = iFactory.getDeclaredConstructor();
            factory = declaredConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (T) factory;
    }
}
