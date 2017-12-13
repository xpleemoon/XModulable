package com.xpleemoon.common.router.component;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.component.api.IComponent;

/**
 * 业务组件基础类，用于承载业务组件暴露的{@link BaseService 服务}
 * <ul>
 * <li>{@link #BaseComponent()}调用了{@link ARouter#inject(Object)}用于IOC，
 * <br/>子类若要重载或者重写构造方法，务必调用super()</li>
 * </ul>
 *
 * @author xpleemoon
 */
public class BaseComponent implements IComponent{
    public BaseComponent() {
        ARouter.getInstance().inject(this);
    }
}
