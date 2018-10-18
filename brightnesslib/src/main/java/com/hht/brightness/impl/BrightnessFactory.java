package com.hht.brightness.impl;

import android.app.Application;

import com.hht.brightness.BrightnessPlatform;
import com.hht.brightness.strategy.change.IBrightnessChange;


/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:02
 * @describe
 */
public class BrightnessFactory {



    /**
     *
     * @param application {@link Application}
     * @param platform {@link BrightnessPlatform}
     * @return
     */
    public static BaseBrightnessImpl createBrightnessImpl(Application application, @BrightnessPlatform int platform){
        BaseBrightnessImpl mBrightness = null;
        switch (platform){
            case BrightnessPlatform.PLATFORM_STANDARD:{
                mBrightness = new StandardBrightnessImpl(application);
            }break;
            case BrightnessPlatform.PLATFORM_MSTAR:{
                mBrightness = new MstarBrightnessImpl(application);
            }break;
            case BrightnessPlatform.PLATFORM_OTHER:{
              throw new IllegalArgumentException("Can not create Custom Platform of BrightnessImpl,You should by builder to newInstance BrightnessImpl");
            }
            default:{
                mBrightness = new MstarBrightnessImpl(application);
            }break;
        }

        return  mBrightness;

    }

    /**
     *
     * @param application {@link Application}
     * @param platform {@link BrightnessPlatform}
     * @param changeStrategy {@link IBrightnessChange.Strategy}
     * @return
     */
    public static BaseBrightnessImpl createBrightnessImpl(Application application, @BrightnessPlatform int platform, @IBrightnessChange.Strategy int changeStrategy){
        BaseBrightnessImpl mBrightness = null;
        switch (platform){
            case BrightnessPlatform.PLATFORM_STANDARD:{
                mBrightness = new StandardBrightnessImpl(application,changeStrategy);
            }break;
            case BrightnessPlatform.PLATFORM_MSTAR:{
                mBrightness = new MstarBrightnessImpl(application,changeStrategy);
            }break;
            case BrightnessPlatform.PLATFORM_OTHER:{
                throw new IllegalArgumentException("Can not create Custom Platform of BrightnessImpl,You should by builder to newInstance BrightnessImpl");
            }
            default:{
                mBrightness = new MstarBrightnessImpl(application,changeStrategy);
            }break;
        }

        return  mBrightness;

    }



}
