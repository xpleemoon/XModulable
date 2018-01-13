package com.xpleemoon.xmodulable.api;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 组件配置缓存类，内部通过{@link #mModuleMap map}实现组件的缓存
 *
 * @author xpleemoon
 */
public class XModulableOptions {
    private Map<String, IModule> mModuleMap;

    private XModulableOptions(Map<String, IModule> moduleMap) {
        this.mModuleMap = moduleMap;
    }

    /**
     * 根据{@code name}获取组件服务管理
     *
     * @param name 组件服务类型
     * @return
     */
    public IModule getModule(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("组件名不能为空");
        }

        return mModuleMap.get(name);
    }

    /**
     * 添加组件
     *
     * @param name
     * @param m
     * @param <M>
     */
    public <M extends IModule> void addModule(String name, M m) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("组件名不能为空");
        }

        if (m == null) {
            throw new IllegalArgumentException("组件不能为null");
        }

        mModuleMap.put(name, m);
    }

    public static final class Builder {
        Map<String, IModule> mModuleMap;

        public Builder() {
            mModuleMap = new HashMap<>();
        }

        /**
         * @see #addModule(String, Class)
         */
        public Builder addModule(String name, String moduleClzName) {
            if (TextUtils.isEmpty(moduleClzName)) {
                return this;
            }

            Class<? extends IModule> moduleClz = null;
            try {
                moduleClz = (Class<? extends IModule>) Class.forName(moduleClzName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return addModule(name, moduleClz);
        }

        /**
         * @see #addModule(String, IModule)
         */
        public Builder addModule(String name, Class<? extends IModule> moduleClz) {
            if (moduleClz == null) {
                return this;
            }

            IModule module = null;
            try {
                module = moduleClz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return addModule(name, module);
        }

        /**
         * 添加组件
         *
         * @param name        组件名
         * @param m   组件
         * @param <M> 组件泛型类型为{@link IModule}子类
         * @return
         */
        public <M extends IModule> Builder addModule(String name, M m) {
            if (TextUtils.isEmpty(name) || m == null) {
                return this;
            }

            mModuleMap.put(name, m);
            return this;
        }

        public XModulableOptions build() {
            return new XModulableOptions(mModuleMap);
        }
    }
}
