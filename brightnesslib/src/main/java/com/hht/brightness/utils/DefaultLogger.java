package com.hht.brightness.utils;

import android.text.TextUtils;
import android.util.Log;



/**
 * Default logger
 *
 */
public class DefaultLogger  {

    private static boolean isShowLog = true;
    private static boolean isShowStackTrace = true;
    private static boolean isMonitorMode = false;

    private static String TAG = "BrightnessLib";

    public static void showLog(boolean showLog) {
        isShowLog = showLog;
    }

    public static void showStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }

    public static void showMonitor(boolean showMonitor) {
        isMonitorMode = showMonitor;
    }




    public static void debug(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(TextUtils.isEmpty(tag) ? getTAG() : tag, message + getExtInfo(stackTraceElement));
        }
    }


    public static void info(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.i(TextUtils.isEmpty(tag) ? getTAG() : tag, message + getExtInfo(stackTraceElement));
        }
    }


    public static void warning(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.w(TextUtils.isEmpty(tag) ? getTAG() : tag, message + getExtInfo(stackTraceElement));
        }
    }


    public static void error(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.e(TextUtils.isEmpty(tag) ? getTAG() : tag, message + getExtInfo(stackTraceElement));
        }
    }


    public static void monitor(String message) {
        if (isShowLog && isMonitorMode()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(TAG + "::monitor", message + getExtInfo(stackTraceElement));
        }
    }


    public static boolean isMonitorMode() {
        return isMonitorMode;
    }


    public static String getTAG() {
        return TAG;
    }

    public static String getExtInfo(StackTraceElement stackTraceElement) {

        String separator = " & ";
        StringBuilder sb = new StringBuilder("[");

        if (isShowStackTrace) {
            String threadName = Thread.currentThread().getName();
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            long threadID = Thread.currentThread().getId();
            int lineNumber = stackTraceElement.getLineNumber();

            sb.append("ThreadId=").append(threadID).append(separator);
            sb.append("ThreadName=").append(threadName).append(separator);
            sb.append("FileName=").append(fileName).append(separator);
            sb.append("ClassName=").append(className).append(separator);
            sb.append("MethodName=").append(methodName).append(separator);
            sb.append("LineNumber=").append(lineNumber);
        }

        sb.append(" ] ");
        return sb.toString();
    }
}