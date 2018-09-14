package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface ExPrinter {

    void d(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void d(@NonNull String tag, @Nullable Object object);

    void e(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void e(@NonNull String tag, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void i(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void v(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(@NonNull String tag, @Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@NonNull String tag, @Nullable String xml);


}
