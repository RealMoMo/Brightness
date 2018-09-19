package com.hht.brightness.strategy.change;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/10 10:57
 * @describe
 */
public abstract class BaseBrightnessChangeImpl implements IBrightnessChange {

    public static final int TYPE_ADD = 0x101;

    public static final int TYPE_MINUS = 0x102;
    /**
     *
     */
    protected int type;
    /**
     * 是否是加亮度
     */
    protected boolean isAddBrightness;

    //protected IBrightness mBrightness;

    protected int targetValue;

    protected int currentValue;

    protected IChangeBrightness mChangeBrightness;






    public interface IChangeBrightness{
        void changeBrightness(int value, boolean addBrightness, boolean finish);
    }

}
