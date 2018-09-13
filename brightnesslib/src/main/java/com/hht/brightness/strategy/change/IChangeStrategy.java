package com.hht.brightness.strategy.change;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:45
 * @describe
 */
public interface IChangeStrategy {
    /**
     *
     * @param currentBrightness
     * @param targetBrightness
     */
    void changeBrightness(int currentBrightness, int targetBrightness);

    void stopChangeBrightness();

    void recycleVar();


    enum Strategy {

        //即时设置到目标亮度
        IMMEDIATELY,
        //渐变设置到目标亮度
        GRADIENT,
        //自定义
        CUSTOM ,
    }


}
