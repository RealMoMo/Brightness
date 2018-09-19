package com.hht.brightness.strategy.change;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:45
 * @describe 亮度变化
 */
public interface IBrightnessChange {
    /**
     * 改变亮度
     * @param startBrightness  开始亮度
     * @param targetBrightness 目标亮度
     */
    void changeBrightness(int startBrightness, int targetBrightness);

    /**
     * 停止亮度变化
     */
    void stopChangeBrightness();

    /**
     * 回收资源
     */
    void recycleVar();


    /**
     * 亮度变化策略类型
     */
    @IntDef({Strategy.IMMEDIATELY, Strategy.GRADIENT, Strategy.CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface Strategy {
        //即时设置到目标亮度
        int IMMEDIATELY = 0;
        //渐变设置到目标亮度
        int GRADIENT = 1;
        //自定义
        int CUSTOM = 2;
    }


}
