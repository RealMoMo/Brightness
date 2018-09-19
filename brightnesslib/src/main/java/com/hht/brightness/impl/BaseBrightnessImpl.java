package com.hht.brightness.impl;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.hht.brightness.BrightnessConfig;
import com.hht.brightness.IBrightness;
import com.hht.brightness.i.StatusListener;
import com.hht.brightness.strategy.change.BaseBrightnessChangeImpl;
import com.hht.brightness.strategy.change.GradientBrightnessChangeImpl;
import com.hht.brightness.strategy.change.IBrightnessChange;
import com.hht.brightness.strategy.change.ImmediatelyBrightnessChangeImpl;


/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:26
 * @describe
 */
public abstract class BaseBrightnessImpl implements IBrightness,BaseBrightnessChangeImpl.IChangeBrightness {

    protected int writingBrightness;

    protected IBrightnessChange changeStrategy;

    /**
     * {@link IBrightnessChange.Strategy}
     */
    protected int changeStrategyType;

    protected StatusListener statusListener;

    protected  boolean isFinish = true;

    /**
     * 亮度是否正在改变的标记
     */
    private boolean isChanging = false;
    /**
     * 亮度是否正在恢复初始值的标记
     */
    private boolean isRecoverBrightness = false;

    @CallSuper
    @Override
    public void setBrightness(int value) {


        changeStrategy.changeBrightness(getBrightness(),value);
        if(statusListener !=null){
            statusListener.changeStarted();
        }



    }

    @CallSuper
    @Override
    public void setProtectWritingBrightness() {

        changeStrategy.changeBrightness(getBrightness(),writingBrightness);
        if(statusListener !=null){
            statusListener.changeStarted();
        }



    }



    @Override
    public int getProtectWritingBrightness() {
        return writingBrightness;
    }

    @Override
    @IBrightnessChange.Strategy
    public int getChangeStrategyType() {
        return changeStrategyType;

    }

    @Override
    public void setChangeStatusListener(StatusListener changeStatusListener) {
        statusListener = changeStatusListener;
    }

    @Override
    public void setChangeStrategyType(@IBrightnessChange.Strategy int changeStrategyType) {
        this.changeStrategyType = changeStrategyType;
    }





    @Override
    public void recycleVar() {
        if(changeStrategy != null){
            changeStrategy.recycleVar();
        }
    }

    protected void initChangeStrategyImpl(@Nullable Context context, @IBrightnessChange.Strategy int changeStrategyType, BaseBrightnessChangeImpl.IChangeBrightness changeBrightness){
        this.changeStrategyType = changeStrategyType;
        if(changeStrategyType == IBrightnessChange.Strategy.IMMEDIATELY){
            changeStrategy = new ImmediatelyBrightnessChangeImpl(changeBrightness);
        }else if(changeStrategyType == IBrightnessChange.Strategy.GRADIENT){
            changeStrategy = new GradientBrightnessChangeImpl(context,changeBrightness);
        }else if(changeStrategyType == IBrightnessChange.Strategy.CUSTOM){
            throw new IllegalStateException("you can't direct instantiate custom chanage strategy!");
        }

    }


    protected void updateStatus(int value,boolean isAddBrightness,boolean finish){
        if(statusListener == null){
            //亮度设置完毕
            if(finish){
                isRecoverBrightness = false;
                isChanging = false;
            }
            //亮度设置仍在更新
            else{
                isChanging = true;
                isRecoverBrightness = isAddBrightness;
            }
            return;
        }

        //亮度设置完毕
        if(finish){
            isRecoverBrightness = false;
            isChanging = false;
            if(isAddBrightness){
                statusListener.addBrightnessSuccessed();
            }else{
                statusListener.minusBrightnessSuccessed();
            }
        }
        //亮度设置仍在更新
        else{
            isChanging = true;
            isRecoverBrightness = isAddBrightness;
            statusListener.updatingBrightnessValue(value);
        }
    }


    public boolean isChanging() {
        return isChanging;
    }

    public void setChanging(boolean changing) {
        isChanging = changing;
    }

    public boolean isRecoverBrightness() {
        return isRecoverBrightness;
    }

    public void setRecoverBrightness(boolean recoverBrightness) {
        isRecoverBrightness = recoverBrightness;
    }





    public static class Build{

        private BrightnessConfig brightnessConfig;

        /**
         *
         * @param application Application
         */
        public Build(Application application){

        }

        /**
         *
         * @param application
         * @param typeBrightness
         */
        public Build(Application application,int typeBrightness){

        }

        /**
         *
         * @param application
         * @param typeBrightness
         * @param changeStrategy
         */
        public Build(Application application,int typeBrightness,IBrightnessChange.Strategy changeStrategy){

        }



        /**
         *
         */
        public BaseBrightnessImpl create(){


            return null;
        }
    }
}
