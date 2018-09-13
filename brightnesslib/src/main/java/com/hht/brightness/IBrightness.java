package com.hht.brightness;

import com.paike.zjc.bright.i.StatusListener;
import com.paike.zjc.bright.strategy.change.IChangeStrategy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:18
 * @describe
 */
public interface IBrightness {

    int PROECT_WRITING_BRIGHTNESS = 20;

    int TYPE_STANDARD = 1;

    int TYPE_MSTAR = 2;

    int getBrightness();

    void setBrightness(int value);

    void setProtectWritingBrightness();

    int getProtectWritingBrightness();

//    void stopAndKeepBrightness();
//
//    void stopAndRecoverBrightness();

    void forceChangeBrightness(int targetBrightness);

    IChangeStrategy.Strategy getChangeStrategyType();

    void setChangeStatusListener(StatusListener changeStatusListener);


    void setChangeStrategyType(IChangeStrategy.Strategy changeStrategyType);

    void recycleVar();
}
