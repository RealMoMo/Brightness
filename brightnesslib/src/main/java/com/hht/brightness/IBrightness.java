package com.hht.brightness;


import com.hht.brightness.i.StatusListener;
import com.hht.brightness.strategy.change.IBrightnessChange;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:18
 * @describe
 */
public interface IBrightness {

    /**
     * 默认护眼亮度
     */
    int PROECT_WRITING_BRIGHTNESS = 20;

    /**
     * 获取亮度值
     * @return 当前亮度值
     */
    int getBrightness();

    /**
     * 设置亮度
     * @param value 亮度值
     */
    void setBrightness(int value);

    /**
     * 设置亮度为护眼书写亮度
     */
    void setProtectWritingBrightness();

//    /**
//     * 更新护眼书写亮度值并设置亮度为该值
//     * @param writingBrightness
//     */
//    void setProtectWritingBrightness(int writingBrightness);

    /**
     *
     * @return 返回当前设置的护眼亮度
     */
    int getProtectWritingBrightness();

//    void stopAndKeepBrightness();
//
//    void stopAndRecoverBrightness();

    /**
     * 强制改变亮度且不带改变亮度策略，直接设置生效(会中断当前亮度改变)
     * @param targetBrightness
     */
    void forceChangeBrightness(int targetBrightness);

    /**
     *
     * @return 返回亮度变化策略 {@link IBrightnessChange.Strategy}
     */
    @IBrightnessChange.Strategy
    int getChangeStrategyType();

    /**
     *
     * @param changeStrategyType 设置亮度变化策略 {@link IBrightnessChange.Strategy}
     */
    void setChangeStrategyType(@IBrightnessChange.Strategy int changeStrategyType);

    /**
     * 设置亮度状态监听。
     * @param changeStatusListener
     */
    void setChangeStatusListener(StatusListener changeStatusListener);


    /**
     * 释放资源，避免内存泄漏。
     */
    void recycleVar();
}
