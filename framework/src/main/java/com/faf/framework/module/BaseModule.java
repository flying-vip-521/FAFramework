package com.faf.framework.module;

import android.content.Context;
import com.faf.framework.log.L;


/**
 * @ClassName: BaseModule
 * @Description: 模块基础模型，提供应用初始化和退出时的回调
 * @Author: flying
 * @CreateDate: 2018/9/12 16:03
 */
public abstract class BaseModule {
    private boolean init = false;

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void onInitFinish() {
        init = true;
        L.v(this.getClass().getSimpleName(), "onInitFinish:" + this.getClass().getSimpleName());
        ModuleManager.getInstance().onModuleFinish(this);
    }

    public boolean isInited() {
        return init;
    }

    /**
     * 应用初始化时调用
     *
     * @param context
     */
    public abstract void onAppInit(Context context);

    /**
     * 应用退出时调用【本应用所有activity退出】
     *
     * @param context
     */
    public abstract void onAppExit(Context context);


}
