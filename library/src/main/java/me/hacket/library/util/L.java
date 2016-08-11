package me.hacket.library.util;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;

import android.util.Log;
import me.hacket.library.HNetConfig;

public final class L {

    private static final boolean LOG_DEBUG_MODE = HNetConfig.IS_LOG_DEBUG;

    /**
     * Master switch.To catch debug level info you need set this value below {@link Log#VERBOSE}<br/>
     */
    private static final int LOG_DEBUG_LEVEL = 0;
    /**
     * Master switch.To catch error level info you need set this value below {@link Log#WARN}<br/>
     */
    private static final int LOG_RELEASE_LEVEL = Log.INFO;

    private static final int LOG_LEVEL = LOG_DEBUG_MODE ? LOG_DEBUG_LEVEL : LOG_RELEASE_LEVEL;

    private static final String LOG_TAG = HNetConfig.TAG;

    /**
     * init logger
     */
    static {
        Logger.init(LOG_TAG)  // log tag
                .methodCount(2) // 方法调用栈数
                .logLevel(com.orhanobut.logger.LogLevel.FULL) // log level
                .methodOffset(2) // 2层封装，偏移2个
                .logTool(new AndroidLogTool()); // custom log tool
        // .isShowThreadInfo() // 隐藏Thread信息
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private L() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param obj
     */
    public static void v(Object obj) {
        if (Log.VERBOSE > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.VERBOSE, null);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     */
    public static void v(String tag, Object obj) {
        if (Log.VERBOSE > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.VERBOSE, null);
        }
    }

    /**
     * Send a {@link #LOG_LEVEL} log message.
     *
     * @param obj
     */
    public static void d(Object obj) {
        if (Log.DEBUG > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.DEBUG, null);
        }
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     */
    public static void d(String tag, Object obj) {
        if (Log.DEBUG > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.DEBUG, null);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param obj
     */
    public static void i(Object obj) {
        if (Log.INFO > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.INFO, null);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     */
    public static void i(String tag, Object obj) {
        if (Log.INFO > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.INFO, null);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param obj
     */
    public static void w(Object obj) {
        if (Log.WARN > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.WARN, null);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     */
    public static void w(String tag, Object obj) {
        if (Log.WARN > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.WARN, null);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param obj
     */
    public static void e(Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ERROR, null);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     */
    public static void e(String tag, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ERROR, null);
        }
    }

    /**
     * 避免和{@link L#e(String, Object)}混淆，不对外暴露
     * <p>
     * Send an {@link Log#ERROR} log message.
     * </p>
     */
    private static void e(Throwable throwable, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ERROR, throwable);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     */
    public static void e(String tag, Throwable throwable, Object obj) {
        if (Log.ERROR > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ERROR, throwable);
        }
    }

    public static void wtf(Object obj) {
        if (Log.ASSERT > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(LOG_TAG, msg, Log.ASSERT, null);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param obj The message you would like logged.
     */
    public static void wtf(String tag, Object obj) {
        if (Log.ASSERT > LOG_LEVEL) {
            String msg = obj != null ? obj.toString() : "obj == null";
            logger(tag, msg, Log.ASSERT, null);
        }
    }

    /**
     * pretty json
     *
     * @param json
     */
    public static void json(String json) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerJson(LOG_TAG, json);
        }
    }

    public static void json(String tag, String json) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerJson(tag, json);
        }
    }

    private static void loggerJson(String tag, String json) {
        Logger.t(tag).json(json);
    }

    /**
     * pretty xml
     *
     * @param xml
     */
    public static void xml(String xml) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerXml(LOG_TAG, xml);
        }
    }

    public static void xml(String tag, String xml) {
        if (Log.DEBUG > LOG_LEVEL) {
            loggerXml(tag, xml);
        }
    }

    private static void loggerXml(String tag, String xml) {
        Logger.t(tag).xml(xml);
    }

    /**
     * Exception信息输出
     *
     * @param e 输出异常
     */
    public static void printStackTrace(Throwable e) {
        if (Log.ERROR > LOG_LEVEL) {
            e.printStackTrace();
        }
    }

    /**
     * Logger
     *
     * @param tag
     * @param msg
     * @param logLevel
     * @param throwable
     */
    private static void logger(String tag, String msg, int logLevel, Throwable throwable) {
        switch (logLevel) {
            case Log.VERBOSE:
                Logger.t(tag).v(msg);
                break;
            case Log.DEBUG:
                Logger.t(tag).d(msg);
                break;
            case Log.INFO:
                Logger.t(tag).i(msg);
                break;
            case Log.WARN:
                Logger.t(tag).w(msg);
                break;
            case Log.ERROR:
                Logger.t(tag).e(throwable, msg);
                break;
            case Log.ASSERT:
                Logger.t(tag).wtf(msg);
                break;
            default:
                break;
        }
    }

}