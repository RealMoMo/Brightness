package com.hht.brightness.impl;

import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.paike.zjc.bright.strategy.change.BaseChangeStrategy;
import com.paike.zjc.bright.strategy.change.IChangeStrategy;

import java.lang.reflect.Method;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:27
 * @describe
 */
public class StandardBrightness extends BaseBrightness implements BaseChangeStrategy.IChangeBrightness {

    /**
     * Application Context
     */
    private Context mContext;


    private int systemMinBrightness;
    private float brightCoefficient = 1f;



    protected StandardBrightness(@NonNull Context context){
        this(context, IChangeStrategy.Strategy.IMMEDIATELY);
    }

    protected StandardBrightness(@NonNull Context context, IChangeStrategy.Strategy changeStrategyType){
        mContext = context;

        initChangeStrategyImpl(mContext,changeStrategyType,this);

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

    @Override
    public void setBrightness(int value) {

            if(statusListener !=null){
                statusListener.changeStarted();
            }
            changeStrategy.changeBrightness(getBrightness(),value);




    }

    @Override
    public void setProtectWritingBrightness() {

            changeStrategy.changeBrightness(getBrightness(),writingBrightness);
            if(statusListener !=null){
                statusListener.changeStarted();
            }



    }

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
