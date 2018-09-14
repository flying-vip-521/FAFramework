package com.faf.framework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.faf.framework.log.Logger;
import com.faf.framework.module.ModuleManager;
import com.faf.framework.utils.AppUtils;

import java.util.Stack;


/**
 * @ClassName: FAFApplicationManager
 * @Description: 应用初始化管理类
 * @Author: flying
 * @CreateDate: 2018/9/12 16:03
 */
public abstract class FAFApplicationManager {
    private static final String TAG = FAFApplicationManager.class.getSimpleName();
    private Application application;
    private Stack<Activity> activityStack = new Stack<>();
    private boolean foreground = false;

    public void init(Application context) {
        FrameworkManager.getInstance().init(context);
        application = context;
        application.registerActivityLifecycleCallbacks(new FrameworkActivityLifeCycleCallback());
        dispatchInit(context);
        //onAppInit必须在dispatchInit后，因为要先注册模块，再启动模块的初始化
        ModuleManager.getInstance().onAppInit();
    }

    public Context getContext() {
        return application.getApplicationContext();
    }

    private void dispatchInit(Application context) {
        String process = AppUtils.getCurProcessName(context);
        boolean isMainProcess = process != null && process.equals(AppUtils.getPackageName(context));
        initOnAllProcess();
        if (isMainProcess) {
            registerModuleOnMainProcess();
            initOnMainProcess();
            initComponentsOnMainProcess();
        } else {
            dispatchRegisterModuleOnOtherProcess(process);
            dispatchInitOnOtherProcess(process);
            dispatchInitComponentsOnOtherProcess(process);
        }
    }

    abstract void initOnAllProcess();

    /**
     * 初始化主进程需要的组件【禁止耗时函数，组件初始化应在子线程执行】
     */
    abstract void initComponentsOnMainProcess();

    abstract void initOnMainProcess();

    abstract void registerModuleOnMainProcess();

    abstract void dispatchInitComponentsOnOtherProcess(String processName);

    abstract void dispatchInitOnOtherProcess(String processName);

    abstract void dispatchRegisterModuleOnOtherProcess(String processName);

    /**
     * 获取本应用栈顶Activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if (!activityStack.isEmpty()) {
            return activityStack.peek();
        }
        return null;
    }

    /**
     * finish本应用所有Activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * finish本应用除栈顶外的所有Activity
     */
    public void finishAllActivityExceptTop() {
        if (activityStack.isEmpty() || activityStack.size() == 1)
            return;
        Activity currentActivity = activityStack.pop();
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.push(currentActivity);
    }

    /**
     * 应用通过back键退出时的回调【判断标准为应用所有的界面关闭】
     */
    public void onAppExit() {
        Logger.v(TAG, "onAppExit");
        ModuleManager.getInstance().onAppExit();
    }

    /**
     * 应用切换到前台时的回调
     */
    public void onAppForeground() {
        foreground = true;
        Logger.v(TAG, "onAppForeground");
    }

    /**
     * 应用切换到后台时的回调
     */
    public void onAppBackground() {
        foreground = false;
        Logger.v(TAG, "onAppBackground");
    }

    public boolean isForeground() {
        return foreground;
    }

    private class FrameworkActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

        private int foregroundActivities;
        private boolean isChangingConfiguration;


        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                onAppForeground();
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                onAppBackground();
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            activityStack.push(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityStack.remove(activity);
            if (activityStack.size() == 0) {
                onAppExit();
            }
        }
    }

}
