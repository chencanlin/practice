package com.ccl.perfectisshit.practicethree.eventbusdemo;

/**
 * Created by ccl on 2018/1/19.
 */

public interface IFactory {

    <T extends IFactory>T getFactory(Class<T> iFactory);
}
