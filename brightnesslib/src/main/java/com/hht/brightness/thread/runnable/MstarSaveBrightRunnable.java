package com.hht.brightness.thread.runnable;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Brightness
 * @email momo.weiye@gmail.com
 * @time 2018/10/18 14:24
 * @describe Mstar平台保存亮度
 */
public class MstarSaveBrightRunnable implements Runnable {
    /**
     * 保存的亮度值
     */
    private int brightnessValue;
    /**
     * Application context
     */
    private Context mContext;

    public MstarSaveBrightRunnable(Context context){
            this(context,0);
    }

    public MstarSaveBrightRunnable(Context context,int brightnessValue){
        mContext  = context;
        this.brightnessValue = brightnessValue;
    }


    /**
     *
     * @param brightnessValue 保存的亮度值
     */
    public void setBrightnessValue(int brightnessValue) {
        this.brightnessValue = brightnessValue;
    }

    @Override
    public void run() {
        synchronized (this) {
            saveMstarBright(brightnessValue);
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
