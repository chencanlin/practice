package com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.bean;

/**
 * Created by ccl on 2017/11/2.
 */

public class Hero {


    private int imageID;
    private String name;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero(int imageID, String name) {
        this.imageID = imageID;
        this.name = name;
    }
}
