package com.org.ccl.practice.eventdelivery;

/**
 * Created by ccl on 2017/5/19.
 */

public interface MySliderListener {

    void isOpenAnother(int position, boolean flag);

    boolean needCloseOther();
}
