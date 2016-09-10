package com.bhuvanesh.talenthive.util;


import android.util.Log;

import com.bhuvanesh.talenthive.Config;

public class THLoggerUtil {

    private static boolean mLogEnabled = Config.DEBUG;

    public static void debug(Object tag, String msg) {
        if (mLogEnabled) {
            //log tag becomes a package name
            Log.d(tag.toString(), msg);
        }
    }

    public static void error(Object tag, String msg) {
        if (mLogEnabled) {
            Log.e(tag.toString(), msg);
        }
    }
}
