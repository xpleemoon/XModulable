package com.xpleemoon.xmodulable.api.template;

import com.xpleemoon.xmodulable.api.IModule;
import com.xpleemoon.xmodulable.api.XModulableOptions;

/**
 * {@link IModule 组件}加载器
 *
 * @author xpleemoon
 */
public interface XModuleLoader {
    /**
     * 主要作用：加载{@link IModule 组件}，并添加到{@code XModulableOptions}
     *
     * @param options {@link IModule 组件}容器wrapper
     */
    void loadInto(XModulableOptions options);
}
