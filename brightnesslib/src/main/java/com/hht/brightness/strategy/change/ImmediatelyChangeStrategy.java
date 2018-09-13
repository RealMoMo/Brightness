package com.hht.brightness.strategy.change;



/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:48
 * @describe
 */
public class ImmediatelyChangeStrategy  extends BaseChangeStrategy  {


    public ImmediatelyChangeStrategy(IChangeBrightness iChangeBrightness) {
        mChangeBrightness = iChangeBrightness;
    }

    @Override
    public void changeBrightness(int currentBrightness, int targetBrightness) {
            currentValue = currentBrightness;
            targetValue = targetBrightness;
            isAddBrightness = (targetValue > currentValue);
            mChangeBrightness.changeBrightness(targetValue,isAddBrightness,true);
    }

    @Override
    public void stopChangeBrightness() {
        //do nothing
    }

    @Override
    public void recycleVar() {

    }
}
