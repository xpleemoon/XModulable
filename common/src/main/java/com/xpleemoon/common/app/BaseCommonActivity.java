package com.xpleemoon.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.basiclib.BaseActivity;
import com.xpleemoon.component.api.ComponentManager;

/**
 * 公共业务基础activity
 * <ul>
 * <li>{@link #onCreate(Bundle)}内部调用了{@link ARouter#inject(Object)}用于IOC</li>
 * </ul>
 *
 * @author xpleemoon
 */
public class BaseCommonActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        ComponentManager.inject(this);
    }
}
