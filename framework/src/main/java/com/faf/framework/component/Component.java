package com.faf.framework.component;

import com.faf.framework.log.L;

public abstract class Component {
    private static final String TAG = Component.class.getSimpleName();
    private String name;

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void onReady() {
        L.v(TAG, "onInitFinish for component : " + name);
        ComponentManager.getInstance().onComponentReady(this);
    }

    /**
     * 执行组件初始化代码，初始化完成时，需调用onReady方法，通知组件管理器
     */
    abstract void execute();


}
