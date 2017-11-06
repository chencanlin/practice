package com.org.ccl.practicetwo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.v4.util.SparseArrayCompat;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.bean.Hero;

/**
 * Created by ccl on 2017/11/2.
 */

public class ImageUtils {


    private static SparseArrayCompat<Hero> heroArray = new SparseArrayCompat<>();


    public static Bitmap gaussianBlur(Context context, @IntRange(from = 1, to = 25) int radius, Bitmap original) {
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(original);
        return original;
    }


    static {
        heroArray.put(0, new Hero(R.mipmap.bh, "赏金猎人"));
        heroArray.put(1, new Hero(R.mipmap.dk, "龙骑士"));
        heroArray.put(2, new Hero(R.mipmap.jugg, "剑圣"));
        heroArray.put(3, new Hero(R.mipmap.yaphet, "影魔"));
        heroArray.put(4, new Hero(R.mipmap.airenjujishou, "矮人狙击手"));
        heroArray.put(5, new Hero(R.mipmap.anyemowang, "暗夜魔王"));
        heroArray.put(6, new Hero(R.mipmap.chuanzhang, "船长"));
        heroArray.put(7, new Hero(R.mipmap.diyulingzhu, "地狱领主"));
        heroArray.put(8, new Hero(R.mipmap.fengbaozhiling, "风暴之灵"));
        heroArray.put(9, new Hero(R.mipmap.handishenniu, "撼地神牛"));
        heroArray.put(10, new Hero(R.mipmap.heianyouxia, "黑暗游侠"));
        heroArray.put(11, new Hero(R.mipmap.kaer, "卡尔"));
        heroArray.put(12, new Hero(R.mipmap.lanmao, "蓝猫"));
        heroArray.put(13, new Hero(R.mipmap.linghunshouwei, "灵魂守卫"));
        heroArray.put(14, new Hero(R.mipmap.liulangjianke, "流浪剑客"));
        heroArray.put(15, new Hero(R.mipmap.shefanvyao, "蛇发女妖"));
        heroArray.put(16, new Hero(R.mipmap.shouwang, "兽王"));
        heroArray.put(17, new Hero(R.mipmap.shuijingshinv, "水晶室女"));
        heroArray.put(18, new Hero(R.mipmap.xiongmaojiuxian, "熊猫酒仙"));
        heroArray.put(19, new Hero(R.mipmap.baihu, "白虎"));
    }

    public static SparseArrayCompat<Hero> getHeroArray(){
        return heroArray;
    }
}
