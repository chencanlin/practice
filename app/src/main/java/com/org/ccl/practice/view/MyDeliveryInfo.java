package com.org.ccl.practice.view;

/**
 * Created by ccl on 2017/5/24.
 */

public class MyDeliveryInfo {
    private String name;
    private Enum status;

    public MyDeliveryInfo(String name, Enum status) {
        this.name = name;
        this.status = status;
    }

    public enum Delivery {
        COMPLETED,UNCOMPLETED,COMPLETING;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }
}
