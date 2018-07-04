package com.ccl.perfectisshit.practicethree.GlideDemo;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by ccl on 2018/2/8.
 */

@GlideExtension
public class MyExtensionClass {


    private MyExtensionClass(){}

    @GlideOption
    public static void scaleFitCenter(RequestOptions options){
        options.fitCenter();
    }
}
