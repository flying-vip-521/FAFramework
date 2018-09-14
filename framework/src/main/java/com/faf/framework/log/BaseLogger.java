package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.faf.framework.log.Utils.checkNotNull;

/**
 * @ClassName: BaseLogger
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/14 18:09
 */
public class BaseLogger {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;


    protected static ExLoggerPrinter printer = new ExLoggerPrinter();


    public static void addLogAdapter(@NonNull LogAdapter adapter) {
        printer.addAdapter(checkNotNull(adapter));
    }

    public static boolean needAddDefaultLogAdapter() {
        return printer.needAddDefaultLogAdapter();
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

}
