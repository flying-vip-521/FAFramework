package com.test.faf;

import com.faf.framework.FAFApplicationManager;
import com.faf.framework.log.AndroidLogAdapter;
import com.faf.framework.log.FormatStrategy;
import com.faf.framework.log.L;
import com.faf.framework.log.PrettyFormatStrategy;

/**
 * @ClassName: ApplicationManager
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/14 18:47
 */
public class ApplicationManager extends FAFApplicationManager {

    private static final String TAG = ApplicationManager.class.getSimpleName();

    private ApplicationManager() {
    }

    private static class Inner {
        private static final ApplicationManager INSTANCE = new ApplicationManager();
    }

    public static ApplicationManager getInstance() {
        return Inner.INSTANCE;
    }

    @Override
    protected void initOnAllProcess() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().showThreadInfo(true).methodCount(10).onlyPrintStackOnException(false).build();
        L.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    protected void initComponentsOnMainProcess() {

    }

    @Override
    protected void initOnMainProcess() {

    }

    @Override
    protected void registerModuleOnMainProcess() {

    }

    @Override
    protected void dispatchInitComponentsOnOtherProcess(String processName) {

    }

    @Override
    protected void dispatchInitOnOtherProcess(String processName) {

    }

    @Override
    protected void dispatchRegisterModuleOnOtherProcess(String processName) {

    }
}
