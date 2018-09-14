package com.faf.framework.module;

import android.content.Context;
import com.faf.framework.log.L;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: ModuleManager
 * @Description: 模块管理类
 * @Author: flying
 * @CreateDate: 2018/9/12 16:03
 */
public class ModuleManager {
    private static final String TAG = ModuleManager.class.getSimpleName();
    private Context context;
    private Map<Class, BaseModule> map = new HashMap();
    private Map<Class, BaseModule> initMap = new HashMap();
    private List<OnAllModuleInitFinishListener> finishListeners = new LinkedList<>();

    private ModuleManager() {
    }

    private static class Inner {
        private static final ModuleManager INSTANCE = new ModuleManager();
    }

    public static ModuleManager getInstance() {
        return Inner.INSTANCE;
    }

    public void init(Context context) {
        this.context = context;
    }


    /**
     * 模块注册
     *
     * @param module
     */
    public void register(BaseModule module) {
        if (context == null) {
            throw new RuntimeException("context is null,should init first");
        }
        if (!map.containsKey(module.getClass())) {
            map.put(module.getClass(), module);
            module.setContext(context);
        }
    }


    /**
     * 注册所有模块初始化完成监听器
     *
     * @param listener
     */
    public void registerAllModuleFinishListener(OnAllModuleInitFinishListener listener) {
        if (!finishListeners.contains(listener)) {
            finishListeners.add(listener);
        }
    }

    /**
     * 反注册所有模块初始化完成监听器
     *
     * @param listener
     */
    public void unregisterAllModuleFinishListener(OnAllModuleInitFinishListener listener) {
        if (finishListeners.contains(listener)) {
            finishListeners.remove(listener);
        }
    }


    /**
     * 模块完成,并检测是否所有模块完成，如果所有模块完成初始化，则进行通知
     *
     * @param module
     */
    void onModuleFinish(BaseModule module) {
        initMap.put(module.getClass(), module);
        if (initMap.values().size() == map.values().size()) {
            L.v(TAG, "all modules init finished 【" + map.values().size() + " modules】");
            for (OnAllModuleInitFinishListener l : finishListeners) {
                l.onAllModuleInit();
            }
            initMap.clear();
        }
    }


    public void onAppInit() {
        for (BaseModule module : map.values()) {
            module.onAppInit(context);
        }
    }

    public void onAppExit() {
        for (BaseModule module : map.values()) {
            module.onAppExit(context);
        }
    }

}
