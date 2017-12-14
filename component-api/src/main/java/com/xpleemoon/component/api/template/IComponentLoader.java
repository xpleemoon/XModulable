package com.xpleemoon.component.api.template;

import com.xpleemoon.component.api.ComponentOptions;

/**
 * {@link com.xpleemoon.component.api.IComponent 组件}加载器
 *
 * @author xpleemoon
 */
public interface IComponentLoader {
    /**
     * 主要作用：加载{@link com.xpleemoon.component.api.IComponent 组件}，并添加到{@code componentOptions}
     *
     * @param componentOptions {@link com.xpleemoon.component.api.IComponent 组件}容器wrapper
     */
    void loadInto(ComponentOptions componentOptions);
}
