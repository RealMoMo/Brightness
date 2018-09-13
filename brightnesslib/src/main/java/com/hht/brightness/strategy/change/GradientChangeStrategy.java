package com.hht.brightness.strategy.change;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:49
 * @describe
 */
public class GradientChangeStrategy extends BaseChangeStrategy {

    private Handler mHandler;



    public GradientChangeStrategy(Context context, IChangeBrightness iChangeBrightness){
        mChangeBrightness = iChangeBrightness;

        mHandler = new Handler(context.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(currentValue == targetValue){
                    mHandler.removeCallbacksAndMessages(null);
                    mChangeBrightness.changeBrightness(currentValue,isAddBrightness,true);
                    return;
                }

                switch (msg.what){
                    case TYPE_ADD:{
                        currentValue++;

                    }break;
                    case TYPE_MINUS:{
                        currentValue--;
                    }break;
                    default:{

                    }break;
                }
                mChangeBrightness.changeBrightness(currentValue,isAddBrightness,false);
                this.sendEmptyMessage(msg.what);
            }
        };
    }


    @Override
    public void changeBrightness(int currentBrightness, int targetBrightness) {
        currentValue = currentBrightness;
        targetValue = targetBrightness;

        type = currentValue > targetValue ? TYPE_MINUS : TYPE_ADD;
        isAddBrightness = (type == TYPE_ADD );
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessage(type);

    }

    @Override
    public void stopChangeBrightness() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void recycleVar() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
