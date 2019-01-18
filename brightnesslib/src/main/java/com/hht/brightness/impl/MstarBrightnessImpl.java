package com.hht.brightness.impl;

import android.app.Application;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.hht.brightness.strategy.change.IBrightnessChange;
import com.hht.brightness.thread.runnable.MstarSaveBrightRunnable;
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

    private MstarSaveBrightRunnable saveBrightRunnable;

    protected MstarBrightnessImpl(@NonNull Application application) {
        this(application, IBrightnessChange.Strategy.IMMEDIATELY);
    }


    protected MstarBrightnessImpl(@NonNull Application application, @NonNull @IBrightnessChange.Strategy int changeStrategyType) {
        mContext = application;
        initChangeStrategyImpl(application, changeStrategyType, this);
        saveBrightRunnable = new MstarSaveBrightRunnable(application);

    }

    public MstarBrightnessImpl(@NonNull IBrightnessChange changeStrategy) {
        this.changeStrategy = changeStrategy;
    }


    @Override
    public int getBrightness() {
        return isFinish ? TvPictureManager.getInstance().getBacklight() : currentUpdatingBrightness;
    }

    @Override
    public void setBrightness(int value) {
        isChanging = true;
        if (isFinish) {
            changeStrategy.changeBrightness(getBrightness(), value);
        } else {
            changeStrategy.changeBrightness(currentUpdatingBrightness, value);
        }

        if (changeListener != null) {
            changeListener.changeStarted();
        }
    }

    @Override
    public void setProtectWritingBrightness() {
        if (isFinish) {
            changeStrategy.changeBrightness(getBrightness(), writingBrightness);
        } else {
            changeStrategy.changeBrightness(currentUpdatingBrightness, writingBrightness);
        }
        if (changeListener != null) {
            changeListener.changeStarted();
        }
    }

    private void setMstarBrightness(int value) {
        try {
            TvManager.getInstance().getPictureManager()
                    .setBacklight(value);
        } catch (TvCommonException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void forceChangeBrightness(int targetBrightness) {
        super.forceChangeBrightness(targetBrightness);
        setMstarBrightness(targetBrightness);
        saveMstarBright(targetBrightness);

    }

    @Override
    public void forceChangeBrightnessAsync(int targetBrightness) {
        super.forceChangeBrightnessAsync(targetBrightness);
        setMstarBrightness(targetBrightness);
        mThreadPool.remove(saveBrightRunnable);
        saveBrightRunnable.setBrightnessValue(targetBrightness);
        mThreadPool.execute(saveBrightRunnable);
    }

    @Override
    public void changeBrightness(int value, boolean isAddBrightness, boolean finish) {
        isFinish = finish;
        setMstarBrightness(value);
        updateStatus(value, isAddBrightness, finish);
        if(finish){
            mThreadPool.remove(saveBrightRunnable);
            saveBrightRunnable.setBrightnessValue(value);
            mThreadPool.execute(saveBrightRunnable);
        }
    }


    private void saveMstarBright(int value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("u8Backlight", value);
        for (int i = 0; i < 9; i++) {
            mContext.getContentResolver()
                    .update(Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/"
                            + "34" + "/picmode/" + i), contentValues, null, null);
            mContext.getContentResolver()
                    .update(Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/"
                            + "23" + "/picmode/" + i), contentValues, null, null);
            mContext.getContentResolver()
                    .update(Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/"
                            + "24" + "/picmode/" + i), contentValues, null, null);
            mContext.getContentResolver()
                    .update(Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/"
                            + "25" + "/picmode/" + i), contentValues, null, null);
            mContext.getContentResolver()
                    .update(Uri.parse("content://mstar.tv.usersetting/picmode_setting/inputsrc/"
                            + "0" + "/picmode/" + i), contentValues, null, null);
        }
    }
}
