package com.yxdz.common.utils;

import android.util.Log;

public class LogUtils {

    public static boolean isDebug = true;

    public static String HUANG = "zhu";

    public static void e(String msg) {
        if (isDebug) {
            Log.e(HUANG, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }


    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

}