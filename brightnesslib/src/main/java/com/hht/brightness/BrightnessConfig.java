package com.hht.brightness;

import com.hht.brightness.impl.BaseBrightnessImpl;
import com.hht.brightness.strategy.change.BaseBrightnessChangeImpl;
import com.hht.brightness.strategy.change.IBrightnessChange;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/11 11:44
 * @describe
 * {@link BaseBrightnessImpl.Build} 参数配置类, 给 BaseBrightnessImpl.Build 配置一些必要的自定义参数
 */


public class BrightnessConfig {
    /**
     * 护眼书写亮度
     */
    private int protectWritingBrightness = IBrightness.PROECT_WRITING_BRIGHTNESS;

    /**
     * {@link IBrightness}  PLATFORM 亮度设置平台类型
     */
    private int typePlatform;

    /**
     * {@link IBrightnessChange.Strategy} 亮度变化策略
     */
    private IBrightnessChange.Strategy changeStrategyType;

    /**
     * {@link BaseBrightnessChangeImpl} 亮度变化策略实现者，通常自定义才设置这
     */
    private BaseBrightnessChangeImpl changeStrategyImpl;

    /**
     * {@link BaseBrightnessImpl} 亮度设置实现者，通常自定义才设置这
     */
    private BaseBrightnessImpl brightnessImpl;





}
