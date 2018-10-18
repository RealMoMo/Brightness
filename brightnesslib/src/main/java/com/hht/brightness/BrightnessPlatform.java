package com.hht.brightness;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name OEM_WhiteBoard
 * @email momo.weiye@gmail.com
 * @time 2018/9/19 14:42
 * @describe 亮度平台
 */
@IntDef({BrightnessPlatform.PLATFORM_STANDARD, BrightnessPlatform.PLATFORM_MSTAR, BrightnessPlatform.PLATFORM_OTHER})
@Retention(RetentionPolicy.SOURCE)
public @interface BrightnessPlatform{
    //Android平台
    int PLATFORM_STANDARD = 0;
    //Mstar平台
    int PLATFORM_MSTAR = 1;
    //自定义平台(海思等)  最好还是直接适配到Android平台
    int PLATFORM_OTHER = 2;
}
