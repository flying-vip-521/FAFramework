package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.faf.framework.FrameworkManager;

import static com.faf.framework.log.Utils.checkNotNull;

/**
 * Android terminal log output implementation for {@link LogAdapter}.
 * <p>
 * Prints output to LogCat with pretty borders.
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class AndroidLogAdapter implements LogAdapter {

    @NonNull
    private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
        this.formatStrategy.setTag(FrameworkManager.getInstance().getContext().getPackageName());
    }

    public AndroidLogAdapter(@NonNull String globalTag) {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
        this.formatStrategy.setTag(globalTag);
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
        this.formatStrategy.setTag(FrameworkManager.getInstance().getContext().getPackageName());
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy, @NonNull String globalTag) {
        this.formatStrategy = checkNotNull(formatStrategy);
        this.formatStrategy.setTag(globalTag);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }

}
