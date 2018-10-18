package com.hht.brightness.impl;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hht.brightness.BrightnessConfig;
import com.hht.brightness.IBrightness;
import com.hht.brightness.i.ChangeListener;
import com.hht.brightness.strategy.change.BaseBrightnessChangeImpl;
import com.hht.brightness.strategy.change.GradientBrightnessChangeImpl;
import com.hht.brightness.strategy.change.IBrightnessChange;
import com.hht.brightness.strategy.change.ImmediatelyBrightnessChangeImpl;
import com.hht.brightness.thread.DefaultPoolExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:26
 * @describe
 */
public abstract class BaseBrightnessImpl implements IBrightness,BaseBrightnessChangeImpl.IChangeBrightness {

    /**
     * Application Context
     */
    protected Context mContext;
    /**
     * 护眼亮度 {@link IBrightness {@link #PROECT_WRITING_BRIGHTNESS}}
     */
    protected int writingBrightness;

    /**
     * 更新中的当前亮度
     */
    protected int currentUpdatingBrightness;

    /**
     * {@link IBrightnessChange}
     */
    protected IBrightnessChange changeStrategy;

    /**
     * {@link IBrightnessChange.Strategy}
     */
    protected int changeStrategyType;

    /**
     * 亮度变化监听{@link ChangeListener}
     */
    protected ChangeListener changeListener;

    /**
     * 是否完成变化的标志
     */
    protected  boolean isFinish = true;

    /**
     * 亮度是否正在改变的标记
     */
    private boolean isChanging = false;

    protected static DefaultPoolExecutor mThreadPool;

    static {
        mThreadPool = DefaultPoolExecutor.getInstance();
        Log.d("realmo","static init");
    }




    /**
     * 设置亮度
     * @param value 亮度值
     */
    @Override
    public void setBrightness(int value) {


        changeStrategy.changeBrightness(getBrightness(),value);
        if(changeListener !=null){
            changeListener.changeStarted();
        }



    }

    /**
     * 设置为护眼亮度
     */
    @Override
    public void setProtectWritingBrightness() {

        changeStrategy.changeBrightness(getBrightness(),writingBrightness);
        if(changeListener !=null){
            changeListener.changeStarted();
        }



    }


    /**
     * 获取护眼亮度
     * @return 护眼亮度
     */
    @Override
    public int getProtectWritingBrightness() {
        return writingBrightness;
    }

    @Override
    @IBrightnessChange.Strategy
    public int getChangeStrategyType() {
        return changeStrategyType;

    }

    /**
     * 设置亮度变化监听
     * @param changeChangeListener {@link ChangeListener} or 空实现的 {@link com.hht.brightness.i.CustomChangeListener }
     */
    @Override
    public void setChangeStatusListener(ChangeListener changeChangeListener) {
        changeListener = changeChangeListener;
    }

    @Override
    public void setChangeStrategyType(@IBrightnessChange.Strategy int changeStrategyType) {
        this.changeStrategyType = changeStrategyType;
    }

    /**
     * 同步强制改变亮度且不会带亮度改变策略效果，而子类子类只需负责设置对应平台的亮度即可
     * @param targetBrightness  目标亮度
     */
    @CallSuper
    @Override
    public void forceChangeBrightness(int targetBrightness) {
        isFinish = true;
        changeStrategy.stopChangeBrightness();
        if(changeListener !=null){
            changeListener.forceChange(targetBrightness);
        }
    }
    /**
     * 异步强制改变亮度且不会带亮度改变策略效果，而子类子类只需负责设置对应平台的亮度即可
     * @param targetBrightness  目标亮度
     */
    @CallSuper
    @Override
    public  void forceChangeBrightnessAsync(int targetBrightness){
        forceChangeBrightness(targetBrightness);
        //子类需重写具体的逻辑
    }

    /**
     * 回收资源
     */
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
        currentUpdatingBrightness = value;

        if(changeListener == null){
            //亮度设置完毕
            if(finish){

                isChanging = false;
            }
            //亮度设置仍在更新
            else{
                isChanging = true;

            }
            return;
        }

        //亮度设置完毕
        if(finish){

            isChanging = false;
            changeListener.updatingBrightnessValue(value);
            if(isAddBrightness){
                changeListener.addBrightnessSuccessed();
            }else{
                changeListener.minusBrightnessSuccessed();
            }
        }
        //亮度设置仍在更新
        else{
            isChanging = true;

            changeListener.updatingBrightnessValue(value);
        }
    }

    /**
     *
     * @return {@link #isChanging}
     */
    public boolean isChanging() {
        return isChanging;
    }




    /**
     * 亮度构建者Build
     */
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
