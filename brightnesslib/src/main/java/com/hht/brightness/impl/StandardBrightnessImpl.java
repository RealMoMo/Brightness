package com.hht.brightness.impl;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.hht.brightness.strategy.change.IBrightnessChange;

import java.lang.reflect.Method;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:27
 * @describe
 */
public class StandardBrightnessImpl extends BaseBrightnessImpl  {

    /**
     * Application Context
     */
    private Context mContext;


    private int systemMinBrightness;
    private float brightCoefficient = 1f;



    protected StandardBrightnessImpl(@NonNull Application application){
        this(application, IBrightnessChange.Strategy.IMMEDIATELY);
    }

    protected StandardBrightnessImpl(@NonNull Application application, @NonNull @IBrightnessChange.Strategy int changeStrategyType){
        mContext = application;

        initChangeStrategyImpl(mContext,changeStrategyType,this);

        initBrightness();
    }

    public StandardBrightnessImpl(@NonNull Application application,@NonNull IBrightnessChange changeStrategy){
        mContext = application;
        this.changeStrategy = changeStrategy;
        initBrightness();
    }

    private void initBrightness(){
        PowerManager mPowerManager = (PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
        systemMinBrightness = getSystemMinimumScreenBright(mPowerManager);
        brightCoefficient = getBrightCoefficient(systemMinBrightness);
        this.writingBrightness = getRealBright(PROECT_WRITING_BRIGHTNESS);
    }

    @Override
    public int getBrightness() {
        try {
            return Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 100;
        }

    }

//    @Override
//    public void setBrightness(int value) {
//
//
//            changeStrategy.changeBrightness(getBrightness(),value);
//        if(statusListener !=null){
//            statusListener.changeStarted();
//        }
//
//
//
//    }
//
//    @Override
//    public void setProtectWritingBrightness() {
//
//            changeStrategy.changeBrightness(getBrightness(),writingBrightness);
//            if(statusListener !=null){
//                statusListener.changeStarted();
//            }
//
//
//
//    }

    /**
     * 直接设置亮度
     * @param targetBrightness
     */
    @Override
    public void forceChangeBrightness(int targetBrightness) {
        isFinish = true;
        changeStrategy.stopChangeBrightness();
        setStandardBrightness(targetBrightness);
        if(statusListener !=null){
            statusListener.changeStarted();
        }

    }


    private void setStandardBrightness(int value){
        Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
    }

    @Override
    public void changeBrightness(int value,boolean isAddBrightness,boolean finish) {
        isFinish = finish;
        setStandardBrightness(value);
        updateStatus(value,isAddBrightness,finish);

    }



    /**
     *
     * @param mPowerManager
     * @return get system min screen bright
     */
    private int getSystemMinimumScreenBright(PowerManager mPowerManager){
        Class<?> c = null;

        try {
            c = Class.forName("android.os.PowerManager");

            Method m1 = c.getMethod("getMinimumScreenBrightnessSetting");

            return  (int)(m1.invoke(mPowerManager));
        }  catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * y = ax + b (100 = 100a + b)
     * @param b
     * @return a -- bright coefficient
     */
    private float getBrightCoefficient(int b){
        return (100f-b)/100;
    }

    /**
     *
     * @param fakeBright
     * @return   system brightness range: 100 - systemMinBrightness
     */
    private int getRealBright(int fakeBright){
        return (int)(brightCoefficient*fakeBright)+ systemMinBrightness;
    }

    /**
     *
     * @param realBright
     * @return  brightness range: 100 - 0
     */
    private int getFakeBright(int realBright){
        return (int)((realBright- systemMinBrightness)/brightCoefficient);
    }


}
