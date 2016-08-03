package me.hacket.library.util;

import android.content.Context;

public class RunningContext {

    private static Context sAppContext;
    /**
     * log日志
     */
    private static boolean isLogDebug;

    public static void initContext(Context context) {
        sAppContext = context;
    }

    public static void initLogDebug(boolean isLogDebug) {
        RunningContext.isLogDebug = isLogDebug;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static boolean isLogDebug() {
        return isLogDebug;
    }

}
