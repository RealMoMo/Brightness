package com.hht.brightness.impl;

import android.content.Context;

import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.paike.zjc.bright.strategy.change.BaseChangeStrategy;
import com.paike.zjc.bright.strategy.change.IChangeStrategy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 9:27
 * @describe
 */
public class MstarBrightness extends BaseBrightness implements BaseChangeStrategy.IChangeBrightness {



    protected MstarBrightness(Context context){
        this(context,IChangeStrategy.Strategy.IMMEDIATELY);
    }


    protected MstarBrightness(Context context, IChangeStrategy.Strategy changeStrategyType){
        writingBrightness = PROECT_WRITING_BRIGHTNESS;
        initChangeStrategyImpl(context,changeStrategyType,this);


    }



    @Override
    public int getBrightness() {
        return TvPictureManager.getInstance().getBacklight();
    }

    @Override
    public void setBrightness(int value) {

            if(statusListener !=null){
                statusListener.changeStarted();
            }
            changeStrategy.changeBrightness(getBrightness(), value);


    }

    @Override
    public void setProtectWritingBrightness() {

            changeStrategy.changeBrightness(getBrightness(), writingBrightness);
            if(statusListener !=null){
                statusListener.changeStarted();
            }


    }

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
