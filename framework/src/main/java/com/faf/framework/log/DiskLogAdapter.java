package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.faf.framework.FrameworkManager;

import static com.faf.framework.log.Utils.checkNotNull;

/**
 * This is used to saves log messages to the disk.
 * By default it uses {@link CsvFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements LogAdapter {

    @NonNull
    private final FormatStrategy formatStrategy;

    public DiskLogAdapter() {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
        formatStrategy.setTag(FrameworkManager.getInstance().getContext().getPackageName());
    }

    public DiskLogAdapter(@NonNull String globalTag) {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
        formatStrategy.setTag(globalTag);
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
        this.formatStrategy.setTag(FrameworkManager.getInstance().getContext().getPackageName());
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy, @NonNull String globalTag) {
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
