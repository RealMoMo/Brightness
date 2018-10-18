package com.hht.brightness.i;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 15:12
 * @describe
 */
public interface ChangeListener {

    void changeStarted();

    /**
     *
     *  亮度变化设置成功
     */
    void changeSuccessed();

    /**
     *
     *  亮度变化设置失败
     */
    void changeFailed();

    /**
     *
     *  亮度变化更新
     *  @param currentBrightness 当前更新到的亮度
     */
    void updatingBrightnessValue(int currentBrightness);

    /**
     *
     *  增加亮度变化设置成功
     */
    void addBrightnessSuccessed();

    /**
     *
     *  增加亮度变化设置失败
     */
    void addBrightnessFailed();

    /**
     *
     *  减少亮度变化设置成功
     */
    void minusBrightnessSuccessed();

    /**
     *
     *  减少亮度变化设置成功
     */
    void minusBrightnessFailed();

    /**
     * 强制改变亮度
     * @param targetBrightness 目标亮度
     */
    void forceChange(int targetBrightness);


}
