package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 *
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   Logger.addLogAdapter(new AndroidLogAdapter());
 * </code></pre>
 * <p>
 * And use the appropriate static Logger methods.
 *
 * <pre><code>
 *   Logger.d("debug");
 *   Logger.e("error");
 *   Logger.w("warning");
 *   Logger.v("verbose");
 *   Logger.i("information");
 *   Logger.wtf("What a Terrible Failure");
 * </code></pre>
 *
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   Logger.d("hello %s", "world");
 * </code></pre>
 *
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   Logger.d(MAP);
 *   Logger.d(SET);
 *   Logger.d(LIST);
 *   Logger.d(ARRAY);
 * </code></pre>
 *
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <pre><code>
 *   Logger.json(JSON_CONTENT);
 *   Logger.xml(XML_CONTENT);
 * </code></pre>
 *
 * <h3>Customize Logger</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 * <li>Different {@link LogAdapter}</li>
 * <li>Different {@link FormatStrategy}</li>
 * <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
public class L extends BaseLogger {
    /**
     * General log function that accepts all configurations as parameter
     */

    public static void d(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.d(tag, message, args);
    }

    public static void d(@NonNull String tag, @Nullable Object object) {
        printer.d(tag, object);
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.e(tag, null, message, args);
    }

    public static void e(@NonNull String tag, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(tag, throwable, message, args);
    }

    public static void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.i(tag, message, args);
    }

    public static void v(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.v(tag, message, args);
    }

    public static void w(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.w(tag, message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        printer.wtf(tag, message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@NonNull String tag, @Nullable String json) {
        printer.json(tag, json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@NonNull String tag, @Nullable String xml) {
        printer.xml(tag, xml);
    }

}
