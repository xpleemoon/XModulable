package com.xpleemoon.component.api;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.util.Map;

/**
 * 组件配置缓存类，内部通过{@link #mComponentMap map}实现组件的缓存
 *
 * @author xpleemoon
 */
public class ComponentOptions {
    private Map<String, IComponent> mComponentMap;

    private ComponentOptions(@NonNull Map<String, IComponent> componentMap) {
        this.mComponentMap = componentMap;
    }

    /**
     * 根据{@code componentType}获取组件服务管理
     *
     * @param name 组件服务类型
     * @return
     */
    public IComponent getComponent(@NonNull String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("组件名不能为空");
        }

        return mComponentMap.get(name);
    }

    /**
     * 添加组件
     *
     * @param name
     * @param component
     * @param <Component>
     */
    public <Component extends IComponent> void addComponent(@NonNull String name, @NonNull Component component) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("组件名不能为空");
        }

        if (component == null) {
            throw new IllegalArgumentException("组件不能为null");
        }

        mComponentMap.put(name, component);
    }

    public static final class Builder {
        Map<String, IComponent> mComponentMap;

        public Builder() {
            mComponentMap = new ArrayMap<>();
        }

        /**
         * @see #addComponent(String, Class)
         */
        public Builder addComponent(String name, String componentClzName) {
            if (TextUtils.isEmpty(componentClzName)) {
                return this;
            }

            Class<? extends IComponent> componentClz = null;
            try {
                componentClz = (Class<? extends IComponent>) Class.forName(componentClzName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return addComponent(name, componentClz);
        }

        /**
         * @see #addComponent(String, IComponent)
         */
        public Builder addComponent(String name, Class<? extends IComponent> componentClz) {
            if (componentClz == null) {
                return this;
            }

            IComponent component = null;
            try {
                component = componentClz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return addComponent(name, component);
        }

        /**
         * 添加组件
         *
         * @param name        组件名
         * @param component   组件
         * @param <Component> 组件泛型类型为{@link IComponent}子类
         * @return
         */
        public <Component extends IComponent> Builder addComponent(String name, Component component) {
            if (TextUtils.isEmpty(name) || component == null) {
                return this;
            }

            mComponentMap.put(name, component);
            return this;
        }

        public ComponentOptions build() {
            return new ComponentOptions(mComponentMap);
        }
    }
}
