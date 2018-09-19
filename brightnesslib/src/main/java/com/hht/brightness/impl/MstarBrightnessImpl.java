package com.hht.brightness.impl;

import android.app.Application;
import android.support.annotation.NonNull;

import com.hht.brightness.strategy.change.IBrightnessChange;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;


/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:27
 * @describe
 */
public class MstarBrightnessImpl extends BaseBrightnessImpl {



    protected MstarBrightnessImpl(@NonNull Application application){
        this(application, IBrightnessChange.Strategy.IMMEDIATELY);
    }


    protected MstarBrightnessImpl(@NonNull Application application, @NonNull @IBrightnessChange.Strategy int changeStrategyType){
        writingBrightness = PROECT_WRITING_BRIGHTNESS;
        initChangeStrategyImpl(application,changeStrategyType,this);


    }

    public MstarBrightnessImpl(@NonNull IBrightnessChange changeStrategy){
        this.changeStrategy = changeStrategy;
    }



    @Override
    public int getBrightness() {
        return TvPictureManager.getInstance().getBacklight();
    }

//    @Override
//    public void setBrightness(int value) {
//
//
//            changeStrategy.changeBrightness(getBrightness(), value);
//        if(statusListener !=null){
//            statusListener.changeStarted();
//        }
//
//    }
//
//    @Override
//    public void setProtectWritingBrightness() {
//
//            changeStrategy.changeBrightness(getBrightness(), writingBrightness);
//            if(statusListener !=null){
//                statusListener.changeStarted();
//            }
//
//
//    }

    private void setMstarBrightness(int value){
        try {
            TvManager.getInstance().getPictureManager()
                    .setBacklight(value);
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forceChangeBrightness(int targetBrightness) {
        isFinish = true;
        changeStrategy.stopChangeBrightness();
        setMstarBrightness(targetBrightness);
        if(statusListener !=null){
            statusListener.changeStarted();
        }
    }

    @Override
    public void changeBrightness(int value,boolean isAddBrightness,boolean finish) {
        isFinish = finish;
        setMstarBrightness(value);
        updateStatus(value,isAddBrightness,finish);
    }
}
