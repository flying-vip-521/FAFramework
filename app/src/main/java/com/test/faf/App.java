package com.test.faf;

import android.app.Application;

/**
 * @ClassName: App
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/14 18:46
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationManager.getInstance().init(this);
    }
}
