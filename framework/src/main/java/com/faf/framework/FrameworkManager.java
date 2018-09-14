package com.faf.framework;

import android.content.Context;

/**
 * @ClassName: FrameworkManager
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/12 20:14
 */
public class FrameworkManager {
    private static final String TAG = FrameworkManager.class.getSimpleName();
    private Context context;

    private FrameworkManager() {
    }

    private static class Inner {
        private static final FrameworkManager INSTANCE = new FrameworkManager();
    }

    public static FrameworkManager getInstance() {
        return Inner.INSTANCE;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    public Context getContext() {
        return context;
    }
}
