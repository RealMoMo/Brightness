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
     * @return 亮度变化设置成功
     */
    void changeSuccessed();

    /**
     *
     * @return 亮度变化设置失败
     */
    void changeFailed();

    /**
     *
     * @return 亮度变化更新
     */
    void updatingBrightnessValue(int currentBrightness);

    /**
     *
     * @return 增加亮度变化设置成功
     */
    void addBrightnessSuccessed();

    /**
     *
     * @return 增加亮度变化设置失败
     */
    void addBrightnessFailed();

    /**
     *
     * @return 减少亮度变化设置成功
     */
    void minusBrightnessSuccessed();

    /**
     *
     * @return 减少亮度变化设置成功
     */
    void minusBrightnessFailed();


}
