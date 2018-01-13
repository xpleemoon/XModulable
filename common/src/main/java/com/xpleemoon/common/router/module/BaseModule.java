package com.xpleemoon.common.router.module;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.xmodulable.api.IModule;

/**
 * 业务组件基础类，用于承载业务组件暴露的{@link BaseService 服务}，
 * </br>通俗地说就是{@link BaseService 服务}容器
 * <ul>
 * <li>{@link #BaseModule()}调用了{@link ARouter#inject(Object)}用于IOC，
 * <br/>子类若要重载或者重写构造方法，务必调用super()</li>
 * </ul>
 *
 * @author xpleemoon
 */
public class BaseModule implements IModule {
    public BaseModule() {
        ARouter.getInstance().inject(this);
    }
}
