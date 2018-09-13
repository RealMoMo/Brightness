package com.hht.brightness.impl;

import android.content.Context;
import android.support.annotation.Nullable;

import com.paike.zjc.bright.IBrightness;
import com.paike.zjc.bright.i.StatusListener;
import com.paike.zjc.bright.strategy.change.BaseChangeStrategy;
import com.paike.zjc.bright.strategy.change.GradientChangeStrategy;
import com.paike.zjc.bright.strategy.change.IChangeStrategy;
import com.paike.zjc.bright.strategy.change.ImmediatelyChangeStrategy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:26
 * @describe
 */
public abstract class BaseBrightness implements IBrightness{

    protected int writingBrightness;

    protected IChangeStrategy changeStrategy;

    protected IChangeStrategy.Strategy changeStrategyType;

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

    @Override
    public int getProtectWritingBrightness() {
        return writingBrightness;
    }

    @Override
    public IChangeStrategy.Strategy getChangeStrategyType() {
        return changeStrategyType;

    }

    @Override
    public void setChangeStatusListener(StatusListener changeStatusListener) {
        statusListener = changeStatusListener;
    }

    @Override
    public void setChangeStrategyType(IChangeStrategy.Strategy changeStrategyType) {
        this.changeStrategyType = changeStrategyType;
    }





    @Override
    public void recycleVar() {
        if(changeStrategy != null){
            changeStrategy.recycleVar();
        }
    }

    protected IChangeStrategy.Strategy initChangeStrategyImpl(@Nullable Context context, IChangeStrategy.Strategy changeStrategyType, BaseChangeStrategy.IChangeBrightness changeBrightness){
        this.changeStrategyType = changeStrategyType;
        if(changeStrategyType == IChangeStrategy.Strategy.IMMEDIATELY){
            changeStrategy = new ImmediatelyChangeStrategy(changeBrightness);
        }else if(changeStrategyType == IChangeStrategy.Strategy.GRADIENT){
            changeStrategy = new GradientChangeStrategy(context,changeBrightness);
        }else if(changeStrategyType == IChangeStrategy.Strategy.CUSTOM){
            throw new IllegalStateException("you can't direct instantiate custom chanage strategy!");
        }

        return null;
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
}
