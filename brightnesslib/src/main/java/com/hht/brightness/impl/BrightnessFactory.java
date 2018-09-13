package com.hht.brightness.impl;

import android.content.Context;

import com.paike.zjc.bright.IBrightness;
import com.paike.zjc.bright.strategy.change.IChangeStrategy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:02
 * @describe
 */
public class BrightnessFactory {

    public static BaseBrightness createBrightnessImpl(Context context, int typeBrightness){
        BaseBrightness mBrightness = null;
        switch (typeBrightness){
            case IBrightness.TYPE_STANDARD:{
                mBrightness = new StandardBrightness(context);
            }break;
            case IBrightness.TYPE_MSTAR:
            default:{
                mBrightness = new MstarBrightness(context);
            }break;
        }

        return  mBrightness;

    }


    public static BaseBrightness createBrightnessImpl(Context context, int typeBrightness, IChangeStrategy.Strategy changeStrategy){
        BaseBrightness mBrightness = null;
        switch (typeBrightness){
            case IBrightness.TYPE_STANDARD:{
                mBrightness = new StandardBrightness(context,changeStrategy);
            }break;
            case IBrightness.TYPE_MSTAR:
            default:{
                mBrightness = new MstarBrightness(context,changeStrategy);
            }break;
        }

        return  mBrightness;

    }



}
