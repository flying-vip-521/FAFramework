package com.faf.framework.component;

import android.text.TextUtils;
import com.faf.framework.log.L;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ComponentManager {
    private static final String TAG = ComponentManager.class.getSimpleName();
    private Map<String, Component> components = new ConcurrentHashMap<>();
    private Set<String> componentReady = new CopyOnWriteArraySet<>();
    private Set<OnAllComponentReadyListener> finishListeners = new CopyOnWriteArraySet<>();

    private ComponentManager() {
    }

    private static class Inner {
        private static final ComponentManager INSTANCE = new ComponentManager();
    }

    public static ComponentManager getInstance() {
        return Inner.INSTANCE;
    }

    /**
     * 注册组件，并启动组件的初始化
     *
     * @param component
     */
    public synchronized void registerComponent(Component component) {
        if (component != null && !TextUtils.isEmpty(component.getName()) && !components.containsKey(component.getName())) {
            components.put(component.getName(), component);
            component.execute();
        }
    }

    /**
     * 组件初始化ok
     *
     * @param component
     */
    public synchronized void onComponentReady(Component component) {
        if (!componentReady.contains(component.getName())) {

            L.v(TAG, "Component Ready : " + component.getName());
            componentReady.add(component.getName());
            if (isAllComponentReady()) {
                notifyAllComponentReady();
            }
        }
    }

    /**
     * 通知所有组件初始化OK
     */
    private synchronized void notifyAllComponentReady() {
        L.v(TAG, "All Component Ready");
        for (Iterator<OnAllComponentReadyListener> iterator = finishListeners.iterator(); iterator.hasNext(); ) {
            OnAllComponentReadyListener listener = iterator.next();
            listener.onAllComponentReady();
        }
        notifyAll();
    }


    /**
     * 注册初始化完成监听器
     *
     * @param listener
     */
    public synchronized void registerAllComponentReadyistener(OnAllComponentReadyListener listener) {
        if (isAllComponentReady()) {
            listener.onAllComponentReady();
            return;
        }
        if (!finishListeners.contains(listener)) {
            finishListeners.add(listener);
        }
    }

    /**
     * 反注册初始化完成监听器
     *
     * @param listener
     */
    public synchronized void unregisterAllComponentReadyistener(OnAllComponentReadyListener listener) {
        finishListeners.remove(listener);
    }

    /**
     * 是否所有组件已OK
     *
     * @return
     */
    public synchronized boolean isAllComponentReady() {
        return components.size() == componentReady.size();
    }

    /**
     * 组件是否已OK
     *
     * @param componentName
     * @return
     */
    public synchronized boolean isComponentReady(String componentName) {
        return componentReady.contains(componentName);
    }

    /**
     * 等待组建初始化完成
     *
     * @throws InterruptedException
     */
    public synchronized void waitForAllComponeReady() throws InterruptedException {
        while (!isAllComponentReady()) {
            wait();
        }
    }

}
